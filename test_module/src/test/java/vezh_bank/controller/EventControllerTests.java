package vezh_bank.controller;

import core.dto.EventDTO;
import core.json.EventData;
import core.json.Events;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vezh_bank.constants.RequestParams;
import vezh_bank.constants.Urls;
import vezh_bank.enums.EventType;
import vezh_bank.extended_tests.ControllerTest;

import java.io.UnsupportedEncodingException;

public class EventControllerTests extends ControllerTest {

    @Test
    public void getEventsTest() throws UnsupportedEncodingException {
        testUtils.logTestStart("Get all events test");

        int userId = testUtils.createUser(serviceProvider.getDataBaseService());
        createEvent(new EventDTO(EventType.ACTIVATING_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.DELETE_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.USER_SIGN_IN, new EventData("some data")));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(userId));

        MockHttpServletResponse response = httpGet(Urls.EVENTS, params);

        checkResponseCode(200, response.getStatus());

        Events events = gson.fromJson(response.getContentAsString(), Events.class);
        checkNumberOfEvents(3, events.getEvents().size());
    }

    private void createEvent(EventDTO eventDTO) {
        serviceProvider.getEventService().addEvent(eventDTO);
    }
}
