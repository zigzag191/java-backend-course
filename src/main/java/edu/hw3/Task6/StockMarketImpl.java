package edu.hw3.Task6;

import java.util.Comparator;
import java.util.PriorityQueue;

public class StockMarketImpl implements StockMarket {

    private final PriorityQueue<Stock> queue;

    public StockMarketImpl() {
        this.queue = new PriorityQueue<>(Comparator
            .comparing(Stock::price)
            .reversed());
    }

    @Override
    public void add(Stock stock) {
        queue.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        queue.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return queue.peek();
    }

}
