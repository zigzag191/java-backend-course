package edu.project3.statprinter;

import edu.project3.cli.CommandLineArguments;
import edu.project3.log.LogStatistics;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;

public abstract class StatisticsPrinter {

    public static final String COMMON_AMOUNT_COL_NAME = "Количество";
    protected final PrintWriter writer;
    private final LogStatistics statistics;
    private final CommandLineArguments arguments;

    public StatisticsPrinter(LogStatistics statistics, OutputStream os, CommandLineArguments arguments) {
        this.statistics = statistics;
        this.writer = new PrintWriter(os, true);
        this.arguments = arguments;
    }

    protected abstract void printHeader(String header);

    protected abstract void tableStart(String... columnNames);

    protected abstract void printTableRow(String... values);

    protected abstract void tableEnd();

    public void print() {
        printHeader("Общая информация");
        tableStart("Метрика", "Значение");
        printTableRow("Источник", arguments.resource());
        printTableRow("Начальная дата", arguments.from().equals(LocalDate.MIN)
            ? "-"
            : arguments.from().toString());
        printTableRow("Конечная дата", arguments.to().equals(LocalDate.MAX)
            ? "-"
            : arguments.to().toString());
        printTableRow("Количество запросов", String.valueOf(statistics.requestCount()));
        printTableRow("Средний размер ответа", String.valueOf(statistics.averageBodyBitesSize()));
        tableEnd();

        printHeader("Запрашиваемые ресурсы");
        tableStart("Ресурс", COMMON_AMOUNT_COL_NAME);
        for (var stat : statistics.resourcesFrequencies().entrySet()) {
            printTableRow(stat.getKey(), String.valueOf(stat.getValue()));
        }
        tableEnd();

        printHeader("Коды ответа");
        tableStart("Код", "Имя", COMMON_AMOUNT_COL_NAME);
        for (var stat : statistics.httpCodesFrequencies().entrySet()) {
            printTableRow(
                String.valueOf(stat.getKey().getCode()),
                stat.getKey().getCodeName(),
                String.valueOf(stat.getValue())
            );
        }
        tableEnd();

        printHeader("Http методы");
        tableStart("Метод", COMMON_AMOUNT_COL_NAME);
        for (var stat : statistics.verbsFrequencies().entrySet()) {
            printTableRow(
                stat.getKey(), String.valueOf(stat.getValue())
            );
        }
        tableEnd();

        printHeader("User agents");
        tableStart("User agent", COMMON_AMOUNT_COL_NAME);
        for (var stat : statistics.userAgentFrequencies().entrySet()) {
            printTableRow(
                stat.getKey(), String.valueOf(stat.getValue())
            );
        }
        tableEnd();
    }

}
