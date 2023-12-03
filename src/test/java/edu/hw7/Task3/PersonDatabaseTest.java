package edu.hw7.Task3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonDatabaseTest {

    public static final int NUMBER_OF_THREADS = 10;
    public static final String BASE_TEST_NAME = "John Doe №";
    public static final String BASE_TEST_ADDRESS = "test address ";
    public static final String BASE_TEST_PHONE_NUMBER = "test phone number ";

    public static Stream<Supplier<PersonDatabase>> databaseShouldBeThreadSafe() {
        return Stream.of(
            SynchronizedPersonDatabase::new,
            RWLockedPersonDatabase::new
        );
    }

    @ParameterizedTest
    @MethodSource
    void databaseShouldBeThreadSafe(Supplier<PersonDatabase> databaseSupplier)
        throws InterruptedException, ExecutionException {
        var database = databaseSupplier.get();

        var currentPersonId = new AtomicInteger();
        var lastAddedId = new AtomicInteger();

        var insertThreads = new ArrayList<Thread>();
        var readThreads = new ArrayList<CompletableFuture<DatabaseTestSearchResult>>();

        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            insertThreads.add(new Thread(() -> {
                for (int j = 0; j < NUMBER_OF_THREADS; ++j) {
                    int id = currentPersonId.incrementAndGet();
                    database.add(new Person(
                        id,
                        BASE_TEST_NAME + id,
                        BASE_TEST_ADDRESS + id,
                        BASE_TEST_PHONE_NUMBER + id
                    ));
                    lastAddedId.set(id);
                }
            }));
        }

        for (var thread : insertThreads) {
            thread.start();
            readThreads.add(CompletableFuture.supplyAsync(() -> {
                int id = lastAddedId.get();
                return new DatabaseTestSearchResult(
                    database.findByName(BASE_TEST_NAME + id),
                    database.findByAddress(BASE_TEST_ADDRESS + id),
                    database.findByPhone(BASE_TEST_PHONE_NUMBER + id)
                );
            }));
        }

        for (var thread : insertThreads) {
            thread.join();
        }

        CompletableFuture.allOf(readThreads.toArray(CompletableFuture[]::new));

        assertThat(database.size()).isEqualTo(NUMBER_OF_THREADS * NUMBER_OF_THREADS);
        for (var future : readThreads) {
            var result = future.get();
            assertThat(result.byAddressResult())
                .containsExactlyElementsOf(result.byNameResult())
                .containsExactlyElementsOf(result.byPhoneNumberResult());
        }
    }

    private record DatabaseTestSearchResult(
        List<Person> byNameResult,
        List<Person> byAddressResult,
        List<Person> byPhoneNumberResult
    ) {
    }

}
