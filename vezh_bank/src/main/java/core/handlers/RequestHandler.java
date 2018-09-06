package core.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import vezh_bank.constants.Headers;
import vezh_bank.util.Logger;

public interface RequestHandler<T> {

    ResponseEntity<T> getResponse(HttpMethod httpMethod);

    default int setRequiredPage(int requiredPage, int pagesCount) {
        Logger logger = Logger.getLogger(this.getClass());
        int result;
        if (requiredPage < 1 || requiredPage > pagesCount) {
            logger.info("Invalid required page");
            result = 1;
        } else {
            result = requiredPage;
        }
        logger.info("Required page: " + result);
        return result;
    }

    default ResponseEntity<T> getResponseEntity(T t, int requiredPage, int pagesCount) {
        Logger logger = Logger.getLogger(this.getClass());

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set(Headers.CURRENT_PAGE, String.valueOf(requiredPage));
        headers.set(Headers.PAGES_COUNT, String.valueOf(pagesCount));

        ResponseEntity<T> responseEntity = new ResponseEntity<T>(t, headers, HttpStatus.OK);

        logger.info("Response status: " + HttpStatus.OK.name());
        return responseEntity;
    }
}
