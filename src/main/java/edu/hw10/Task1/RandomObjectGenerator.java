package edu.hw10.Task1;

import edu.hw10.Task1.restrictions.Max;
import edu.hw10.Task1.restrictions.Min;
import edu.hw10.Task1.restrictions.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RandomObjectGenerator {

    public static final String UNABLE_TO_CREATE_OBJECT_ERROR = "unable to create object";
    private static final Logger LOGGER = LogManager.getLogger();

    public Object nextObject(Class<?> objectClass) {
        var constructors = objectClass.getDeclaredConstructors();

        Arrays.sort(objectClass.getConstructors(), Comparator
            .<Constructor<?>>comparingInt(Constructor::getParameterCount)
            .reversed());

        for (var constructor : constructors) {
            try {
                var arguments = generateRandomArguments(constructor);
                return constructor.newInstance(arguments);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                     | NoSuchMethodException e) {
                LOGGER.warn("could not create object with " + constructor + ". Error: " + e);
            }
        }

        throw new RuntimeException(UNABLE_TO_CREATE_OBJECT_ERROR);
    }

    public Object nextObject(Class<?> objectClass, String methodName, Class<?>... params) {
        try {
            var factoryMethod = objectClass.getMethod(methodName, params);
            if (factoryMethod.getReturnType() != objectClass) {
                throw new RuntimeException(UNABLE_TO_CREATE_OBJECT_ERROR);
            }
            var arguments = generateRandomArguments(factoryMethod);
            return factoryMethod.invoke(null, arguments);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException
                 | IllegalAccessException e) {
            throw new RuntimeException(UNABLE_TO_CREATE_OBJECT_ERROR, e);
        }
    }

    private Object[] generateRandomArguments(Executable executable)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var parameters = executable.getParameters();
        var result = new Object[parameters.length];

        for (int i = 0; i < result.length; ++i) {
            result[i] = generateRandomArgument(parameters[i]);
        }

        return result;
    }

    private Object generateRandomArgument(Parameter parameter)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var type = parameter.getType();

        var min = parameter.getAnnotation(Min.class);
        var max = parameter.getAnnotation(Max.class);

        boolean notNull = parameter.isAnnotationPresent(NotNull.class);
        int rangeMin = (min == null) ? Integer.MIN_VALUE : min.value();
        int rangeMax = (max == null) ? Integer.MAX_VALUE : max.value();

        if (type == String.class) {
            return generateString(notNull);
        }

        var primitive = generatePrimitive(type, rangeMin, rangeMax);
        if (primitive != null) {
            return primitive;
        }

        return notNull ? type.getDeclaredConstructor().newInstance() : null;
    }

    private String generateString(boolean notNull) {
        var rng = ThreadLocalRandom.current();
        if (notNull) {
            return UUID.randomUUID().toString();
        }
        return rng.nextBoolean()
            ? UUID.randomUUID().toString()
            : null;
    }

    @SuppressWarnings("ReturnCount")
    private Object generatePrimitive(Class<?> clazz, int rangeMin, int rangeMax) {
        var rng = ThreadLocalRandom.current();
        if (clazz == double.class || clazz == Double.class) {
            return rng.nextDouble(rangeMin, rangeMax);
        }
        if (clazz == int.class || clazz == Integer.class) {
            return rng.nextInt(rangeMin, rangeMax);
        }
        if (clazz == long.class || clazz == Long.class) {
            return rng.nextLong(rangeMin, rangeMax);
        }
        if (clazz == char.class || clazz == Character.class) {
            return (char) rng.nextInt(rangeMin, rangeMax);
        }
        if (clazz == boolean.class || clazz == Boolean.class) {
            return rng.nextBoolean();
        }
        return null;
    }

}
