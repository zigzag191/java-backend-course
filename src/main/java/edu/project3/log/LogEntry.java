package edu.project3.log;

import java.time.OffsetDateTime;

public record LogEntry(
    String remoteAddress,
    String remoteUser,
    OffsetDateTime time,
    String verb,
    String requestedResource,
    HttpCode statusCode,
    int bodyBitesSend,
    String httpReferer,
    String httpUserAgent
) {
}
