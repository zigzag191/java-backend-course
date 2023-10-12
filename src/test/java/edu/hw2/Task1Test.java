package edu.hw2;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw2.Task1.Expr.Addition;
import static edu.hw2.Task1.Expr.Constant;
import static edu.hw2.Task1.Expr.Exponent;
import static edu.hw2.Task1.Expr.Multiplication;
import static edu.hw2.Task1.Expr.Negate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("MagicNumber")
public class Task1Test {

    static Stream<Arguments> binaryOperationSource() {
        return Stream.of(
            Arguments.arguments(0.0, 0.0),
            Arguments.arguments(-1.0, 0.0),
            Arguments.arguments(0.0, 1.0),
            Arguments.arguments(99999.0, 999999.0),
            Arguments.arguments(-10000.0, -12345.0)
        );
    }

    @ParameterizedTest
    @MethodSource("binaryOperationSource")
    @DisplayName("Expr.Addition должно давать тот же результат, что и оператор \"+\"")
    void additionShouldGiveSameResultAsBuiltIn(double lhs, double rhs) {
        var addition = new Addition(new Constant(lhs), new Constant(rhs));
        assertThat(addition.evaluate()).isEqualTo(lhs + rhs);
    }

    @ParameterizedTest
    @MethodSource("binaryOperationSource")
    @DisplayName("Expr.Multiplication должно давать тот же результат, что и оператор \"*\"")
    void multiplicationShouldGiveSameResultAsBuiltIn(double lhs, double rhs) {
        var multiplication = new Multiplication(new Constant(lhs), new Constant(rhs));
        assertThat(multiplication.evaluate()).isEqualTo(lhs * rhs);
    }

    @ParameterizedTest
    @MethodSource("binaryOperationSource")
    @DisplayName("Expr.Exponent должно давать тот же результат, что и Math.pow")
    void exponentShouldGiveSameResultAsBuiltIn(double lhs, double rhs) {
        var exponent = new Exponent(new Constant(lhs), (int) rhs);
        assertThat(exponent.evaluate()).isEqualTo(Math.pow(lhs, (int) rhs));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 1, 99999, -99999})
    @DisplayName("Expr.Negate должно давать тот же результат, что и оператор \"-\"")
    void negationShouldGiveSameResultAsBuiltIn(double value) {
        var negation = new Negate(new Constant(value));
        assertThat(negation.evaluate()).isEqualTo(-value);
    }

    @Test
    @DisplayName("Сложное выражение должно считаться корректно")
    void compoundExpressionShouldBeEvaluatedCorrectly() {
        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Constant(1));
        var sumTwoFour = new Addition(two, four);
        var multi = new Multiplication(sumTwoFour, negOne);
        var exp = new Exponent(multi, 2);
        var res = new Addition(exp, new Constant(1));
        assertThat(res.evaluate()).isEqualTo(Math.pow((2.0 + 4.0) * -1.0, 2) + 1);
    }

}
