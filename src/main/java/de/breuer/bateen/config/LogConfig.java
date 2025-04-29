package de.breuer.bateen.config;

import ch.qos.logback.classic.Logger;
import de.breuer.bateen.util.VaadinLogAppender;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogConfig {

    @PostConstruct
    public void init() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        VaadinLogAppender vaadinAppender = new VaadinLogAppender();
        vaadinAppender.setContext(rootLogger.getLoggerContext());
        vaadinAppender.start();
        rootLogger.addAppender(vaadinAppender);
    }
}
