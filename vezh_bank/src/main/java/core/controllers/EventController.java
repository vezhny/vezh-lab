package core.controllers;

import core.dto.EventDTO;
import core.handlers.EventsRequestHandler;
import core.handlers.RequestHandler;
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

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = Urls.EVENTS)
public class EventController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ServiceProvider serviceProvider;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EventDTO>> getEvents(@RequestParam Map<String, String> params) {
        logger.info("Events GET request");
        RequestHandler<List<EventDTO>> handler = new EventsRequestHandler(serviceProvider, params);
        return handler.getResponse(HttpMethod.GET);
    }
}
