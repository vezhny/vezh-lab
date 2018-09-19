package core.handlers;

import core.dto.EventDTO;
import core.exceptions.BadRequestException;
import core.services.ServiceProvider;
import core.validators.EventRequestValidator;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.RequestParams;
import vezh_bank.constants.UserDefault;
import vezh_bank.persistence.entity.Event;
import vezh_bank.util.Logger;
import vezh_bank.util.PageCounter;
import vezh_bank.util.TypeConverter;

import java.util.List;
import java.util.Map;

public class EventsRequestHandler implements RequestHandler<List<EventDTO>> {
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
    public ResponseEntity<List<EventDTO>> getResponse(HttpMethod httpMethod) {
        try {
            eventRequestValidator.checkRequestParams();
        } catch (BadRequestException e) {
            return error(e);
        }

        int rowsOntPage = UserDefault.ROWS_ON_PAGE; //TODO: get from user config
        logger.info("Rows on page: " + rowsOntPage);

        String type = requestParams.get(RequestParams.EVENT_TYPE);
        String date = requestParams.get(RequestParams.EVENT_DATE);
        String data = requestParams.get(RequestParams.EVENT_DATA);

        int pagesCount = PageCounter.getPagesCount(rowsOntPage,
                serviceProvider.getDataBaseService().getEventDao().selectCount(type, date, data));

        int requiredPage = setRequiredPage(TypeConverter.stringToInt(requestParams.get(RequestParams.REQUIRED_PAGE),
                1), pagesCount);

        List<Event> events = serviceProvider.getDataBaseService().getEventDao()
                .select(requiredPage, rowsOntPage, type, date, data);

        List<EventDTO> eventDTOS = serviceProvider.getEventService().getEvents(events);

        ResponseEntity<List<EventDTO>> response = getResponseEntity(eventDTOS, requiredPage, pagesCount);
        return response;
    }
}
