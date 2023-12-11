package edu.hw8.Task3;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"MultipleStringLiterals", "MagicNumber"})
public class PasswordBreakerTest {

    static final Map<String, String> HASHES = Map.of(
        "fbade9e36a3f36d3d676c1b808451dd7", "user1",
        "81dc9bdb52d04dc20036dbd8313ed055", "user2",
        "d93591bdf7860e1e4ee2fca799911215", "user3",
        "b4ca7d6d85d4a860cfc6e12adab1c49d", "user4",
        "56b0fbc82e5050909a1fbd10de711dbd", "user5",
        "fa246d0262c3925617b0c72bb20eeb1d", "user6",
        "74b87337454200d4d33f80c4663dc5e5", "user7",
        "368c57ef1e5ab3b9c48cb2ada71e4940", "user9",
        "e6da6827bc584f4e8ed5842fbe70a58e", "user10",
        "0cc175b9c0f1b6a831c399e269772661", "user11"
    );
    static final Map<String, String> EXPECTED = Map.of(
        "user1", "z",
        "user2", "1234",
        "user3", "4321",
        "user4", "9zzz",
        "user5", "1q2w",
        "user6", "9999",
        "user7", "aaaa",
        "user9", "kl9",
        "user10", "1za",
        "user11", "a"
    );
    public static final int MAX_PASSWORD_LENGTH = 4;

    public static Stream<Supplier<PasswordBreaker>> passwordsShouldBeDeterminedCorrectly() {
        return Stream.of(
            () -> new SingleThreadPasswordBreaker(HASHES, MAX_PASSWORD_LENGTH),
            () -> new MultithreadedPasswordBreaker(HASHES, 2, MAX_PASSWORD_LENGTH),
            () -> new MultithreadedPasswordBreaker(HASHES, 8, MAX_PASSWORD_LENGTH),
            () -> new MultithreadedPasswordBreaker(HASHES, 12, MAX_PASSWORD_LENGTH)
        );
    }

    @ParameterizedTest
    @MethodSource
    void passwordsShouldBeDeterminedCorrectly(Supplier<PasswordBreaker> breakerSupplier) {
        var breaker = breakerSupplier.get();
        var result = breaker.getPasswords();
        assertThat(result).containsExactlyInAnyOrderEntriesOf(EXPECTED);
    }

}
