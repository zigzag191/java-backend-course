package edu.hw9.Task1;

public class Statistic {

    private int count;
    private double sum;
    private double max;
    private double min;

    public Statistic(double initialValue) {
        count = 1;
        sum = initialValue;
        max = initialValue;
        min = initialValue;
    }

    public void update(double value) {
        ++count;
        sum += value;
        if (max < value) {
            max = value;
        }
        if (min > value) {
            min = value;
        }
    }

    public double getAverage() {
        return sum / (double) count;
    }

    public double getSum() {
        return sum;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }
}
