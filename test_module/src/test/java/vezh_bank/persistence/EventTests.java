package vezh_bank.persistence;

import core.config.VezhBankConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import vezh_bank.TestUtils;
import vezh_bank.constants.MavenProfiles;
import vezh_bank.enums.EventType;
import vezh_bank.persistence.entity.Event;
import vezh_bank.persistence.providers.event.EventPagesArgumentsProvider;
import vezh_bank.persistence.providers.event.SelectEventArgumentsProvider;
import vezh_bank.util.Logger;

import java.util.List;

public class EventTests extends PersistenceTest {

    @Test
    public void insertEventTest() {
        testUtils.logTestStart("Insert event test");
        EventType eventType = EventType.USER_SIGN_IN;
        String eventData = "Test data";
        createEvent(new Event(eventType, eventData));

        List<Event> events = dataBaseService.getEventDao().selectAll();
        checkEventsCount(1, events);
        checkEvent(eventType, eventData, events.get(0));
    }

    @Test
    public void selectAllEventsTest() throws InterruptedException {
        testUtils.logTestStart("Select all events test");
        EventType eventType = EventType.USER_SIGN_IN;
        String eventData1 = "Test data 1";
        String eventData2 = "Test data 2";
        createEvent(new Event(eventType, eventData1));
        Thread.sleep(2000);
        createEvent(new Event(eventType, eventData2));

        List<Event> events = dataBaseService.getEventDao().selectAll();
        checkEventsCount(2, events);
    }

    @Test
    public void getEventByIdTest() {
        testUtils.logTestStart("Select event by ID test");
        EventType eventType = EventType.USER_SIGN_IN;
        String eventData = "Test data 1";
        createEvent(new Event(eventType, eventData));

        Event event = dataBaseService.getEventDao().selectAll().get(0);
        event = dataBaseService.getEventDao().getById(event.getId());
        checkEvent(eventType, eventData, event);
    }

    @ParameterizedTest
    @ArgumentsSource(SelectEventArgumentsProvider.class)
    public void selectEventTest(String eventType, String date, String data, int expectedEventCount) {
        testUtils.logTestStart("Select event test");
        createEvent(new Event(EventType.USER_SIGN_IN, "User ID: 1"));
        createEvent(new Event(EventType.USER_SIGN_OUT, "User ID: 1"));
        createEvent(new Event(EventType.USER_SIGN_IN, "User ID: 2"));
        createEvent(new Event(EventType.USER_SIGN_OUT, "User ID: 2"));
        createEvent(new Event(EventType.USER_SIGN_IN, "User ID: 3"));
        createEvent(new Event(EventType.USER_SIGN_OUT, "User ID: 3"));
        createEvent(new Event(EventType.USER_SIGN_UP, "User ID: 3"));

        List<Event> events = dataBaseService.getEventDao().select(eventType, date, data);
        checkEventsCount(expectedEventCount, events);
    }

    @Test
    public void selectCountTest() {
        testUtils.logTestStart("Select count test");
        createEvent(new Event(EventType.USER_SIGN_IN, "User ID: 1"));
        createEvent(new Event(EventType.USER_SIGN_IN, "User ID: 1"));

        Assertions.assertEquals(2, dataBaseService.getEventDao().selectCount(), "Events count");
    }

    @ParameterizedTest
    @ArgumentsSource(EventPagesArgumentsProvider.class)
    public void selectPagesTest(int requiredPage, int rowsOnPage,
                                String type, String date, String data,
                                int expectedEventCount) {
        testUtils.logTestStart("Select pages test");
        createEvent(new Event(EventType.USER_SIGN_IN, "User ID: 1"));
        createEvent(new Event(EventType.USER_SIGN_OUT, "User ID: 1"));
        createEvent(new Event(EventType.USER_SIGN_IN, "User ID: 2"));
        createEvent(new Event(EventType.USER_SIGN_OUT, "User ID: 2"));
        createEvent(new Event(EventType.USER_SIGN_IN, "User ID: 3"));
        createEvent(new Event(EventType.USER_SIGN_OUT, "User ID: 3"));
        createEvent(new Event(EventType.USER_SIGN_UP, "User ID: 3"));

        List<Event> events = dataBaseService.getEventDao().select(requiredPage, rowsOnPage,
                type, date, data);
        checkEventsCount(expectedEventCount, events);
    }
}
