package core.handlers;

import core.response.VezhBankResponse;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.RequestParams;
import vezh_bank.util.Logger;

import java.util.HashMap;
import java.util.Map;

public class UserRequestHandler implements RequestHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    private Map<String, String> requestParams;
    private VezhBankResponse vezhBankResponse;

    public UserRequestHandler(Map<String, String> requestParams, VezhBankResponse vezhBankResponse) {
        this.requestParams = requestParams;
        this.vezhBankResponse = vezhBankResponse;
    }

    public UserRequestHandler(String login, VezhBankResponse vezhBankResponse) {
        this.vezhBankResponse = vezhBankResponse;
        requestParams = new HashMap<>();
        requestParams.put(RequestParams.LOGIN, login);
    }

    @Override
    public ResponseEntity getResponse() {
        logRequestParams(logger, requestParams);
        return vezhBankResponse.build();
    }
}
