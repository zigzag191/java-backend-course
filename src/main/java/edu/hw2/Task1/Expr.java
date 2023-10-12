package edu.hw2.Task1;

public sealed interface Expr {
    double evaluate();

    record Constant(double value) implements Expr {
        @Override
        public double evaluate() {
            return value;
        }
    }

    record Negate(Expr expr) implements Expr {
        @Override
        public double evaluate() {
            return -expr.evaluate();
        }
    }

    record Exponent(Expr base, int power) implements Expr {
        @Override
        public double evaluate() {
            return Math.pow(base.evaluate(), power);
        }
    }

    record Addition(Expr lhs, Expr rhs) implements Expr {
        @Override
        public double evaluate() {
            return lhs.evaluate() + rhs.evaluate();
        }
    }

    record Multiplication(Expr lhs, Expr rhs) implements Expr {
        @Override
        public double evaluate() {
            return lhs.evaluate() * rhs.evaluate();
        }
    }
}
