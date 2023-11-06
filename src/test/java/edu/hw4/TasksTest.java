package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
public class TasksTest {

    List<Animal> animals = List.of(
        new Animal("test1", Animal.Type.CAT, Animal.Sex.F, 4, 12, 50, true),
        new Animal("test2", Animal.Type.DOG, Animal.Sex.M, 15, 11, 150, false),
        new Animal("test3", Animal.Type.SPIDER, Animal.Sex.F, 3, 2, 5, true),
        new Animal("test4", Animal.Type.FISH, Animal.Sex.M, 4, 1, 12, false),
        new Animal("test5", Animal.Type.DOG, Animal.Sex.F, 5, 140, 30, true),
        new Animal("test6", Animal.Type.DOG, Animal.Sex.M, 2, 50, 23, false),
        new Animal("test7", Animal.Type.SPIDER, Animal.Sex.M, 8, 14, 6, true),
        new Animal("test8", Animal.Type.FISH, Animal.Sex.M, 7, 6, 13, false),
        new Animal("test9", Animal.Type.SPIDER, Animal.Sex.F, 5, 15, 10, false),
        new Animal("test10", Animal.Type.FISH, Animal.Sex.M, 6, 3, 20, false),
        new Animal("very long name", Animal.Type.BIRD, Animal.Sex.F, 4, 4, 5, true)
    );

    @Test
    void task1Test() {
        assertThat(Tasks.task1(animals)).isSortedAccordingTo(Comparator.comparing(Animal::height));
    }

    @Test
    void task2Test() {
        int k = 3;
        assertThat(Tasks.task2(animals, k))
            .isSortedAccordingTo(Comparator.comparing(Animal::weight).reversed()).hasSize(k);
    }

    @Test
    void task3Test() {
        var expected = Map.of(
            Animal.Type.CAT, 1,
            Animal.Type.DOG, 3,
            Animal.Type.SPIDER, 3,
            Animal.Type.BIRD, 1,
            Animal.Type.FISH, 3
        );
        assertThat(Tasks.task3(animals)).containsExactlyInAnyOrderEntriesOf(expected);
    }

    @Test
    void task4Test() {
        assertThat(Tasks.task4(animals)).isEqualTo(animals.get(10));
    }

    @Test
    void task5Test() {
        assertThat(Tasks.task5(animals)).isEqualTo(Animal.Sex.M);
    }

    @Test
    void task6Test() {
        var expected = Map.of(
            Animal.Type.CAT, animals.get(0),
            Animal.Type.DOG, animals.get(1),
            Animal.Type.SPIDER, animals.get(8),
            Animal.Type.FISH, animals.get(9),
            Animal.Type.BIRD, animals.get(10)
        );
        assertThat(Tasks.task6(animals)).containsExactlyInAnyOrderEntriesOf(expected);
    }

    @Test
    void task7Test() {
        int k = 3;
        assertThat(Tasks.task7(animals, k)).isEqualTo(animals.get(7));
    }

    @Test
    void task8Test() {
        int k = 15;
        assertThat(Tasks.task8(animals, k).orElseThrow()).isEqualTo(animals.get(1));
    }

    @Test
    void task9Test() {
        assertThat(Tasks.task9(animals)).isEqualTo(42);
    }

    @Test
    void task10Test() {
        var expected = Stream.of(1, 2, 3, 4, 5, 7, 8, 9, 10)
            .map(animals::get)
            .toList();
        assertThat(Tasks.task10(animals)).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void task11Test() {
        var expected = List.of(animals.get(4));
        assertThat(Tasks.task11(animals)).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void task12Test() {
        assertThat(Tasks.task12(animals)).isEqualTo(7);
    }

    @Test
    void task13Test() {
        var expected = List.of(animals.get(10));
        assertThat(Tasks.task13(animals)).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void task14Test() {
        int k = 100;
        assertThat(Tasks.task14(animals, k)).isTrue();
    }

    @Test
    void task15Test() {
        int k = 3;
        int l = 7;
        var expected = Map.of(
            Animal.Type.CAT, 50,
            Animal.Type.DOG, 30,
            Animal.Type.SPIDER, 15,
            Animal.Type.BIRD, 5,
            Animal.Type.FISH, 45
        );
        assertThat(Tasks.task15(animals, k, l)).containsExactlyInAnyOrderEntriesOf(expected);
    }

    @Test
    void task16Test() {
        assertThat(Tasks.task16(animals))
            .isSortedAccordingTo(Comparator
                .comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name));
    }

    @Test
    void task17Test() {
        assertThat(Tasks.task17(animals)).isTrue();
    }

    @Test
    void task18Test() {
        var list1 = List.of(
            new Animal("test1", Animal.Type.FISH, Animal.Sex.F, 1, 3, 21, false)
        );
        var list2 = List.of(
            new Animal("test2", Animal.Type.FISH, Animal.Sex.M, 1, 3, 23, false)
        );
        assertThat(Tasks.task18(List.of(list1, list2, animals)).weight()).isEqualTo(23);
    }

}
