package core.response.currency;

import core.response.VezhBankResponse;
import core.services.ServiceProvider;
import org.springframework.http.ResponseEntity;
import vezh_bank.util.Logger;

import java.util.Map;

public class AddCurrencyResponse implements VezhBankResponse {
    private Logger logger = Logger.getLogger(this.getClass());
    private ServiceProvider serviceProvider;
    private Map<String, String> requestParams;

    public AddCurrencyResponse(ServiceProvider serviceProvider, Map<String, String> requestParams) {
        this.serviceProvider = serviceProvider;
        this.requestParams = requestParams;
    }

    @Override
    public ResponseEntity build() { // TODO: value in upper case
        logger.info("Add currency");
        return null;
    }
}
