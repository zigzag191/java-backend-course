package edu.hw10.Task1;

import edu.hw10.Task1.restrictions.Max;
import edu.hw10.Task1.restrictions.Min;
import edu.hw10.Task1.restrictions.NotNull;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

@SuppressWarnings("MagicNumber")
public class RandomObjectGeneratorTest {

    @Test
    void recordsShouldBeGeneratedCorrectly() {
        assertThatNoException().isThrownBy(() -> {
            var rog = new RandomObjectGenerator();
            var object = (TestRecord) rog.nextObject(TestRecord.class);
        });
    }

    @Test
    void generationWithFactoryMethodsShouldWorkCorrectly() {
        assertThatNoException().isThrownBy(() -> {
            var rog = new RandomObjectGenerator();
            var object = (ClassWithStaticFactoryMethod) rog.nextObject(
                ClassWithStaticFactoryMethod.class,
                "create",
                long.class,
                char.class,
                EmptyRecord.class
            );
        });
    }

    @Test
    void restrictionsShouldBeRespected() {
        var rog = new RandomObjectGenerator();
        for (int i = 0; i < 100; ++i) {
            var object = (ClassWithStaticFactoryMethod) rog.nextObject(ClassWithStaticFactoryMethod.class);
            assertThat(object.a).isBetween((char) 10, (char) 20);
            assertThat(object.b).isBetween(-10L, 50L);
            assertThat(object.c).isNotNull();
        }
    }

    @Test
    void emptyClassesShouldBeHandledCorrectly() {
        assertThatNoException().isThrownBy(() -> {
            var rog = new RandomObjectGenerator();
            var object = (EmptyRecord) rog.nextObject(EmptyRecord.class);
        });
    }

    record TestRecord(int a, boolean b, @NotNull String s) {
    }

    static class ClassWithStaticFactoryMethod {

        public char a;
        public long b;
        public EmptyRecord c;

        ClassWithStaticFactoryMethod(
            @Min(10) @Max(20) char a,
            @Min(-10) @Max(50) long b,
            @NotNull EmptyRecord c
        ) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public static ClassWithStaticFactoryMethod create(long b, char a, EmptyRecord c) {
            return new ClassWithStaticFactoryMethod(a, b, c);
        }

    }

    record EmptyRecord() {
    }

}
