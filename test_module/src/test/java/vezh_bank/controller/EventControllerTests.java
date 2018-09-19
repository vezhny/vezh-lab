package vezh_bank.controller;

import core.dto.EventDTO;
import core.json.EventData;
import core.json.Events;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vezh_bank.constants.ExceptionMessages;
import vezh_bank.constants.Headers;
import vezh_bank.constants.RequestParams;
import vezh_bank.constants.Urls;
import vezh_bank.enums.EventType;
import vezh_bank.extended_tests.ControllerTest;

import java.io.UnsupportedEncodingException;

public class EventControllerTests extends ControllerTest {

    @Test
    public void getEventsTest() throws UnsupportedEncodingException {
        testUtils.logTestStart("Get all events test");

        int userId = testUtils.createNotAClient(serviceProvider.getDataBaseService());
        createEvent(new EventDTO(EventType.ACTIVATING_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.DELETE_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.USER_SIGN_IN, new EventData("some data")));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(userId));

        MockHttpServletResponse response = httpGet(Urls.EVENTS, params);

        checkResponseCode(200, response.getStatus());

        Events events = gson.fromJson(response.getContentAsString(), Events.class);
        checkNumberOfEvents(3, events.getEvents().size());
        Assertions.assertEquals(String.valueOf(1), response.getHeader(Headers.CURRENT_PAGE),
                "Current page");
        Assertions.assertEquals(String.valueOf(1), response.getHeader(Headers.PAGES_COUNT),
                "Pages count");
    }

    @Test
    public void getEventsUserIdAbsent() {
        testUtils.logTestStart("Get events when user ID absent test");

        createEvent(new EventDTO(EventType.ACTIVATING_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.DELETE_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.USER_SIGN_IN, new EventData("some data")));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        MockHttpServletResponse response = httpGet(Urls.EVENTS, params);

        checkResponseCode(400, response.getStatus());

        Assertions.assertEquals(ExceptionMessages.USER_ID_MUST_PRESENT,
                response.getHeader(Headers.ERROR_MESSAGE), "Error message");
    }

    @Test
    public void getEventsUserIdCantBeNumber() {
        testUtils.logTestStart("Get events when user ID can't be a number test");

        createEvent(new EventDTO(EventType.ACTIVATING_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.DELETE_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.USER_SIGN_IN, new EventData("some data")));

        String userId = "x";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, userId);

        MockHttpServletResponse response = httpGet(Urls.EVENTS, params);

        checkResponseCode(400, response.getStatus());

        Assertions.assertEquals(String.format(ExceptionMessages.VALUE_CAN_NOT_BE_A_NUMBER, userId),
                response.getHeader(Headers.ERROR_MESSAGE), "Error message");
    }

    @Test
    public void getEventsUserIdDoesntExist() {
        testUtils.logTestStart("Get events when user doesn't exist test");

        createEvent(new EventDTO(EventType.ACTIVATING_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.DELETE_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.USER_SIGN_IN, new EventData("some data")));

        String userId = "999";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, userId);

        MockHttpServletResponse response = httpGet(Urls.EVENTS, params);

        checkResponseCode(400, response.getStatus());

        Assertions.assertEquals(String.format(ExceptionMessages.USER_DOES_NOT_EXIST, userId),
                response.getHeader(Headers.ERROR_MESSAGE), "Error message");
    }

    @Test
    public void getEventsUserIsClient() {
        testUtils.logTestStart("Get events when user is client test");

        createEvent(new EventDTO(EventType.ACTIVATING_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.DELETE_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.USER_SIGN_IN, new EventData("some data")));

        String userId = String.valueOf(testUtils.createClient(serviceProvider.getDataBaseService()));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, userId);

        MockHttpServletResponse response = httpGet(Urls.EVENTS, params);

        checkResponseCode(400, response.getStatus());

        Assertions.assertEquals(ExceptionMessages.THIS_OPERATION_IS_NOT_AVAILABLE_FOR_CLIENTS,
                response.getHeader(Headers.ERROR_MESSAGE), "Error message");
    }

    private void createEvent(EventDTO eventDTO) {
        serviceProvider.getEventService().addEvent(eventDTO);
    }
}
