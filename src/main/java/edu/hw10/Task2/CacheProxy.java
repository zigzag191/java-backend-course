package edu.hw10.Task2;

import edu.hw10.Task2.callcache.CallCache;
import edu.hw10.Task2.callcache.FileCallCache;
import edu.hw10.Task2.callcache.InMemoryCallCache;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class CacheProxy {

    public static final String IMPOSSIBLE_TO_SERIALIZE_MESSAGE = "impossible to serialize method results";

    private CacheProxy() {
    }

    public static Object create(Object original, Class<?> proxyInterface, Path cacheFile) throws NoSuchMethodException {
        var methodCaches = buildMethodCaches(proxyInterface, m -> {
            if (m.getAnnotation(Cache.class).persists()) {
                if (!canBeSerialized(m)) {
                    throw new RuntimeException(IMPOSSIBLE_TO_SERIALIZE_MESSAGE);
                }
                try {
                    return new FileCallCache(cacheFile);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("unable to create or init cache from file: " + cacheFile, e);
                }
            } else {
                return new InMemoryCallCache();
            }
        });
        var handler = new CacheInvocationHandler(original, methodCaches);
        return Proxy.newProxyInstance(proxyInterface.getClassLoader(), new Class<?>[] {proxyInterface}, handler);
    }

    public static Object create(Object original, Class<?> proxyInterface) throws NoSuchMethodException {
        var methodCaches = buildMethodCaches(proxyInterface, m -> {
            if (m.getAnnotation(Cache.class).persists()) {
                if (!canBeSerialized(m)) {
                    throw new RuntimeException(IMPOSSIBLE_TO_SERIALIZE_MESSAGE);
                }
                try {
                    var file = Files.createTempFile(null, null);
                    file.toFile().deleteOnExit();
                    return new FileCallCache(file);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("unable to create or init cache from temp file", e);
                }
            } else {
                return new InMemoryCallCache();
            }
        });
        var handler = new CacheInvocationHandler(original, methodCaches);
        return Proxy.newProxyInstance(proxyInterface.getClassLoader(), new Class<?>[] {proxyInterface}, handler);
    }

    public static Map<Method, CallCache> buildMethodCaches(
        Class<?> proxyInterface,
        Function<Method, CallCache> cacheSupplier
    ) throws NoSuchMethodException {
        var methods = proxyInterface.getMethods();
        var methodCaches = new HashMap<Method, CallCache>();
        for (var method : methods) {
            if (method.isAnnotationPresent(Cache.class)) {
                if (canBeCached(method)) {
                    methodCaches.put(method, cacheSupplier.apply(method));
                } else {
                    throw new RuntimeException("method " + method.getName() + " cannot be cached");
                }
            }
        }
        return methodCaches;
    }

    private static boolean canBeCached(Method method) throws NoSuchMethodException {
        for (var parameter : method.getParameters()) {
            if (!isPrimitive(parameter.getType()) && !overridesEqualsAndHashCode(parameter.getType())) {
                return false;
            }
        }
        return true;
    }

    private static boolean canBeSerialized(Method method) {
        for (var parameter : method.getParameters()) {
            if (!isPrimitive(parameter.getType()) && !Serializable.class.isAssignableFrom(parameter.getType())) {
                return false;
            }
        }
        var returnType = method.getReturnType();
        return isPrimitive(returnType) || Serializable.class.isAssignableFrom(returnType);
    }

    private static boolean isPrimitive(Class<?> clazz) {
        return clazz == int.class
            || clazz == long.class
            || clazz == double.class
            || clazz == float.class
            || clazz == byte.class
            || clazz == char.class
            || clazz == boolean.class
            || clazz == short.class;
    }

    private static boolean overridesEqualsAndHashCode(Class<?> clazz) throws NoSuchMethodException {
        var equals = clazz.getMethod("equals", Object.class);
        var hashCode = clazz.getMethod("hashCode");
        return equals.getDeclaringClass() == clazz && hashCode.getDeclaringClass() == clazz;
    }

    private record CacheInvocationHandler(
        Object original,
        Map<Method, CallCache> methodCaches
    ) implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var cache = methodCaches.get(method);
            if (cache == null) {
                return method.invoke(original, args);
            }
            var cachedResult = cache.get(args);
            if (cachedResult != null) {
                return cachedResult;
            }
            var result = method.invoke(original, args);
            cache.put(args, result);
            return result;
        }

    }

}
