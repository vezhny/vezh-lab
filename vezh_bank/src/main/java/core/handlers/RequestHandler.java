package core.handlers;

import org.springframework.http.ResponseEntity;
import vezh_bank.util.Logger;

import java.util.Map;

public interface RequestHandler<T> {

    ResponseEntity<T> getResponse();

    default void logRequestParams(Logger logger, Map<String, String> requestParams) {
        logger.info("Request params: ");
        for (Map.Entry entry : requestParams.entrySet()) {
            logger.info(entry.getKey() + ": " + entry.getValue());
        }
    }
}
