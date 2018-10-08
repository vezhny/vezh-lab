package core.handlers;

import core.response.VezhBankResponse;
import core.response.currency.AddCurrencyResponse;
import core.services.ServiceProvider;
import org.springframework.http.ResponseEntity;
import vezh_bank.enums.RequestType;
import vezh_bank.util.Logger;

import java.util.Map;

public class CurrenciesRequestHandler implements RequestHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    private Map<String, String> requestParams;
    private VezhBankResponse vezhBankResponse;
    private RequestType requestType;
    private ServiceProvider serviceProvider;

    public CurrenciesRequestHandler(Map<String, String> requestParams, ServiceProvider serviceProvider, RequestType requestType) {
        this.requestParams = requestParams;
        this.requestType = requestType;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public ResponseEntity getResponse() {
        logRequestParams(logger, requestParams);
        switch (requestType) {
            case ADD_CURRENCY: {
                vezhBankResponse = new AddCurrencyResponse(serviceProvider, requestParams);
                break;
            }
        }
        ResponseEntity response = vezhBankResponse.build();
        logger.info(response.toString());
        return response;
    }
}
