package core.handlers;

import core.json.Events;
import core.response.VezhBankResponse;
import org.springframework.http.ResponseEntity;
import vezh_bank.util.Logger;

import java.util.Map;

public class EventsRequestHandler implements RequestHandler<Events> {
    private Logger logger = Logger.getLogger(this.getClass());

    private Map<String, String> requestParams;
    private VezhBankResponse vezhBankResponse;

    public EventsRequestHandler(Map<String, String> requestParams, VezhBankResponse vezhBankResponse) {
        this.requestParams = requestParams;
        this.vezhBankResponse = vezhBankResponse;
    }

    @Override
    public ResponseEntity<Events> getResponse() {
        logRequestParams(logger, requestParams);
        return vezhBankResponse.build();
    }
}
