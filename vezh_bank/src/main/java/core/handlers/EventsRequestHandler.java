package core.handlers;

import core.dto.EventDTO;
import core.dto.UserDTO;
import core.exceptions.BadRequestException;
import core.json.Events;
import core.services.ServiceProvider;
import core.validators.EventRequestValidator;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.RequestParams;
import vezh_bank.persistence.entity.Event;
import vezh_bank.util.Logger;
import vezh_bank.util.PageCounter;
import vezh_bank.util.TypeConverter;

import java.util.List;
import java.util.Map;

public class EventsRequestHandler implements RequestHandler<Events> {
    private Logger logger = Logger.getLogger(this.getClass());

    private ServiceProvider serviceProvider;
    private Map<String, String> requestParams;
    private EventRequestValidator eventRequestValidator;

    public EventsRequestHandler(ServiceProvider serviceProvider, Map<String, String> requestParams) {
        this.serviceProvider = serviceProvider;
        this.requestParams = requestParams;
        this.eventRequestValidator = new EventRequestValidator(requestParams, serviceProvider.getDataBaseService());
    }

    @Override
    public ResponseEntity<Events> getResponse(HttpMethod httpMethod) {
        try {
            eventRequestValidator.checkRequestParams();
        } catch (BadRequestException e) {
            return error(e);
        }

        UserDTO user = new UserDTO(serviceProvider.getDataBaseService().getUserDao()
                .getById(TypeConverter.stringToInt(requestParams.get(RequestParams.USER_ID))));
        int rowsOnPage = user.getConfig().getEventsOnPage();
        logger.info("Rows on page: " + rowsOnPage);

        String type = requestParams.get(RequestParams.EVENT_TYPE);
        String date = requestParams.get(RequestParams.EVENT_DATE);
        String data = requestParams.get(RequestParams.EVENT_DATA);

        int pagesCount = PageCounter.getPagesCount(rowsOnPage,
                serviceProvider.getDataBaseService().getEventDao().selectCount(type, date, data));

        int requiredPage = setRequiredPage(TypeConverter.stringToInt(requestParams.get(RequestParams.REQUIRED_PAGE),
                1), pagesCount);

        List<Event> events = serviceProvider.getDataBaseService().getEventDao()
                .select(requiredPage, rowsOnPage, type, date, data);

        List<EventDTO> eventDTOS = serviceProvider.getEventService().getEvents(events);
        Events eventsResponse = new Events(eventDTOS);

        ResponseEntity<Events> response = getResponseEntity(eventsResponse, requiredPage, pagesCount);
        return response;
    }
}
