package core.controllers;

import core.handlers.EventsRequestHandler;
import core.handlers.RequestHandler;
import core.json.Events;
import core.services.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vezh_bank.constants.Urls;
import vezh_bank.util.Logger;

import java.util.Map;

@Controller
@RequestMapping(value = Urls.EVENTS)
public class EventController implements BaseController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ServiceProvider serviceProvider;

    /**
     * Required params: userId
     * filters: type, date, data
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Events> getEvents(@RequestParam Map<String, String> params) {
        logStartOperation(logger);
        logger.info("Events GET request");
        RequestHandler<Events> handler = new EventsRequestHandler(serviceProvider, params);
        logEndOperation(logger);
        return handler.getResponse(HttpMethod.GET);
    }
}
