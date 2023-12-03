package edu.project3;

import edu.project3.cli.ArgumentsParser;
import edu.project3.log.LogParser;
import edu.project3.log.LogStatistics;
import edu.project3.statprinter.AdocPrinter;
import edu.project3.statprinter.MarkdownPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {

    private static final Logger LOGGER = LogManager.getLogger();

    private Main() {
    }

    public static void main(String[] args) {
        try {
            var arguments = ArgumentsParser.parse(args);
            var logParser = new LogParser(arguments);
            var logEntries = logParser.parse();
            var statistics = LogStatistics.calculate(logEntries);
            var printer = switch (arguments.format()) {
                case MARKDOWN -> new MarkdownPrinter(statistics, System.out, arguments);
                case ADOC -> new AdocPrinter(statistics, System.out, arguments);
            };
            printer.print();
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }
    }

}
