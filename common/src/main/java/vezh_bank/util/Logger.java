package vezh_bank.util;

//import org.apache.log4j.BasicConfigurator;

import org.slf4j.LoggerFactory;

public class Logger {
//    private org.apache.log4j.Logger logger;
    private org.slf4j.Logger logger;

    public Logger(org.slf4j.Logger logger) {
        this.logger = logger;
//        BasicConfigurator.configure();
    }

    public static Logger getLogger(Class clazz) {
        return new Logger(LoggerFactory.getLogger(clazz));
    }

    public void info(Object message) {
        logger.info(message.toString());
    }

    public void debug(Object message) {
        logger.debug(message.toString());
    }

    public void error(Object message) {
        logger.error(message.toString());
    }
}
