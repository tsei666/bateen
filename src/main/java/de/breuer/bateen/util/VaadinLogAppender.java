package de.breuer.bateen.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class VaadinLogAppender extends AppenderBase<ILoggingEvent> {

    private static final List<LogEntry> logs = Collections.synchronizedList(new LinkedList<>());

    @Override
    protected void append(ILoggingEvent eventObject) {
        logs.add(new LogEntry(eventObject.getTimeStamp(), eventObject.getFormattedMessage()));
        if (logs.size() > 500) {
            logs.remove(0);
        }
    }

    public static List<LogEntry> getLogs() {
        return new LinkedList<>(logs);
    }

    public static void clearLogs() {
        logs.clear();
    }

    public static class LogEntry {
        private final long timestamp;
        private final String message;

        public LogEntry(long timestamp, String message) {
            this.timestamp = timestamp;
            this.message = message;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String getMessage() {
            return message;
        }

        public Instant getInstant() {
            return Instant.ofEpochMilli(timestamp);
        }
    }
}
