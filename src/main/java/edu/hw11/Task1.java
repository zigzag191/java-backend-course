package edu.hw11;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public final class Task1 {

    private Task1() {
    }

    public static Object createObjectWithToString() {
        var type = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.named("toString"))
            .intercept(FixedValue.value("Hello, ByteBuddy!"));
        try (var unloaded = type.make()) {
            return unloaded
                .load(Task1.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();
        } catch (InstantiationException
                 | IllegalAccessException
                 | NoSuchMethodException
                 | InvocationTargetException e
        ) {
            throw new RuntimeException("unable to create object", e);
        }
    }

}
