package edu.project3.log;

import edu.project3.logsource.LogSource;
import edu.project3.logsource.StringSource;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@SuppressWarnings({"LineLength", "MultipleStringLiterals"})
public final class LogTestData {

    public static final ZoneOffset ZERO_OFFSET = ZoneOffset.of("+0000");
    public static final LogSource SOURCE = new StringSource(
        "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\""
            + System.lineSeparator()
            + "217.168.17.5 - - [17/May/2015:08:05:34 +0000] \"GET /downloads/product_1 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\""
            + System.lineSeparator()
    );
    public static final List<LogEntry> ENTRIES = List.of(
        new LogEntry("93.180.71.3", "-",
            OffsetDateTime.of(2015, 5, 17, 8, 5, 32, 0,
                ZERO_OFFSET
            ), "GET", "/downloads/product_1", HttpCode.CODE_304, 0,
            "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        ),
        new LogEntry("217.168.17.5", "-",
            OffsetDateTime.of(2015, 5, 17, 8, 5, 34, 0,
                ZERO_OFFSET
            ), "GET", "/downloads/product_1", HttpCode.CODE_200, 490,
            "-", "Debian APT-HTTP/1.3 (0.8.10.3)"
        )
    );

    private LogTestData() {
    }

}
