package edu.hw4;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("MultipleStringLiterals")
public class ValidationTest {

    List<Animal> animals = List.of(
        new Animal("test1", Animal.Type.SPIDER, Animal.Sex.F, -1, 1, -1, true),
        new Animal("test2", null, null, 1, 1, 1, true),
        new Animal("test3", null, Animal.Sex.F, 1, 1, -1, true)
    );

    @Test
    void task19Test() {
        var expected = Map.of(
            "test1", ValidationUtils.validate(animals.get(0)),
            "test2", ValidationUtils.validate(animals.get(1)),
            "test3", ValidationUtils.validate(animals.get(2))
        );
        assertThat(Tasks.task19(animals)).containsExactlyInAnyOrderEntriesOf(expected);
    }

    @Test
    void task20Test() {
        var expected = Map.of(
            "test1", "age, weight",
            "test2", "sex, type",
            "test3", "type, weight"
        );
        assertThat(Tasks.task20(animals)).containsExactlyInAnyOrderEntriesOf(expected);
    }

}
