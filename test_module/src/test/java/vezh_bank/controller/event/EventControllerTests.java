package vezh_bank.controller.event;

import core.dto.EventDTO;
import core.json.EventData;
import core.json.Events;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vezh_bank.constants.ExceptionMessages;
import vezh_bank.constants.RequestParams;
import vezh_bank.constants.Urls;
import vezh_bank.controller.event.providers.EventControllerArgumentsProvider;
import vezh_bank.enums.EventType;
import vezh_bank.extended_tests.ControllerTest;

import java.io.UnsupportedEncodingException;

@Epic("Event controller")
@Story("Get events")
@Link(name = "Issue", value = "https://github.com/vezhny/vezh-lab/issues/11", url = "https://github.com/vezhny/vezh-lab/issues/11")
public class EventControllerTests extends ControllerTest {

    @Severity(SeverityLevel.BLOCKER)
    @Feature("Get events")
    @Test
    @Description("Get all events test")
    public void getEventsTest() throws UnsupportedEncodingException {
        testUtils.logTestStart("Get all events test");

        int userId = testUtils.createNotAClient(serviceProvider.getDataBaseService());
        createEvent(new EventDTO(EventType.ACTIVATING_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.DELETE_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.USER_SIGN_IN, new EventData("some data")));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(userId));

        MockHttpServletResponse response = httpGet(Urls.EVENTS, params);

        httpAsserts.checkResponseCode(200, response.getStatus());

        Events events = gson.fromJson(response.getContentAsString(), Events.class);
        eventAsserts.checkNumberOfEvents(3, events.getEvents().size());
        httpAsserts.checkCurrentPage(1, response);
        httpAsserts.checkPagesCount(1, response);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("User id validation")
    @Test
    @Description("Get events when user ID absent test")
    public void getEventsUserIdAbsent() {
        testUtils.logTestStart("Get events when user ID absent test");

        createEvent(new EventDTO(EventType.ACTIVATING_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.DELETE_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.USER_SIGN_IN, new EventData("some data")));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        MockHttpServletResponse response = httpGet(Urls.EVENTS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(ExceptionMessages.missingParameter(RequestParams.USER_ID), response);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("User id validation")
    @Test
    @Description("Get events when user ID can't be a number test")
    public void getEventsUserIdCantBeNumber() {
        testUtils.logTestStart("Get events when user ID can't be a number test");

        createEvent(new EventDTO(EventType.ACTIVATING_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.DELETE_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.USER_SIGN_IN, new EventData("some data")));

        String userId = "x";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, userId);

        MockHttpServletResponse response = httpGet(Urls.EVENTS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(ExceptionMessages.invalidParameter(RequestParams.USER_ID), response);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("User id validation")
    @Test
    @Description("Get events when user doesn't exist test")
    public void getEventsUserIdDoesntExist() {
        testUtils.logTestStart("Get events when user doesn't exist test");

        createEvent(new EventDTO(EventType.ACTIVATING_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.DELETE_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.USER_SIGN_IN, new EventData("some data")));

        String userId = "999";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, userId);

        MockHttpServletResponse response = httpGet(Urls.EVENTS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(String.format(ExceptionMessages.USER_DOES_NOT_EXIST, userId), response);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User access validation")
    @Test
    @Description("Get events when user is client test")
    public void getEventsUserIsClient() {
        testUtils.logTestStart("Get events when user is client test");

        createEvent(new EventDTO(EventType.ACTIVATING_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.DELETE_CARD, new EventData("some data")));
        createEvent(new EventDTO(EventType.USER_SIGN_IN, new EventData("some data")));

        String userId = String.valueOf(testUtils.createClient(serviceProvider.getDataBaseService()));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, userId);

        MockHttpServletResponse response = httpGet(Urls.EVENTS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(ExceptionMessages.ACCESS_DENIED, response);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("Get events")
    @Description("Get events where required page: {0}, event type: {1}, event data {2}")
    @ParameterizedTest
    @ArgumentsSource(EventControllerArgumentsProvider.class)
    public void getEvents(String requiredPage, String eventType, String eventData, int expectedEventsCount,
                          int expectedCurrentPage, int expectedPagesCount) throws UnsupportedEncodingException {
        testUtils.logTestStart("Get events with filters test");

        int userId = testUtils.createNotAClient(serviceProvider.getDataBaseService());
        createEvent(new EventDTO(EventType.ACTIVATING_CARD, new EventData("some data 1")));
        createEvent(new EventDTO(EventType.DELETE_CARD, new EventData("some data 2")));
        createEvent(new EventDTO(EventType.USER_SIGN_IN, new EventData("some data 3")));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(userId));
        params.set(RequestParams.REQUIRED_PAGE, requiredPage);
        params.set(RequestParams.EVENT_TYPE, eventType);
        params.set(RequestParams.EVENT_DATA, eventData);

        MockHttpServletResponse response = httpGet(Urls.EVENTS, params);

        httpAsserts.checkResponseCode(200, response.getStatus());
        Events events = gson.fromJson(response.getContentAsString(), Events.class);
        eventAsserts.checkNumberOfEvents(expectedEventsCount, events.getEvents().size());
        httpAsserts.checkCurrentPage(expectedCurrentPage, response);
        httpAsserts.checkPagesCount(expectedPagesCount, response);
    }

    @Step("Creating event {0}")
    private void createEvent(EventDTO eventDTO) {
        serviceProvider.getEventService().addEvent(eventDTO);
    }
}
