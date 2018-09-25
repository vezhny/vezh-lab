package core.handlers;

import core.response.VezhBankResponse;
import org.springframework.http.ResponseEntity;
import vezh_bank.util.Logger;

import java.util.Map;

public class UserRequestHandler implements RequestHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    private Map<String, String> requestParams;
    private VezhBankResponse vezhBankResponse;

    public UserRequestHandler(Map<String, String> requestParams, VezhBankResponse vezhBankResponse) {
        this.requestParams = requestParams;
        this.vezhBankResponse = vezhBankResponse;
    }

    @Override
    public ResponseEntity getResponse() {
        logRequestParams(logger, requestParams);
        return vezhBankResponse.build();
    }
}
