package core.controllers;

import core.handlers.CurrenciesRequestHandler;
import core.handlers.RequestHandler;
import core.services.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vezh_bank.constants.Urls;
import vezh_bank.enums.RequestType;
import vezh_bank.util.Logger;

import java.util.Map;

@RequestMapping(value = Urls.CURRENCIES)
public class CurrencyController implements BaseController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ServiceProvider serviceProvider;

    /**
     * Required params: userId, code, value
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addCurrency(@RequestParam Map<String, String> params) {
        logStartOperation(logger);
        RequestHandler requestHandler = new CurrenciesRequestHandler(params, serviceProvider, RequestType.REGISTER_USER);
        ResponseEntity responseEntity = requestHandler.getResponse();
        logEndOperation(logger);
        return responseEntity;
    }
}
