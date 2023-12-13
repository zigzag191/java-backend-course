package edu.hw11;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.objectweb.asm.Opcodes;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {

    public static final String IMPL_CLASS_NAME = "FibCalculatorImpl";
    public static final String GENERATED_METHOD_NAME = "fib";
    public static final String DESCRIPTOR = "(I)J";
    public static final int OPERAND_STACK_SIZE = 6;
    static FibCalculator calculator;

    @BeforeAll
    static void createCalculator()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        calculator = new ByteBuddy()
            .subclass(FibCalculator.class)
            .name(IMPL_CLASS_NAME)
            .method(named(GENERATED_METHOD_NAME))
            .intercept(new Implementation.Simple(new FibAppender()))
            .make()
            .load(Task3Test.class.getClassLoader())
            .getLoaded()
            .getDeclaredConstructor()
            .newInstance();
    }

    @ParameterizedTest
    @CsvSource({
        "1, 1",
        "2, 1",
        "3, 2",
        "4, 3",
        "5, 5",
        "6, 8",
        "7, 13",
        "8, 21",
        "9, 34"
    })
    void generatedCalculatorShouldProduceCorrectValues(int n, long expected) {
        assertThat(calculator.fib(n)).isEqualTo(expected);
    }

    public static abstract class FibCalculator {
        public abstract long fib(int n);
    }

    public static class FibAppender implements ByteCodeAppender {

        @Override
        @NotNull
        public Size apply(
            @NotNull MethodVisitor methodVisitor,
            @NotNull Implementation.Context context,
            @NotNull MethodDescription methodDescription
        ) {
            var recursiveCall = new Label();

            methodVisitor.visitInsn(Opcodes.ICONST_2);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitInsn(Opcodes.ISUB);
            methodVisitor.visitJumpInsn(Opcodes.IFLT, recursiveCall);
            methodVisitor.visitInsn(Opcodes.LCONST_1);
            methodVisitor.visitInsn(Opcodes.LRETURN);

            methodVisitor.visitLabel(recursiveCall);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitInsn(Opcodes.ICONST_2);
            methodVisitor.visitInsn(Opcodes.ISUB);
            methodVisitor.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                IMPL_CLASS_NAME,
                GENERATED_METHOD_NAME,
                DESCRIPTOR,
                false
            );

            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitInsn(Opcodes.ISUB);
            methodVisitor.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                IMPL_CLASS_NAME,
                GENERATED_METHOD_NAME,
                DESCRIPTOR,
                false
            );

            methodVisitor.visitInsn(Opcodes.LADD);
            methodVisitor.visitInsn(Opcodes.LRETURN);

            return new Size(OPERAND_STACK_SIZE, 0);
        }

    }

}
