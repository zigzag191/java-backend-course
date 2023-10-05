package hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {

    @Test
    void test1() {
        assertThat(Task7.rotateLeft(16, 1)).isEqualTo(1);
    }

    @Test
    void test2() {
        assertThat(Task7.rotateLeft(17, 2)).isEqualTo(6);
    }

    @Test
    void test3() {
        assertThat(Task7.rotateRight(8, 1)).isEqualTo(4);
    }

}
