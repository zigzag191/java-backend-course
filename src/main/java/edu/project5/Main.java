package edu.project5;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class Main {

    public static final String TESTED_METHOD = "name";
    public static final int WARM_UP_TIME_SECONDS = 5;
    public static final int MEASUREMENT_TIME_MINUTES = 3;
    private Student student;
    private Method method;
    private MethodHandle handle;
    private Supplier<?> lambda;

    public static void main(String[] args) throws Throwable {
        Options options = new OptionsBuilder()
            .include(Main.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(WARM_UP_TIME_SECONDS))
            .measurementIterations(1)
            .measurementTime(TimeValue.minutes(MEASUREMENT_TIME_MINUTES))
            .build();

        new Runner(options).run();
    }

    @Setup
    public void setup() throws Throwable {
        student = new Student("hello", "world");
        method = Student.class.getDeclaredMethod(TESTED_METHOD);

        var lookup = MethodHandles.lookup();
        var methodType = MethodType.methodType(String.class);

        handle = lookup.findVirtual(Student.class, TESTED_METHOD, methodType);

        var callSite = LambdaMetafactory.metafactory(
            lookup,
            "get",
            MethodType.methodType(Supplier.class, Student.class),
            MethodType.methodType(Object.class),
            handle,
            MethodType.methodType(String.class)
        );

        var factory = callSite.getTarget().bindTo(student);
        lambda = (Supplier<?>) factory.invoke();
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        bh.consume(method.invoke(student));
    }

    @Benchmark
    public void methodHandles(Blackhole bh) throws Throwable {
        bh.consume(handle.invoke(student));
    }

    @Benchmark
    public void lambdaMetafactory(Blackhole bh) {
        bh.consume(lambda.get());
    }

    public record Student(String name, String surname) {
    }

}
