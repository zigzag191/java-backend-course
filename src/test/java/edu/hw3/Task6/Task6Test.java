package edu.hw3.Task6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task6Test {

    static final Stock[] STOCKS = new Stock[]{
        new Stock(100),
        new Stock(50),
        new Stock(99),
        new Stock(34),
        new Stock(200),
        new Stock(12),
    };

    @Test
    @DisplayName("Самая дорогая акция должна определяться корректно")
    void mostValuableStockShouldBeDeterminedCorrectly() {
        var stockMarket = new StockMarketImpl();
        Arrays.stream(STOCKS).forEach(stockMarket::add);

        var sortedStocks = new ArrayList<Stock>();
        var mostValuableStock = stockMarket.mostValuableStock();

        while (mostValuableStock != null) {
            sortedStocks.add(mostValuableStock);
            stockMarket.remove(mostValuableStock);
            mostValuableStock = stockMarket.mostValuableStock();
        }

        assertThat(sortedStocks).containsExactlyInAnyOrder(STOCKS);
        assertThat(sortedStocks).isSortedAccordingTo(Comparator.comparing(Stock::price).reversed());
    }

}
