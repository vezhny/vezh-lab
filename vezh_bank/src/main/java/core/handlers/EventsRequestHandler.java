package core.handlers;

import core.json.Events;
import core.response.VezhBankResponse;
import core.response.event.GetEventsResponse;
import core.services.ServiceProvider;
import org.springframework.http.ResponseEntity;
import vezh_bank.util.Logger;

import java.util.Map;

public class EventsRequestHandler implements RequestHandler<Events> {
    private Logger logger = Logger.getLogger(this.getClass());

    private Map<String, String> requestParams;
    private ServiceProvider serviceProvider;
    private VezhBankResponse<Events> vezhBankResponse;

    public EventsRequestHandler(ServiceProvider serviceProvider, Map<String, String> requestParams) {
        this.requestParams = requestParams;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public ResponseEntity<Events> getResponse() {
        logRequestParams(logger, requestParams);
        vezhBankResponse = new GetEventsResponse(serviceProvider, requestParams);
        ResponseEntity<Events> response = vezhBankResponse.build();
        logger.info(response.toString());
        return response;
    }
}
