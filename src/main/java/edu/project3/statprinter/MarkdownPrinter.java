package edu.project3.statprinter;

import edu.project3.cli.CommandLineArguments;
import edu.project3.log.LogStatistics;
import java.io.OutputStream;

public class MarkdownPrinter extends StatisticsPrinter {

    public MarkdownPrinter(LogStatistics statistics, OutputStream os, CommandLineArguments arguments) {
        super(statistics, os, arguments);
    }

    @Override
    protected void printHeader(String header) {
        writer.println("### " + header);
    }

    @Override
    protected void tableStart(String... columnNames) {
        for (var column : columnNames) {
            writer.print("|" + column);
        }
        writer.println("|");
        writer.print("|:-:".repeat(columnNames.length));
        writer.println("|");
    }

    @Override
    protected void printTableRow(String... values) {
        for (var value : values) {
            writer.print("|" + value);
        }
        writer.println("|");
    }

    @Override
    protected void tableEnd() {
        writer.println();
    }

}
