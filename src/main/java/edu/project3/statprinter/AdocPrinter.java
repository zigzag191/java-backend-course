package edu.project3.statprinter;

import edu.project3.cli.CommandLineArguments;
import edu.project3.log.LogStatistics;
import java.io.OutputStream;

public class AdocPrinter extends StatisticsPrinter {

    public static final String TABLE_DELIMITER = "|===";

    public AdocPrinter(LogStatistics statistics, OutputStream os, CommandLineArguments arguments) {
        super(statistics, os, arguments);
    }

    @Override
    protected void printHeader(String header) {
        writer.println("=== " + header);
    }

    @Override
    protected void tableStart(String... columnNames) {
        writer.println("[cols=\"" + "1,".repeat(columnNames.length - 1) + "1\"]");
        writer.println(TABLE_DELIMITER);
    }

    @Override
    protected void printTableRow(String... values) {
        for (var value : values) {
            writer.println("|" + value);
        }
        writer.println();
    }

    @Override
    protected void tableEnd() {
        writer.println(TABLE_DELIMITER);
    }

}
