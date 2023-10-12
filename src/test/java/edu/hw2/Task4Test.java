package edu.hw2;

import edu.hw2.Task4.CallingInfo;
import edu.hw2.Task4.RuntimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {

    private static final String CLASS_NAME = "edu.hw2.Task4Test";

    @Test
    @DisplayName("Класс и вызванный метод должны корректно определяться")
    void classNameAndMethodNameShouldBeDetectedCorrectly() {
        var methodName = "classNameAndMethodNameShouldBeDetectedCorrectly";
        var info = RuntimeUtils.callingInfo();
        assertThat(info).extracting("className", "methodName")
            .containsExactly(CLASS_NAME, methodName);
    }

    @Test
    @DisplayName("Анонимные классы должны корректно определяться")
    void anonymousClassesShouldBeDetectedCorrectly() {
        var anonymousClassInstance1 = new Task4Test() {
            public CallingInfo describe() {
                return RuntimeUtils.callingInfo();
            }
        };
        var anonymousClassInstance2 = new Task4Test() {
            public CallingInfo describe() {
                return RuntimeUtils.callingInfo();
            }
        };
        var methodName = "describe";
        var info1 = anonymousClassInstance1.describe();
        var info2 = anonymousClassInstance2.describe();
        assertThat(info1.className()).startsWith(CLASS_NAME + '$');
        assertThat(info2.className()).startsWith(CLASS_NAME + '$');
        assertThat(info1.methodName()).isEqualTo(info2.methodName()).isEqualTo(methodName);
    }

}
