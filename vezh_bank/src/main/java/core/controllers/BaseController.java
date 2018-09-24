package core.controllers;

import vezh_bank.util.Logger;

public interface BaseController {

    default void logStartOperation(Logger logger) {
        logger.info(">>>>>>>>>>>>>> START OPERATION <<<<<<<<<<<<<<");
    }

    default void logEndOperation(Logger logger) {
        logger.info(">>>>>>>>>>>>>> END OPERATION <<<<<<<<<<<<<<");
    }
}
