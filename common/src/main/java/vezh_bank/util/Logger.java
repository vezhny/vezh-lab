package vezh_bank.util;

import org.apache.log4j.BasicConfigurator;

public class Logger {
    private org.apache.log4j.Logger logger;

    public Logger(org.apache.log4j.Logger logger) {
        this.logger = logger;
        BasicConfigurator.configure();
    }

    public static Logger getLogger(Class clazz) {
        return new Logger(org.apache.log4j.Logger.getLogger(clazz));
    }

    public void info(Object message) {
        logger.info(message);
    }

    public void debug(Object message) {
        logger.debug(message);
    }

    public void error(Object message) {
        logger.error(message);
    }
}
