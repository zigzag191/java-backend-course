package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import static net.bytebuddy.matcher.ElementMatchers.named;

public final class Task2 {

    private Task2() {
    }

    static {
        ByteBuddyAgent.install();
    }

    public static void delegateMethodCall(Class<?> source, String methodName, Class<?> target) {
        new ByteBuddy()
            .redefine(source)
            .method(named(methodName))
            .intercept(MethodDelegation.to(target))
            .make()
            .load(source.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
    }

}
