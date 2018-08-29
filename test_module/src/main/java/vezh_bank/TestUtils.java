package vezh_bank;

import org.apache.log4j.Logger;

public class TestUtils {
    private Logger logger;

    public TestUtils(Logger logger) {
        this.logger = logger;
    }

    public void logTestStart(String log) {
        logger.info("------ " + log + " ------");
    }

    public void logTestEnd() {
        logger.info("-------------------------\n");
    }
}
