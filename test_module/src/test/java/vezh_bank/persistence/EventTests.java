package vezh_bank.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import vezh_bank.enums.EventType;
import vezh_bank.extended_tests.PersistenceTest;
import vezh_bank.persistence.entity.Event;
import vezh_bank.persistence.providers.event.EventPagesArgumentsProvider;
import vezh_bank.persistence.providers.event.SelectEventArgumentsProvider;

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
    @ParameterizedTest
    @ArgumentsSource(SelectEventArgumentsProvider.class)
    public void selectEventCountTest(String eventType, String date, String data, int expectedEventCount) {
        testUtils.logTestStart("Select event count test");
        createEvent(new Event(EventType.USER_SIGN_IN, "User ID: 1"));
        createEvent(new Event(EventType.USER_SIGN_OUT, "User ID: 1"));
        createEvent(new Event(EventType.USER_SIGN_IN, "User ID: 2"));
        createEvent(new Event(EventType.USER_SIGN_OUT, "User ID: 2"));
        createEvent(new Event(EventType.USER_SIGN_IN, "User ID: 3"));
        createEvent(new Event(EventType.USER_SIGN_OUT, "User ID: 3"));
        createEvent(new Event(EventType.USER_SIGN_UP, "User ID: 3"));

        int events = dataBaseService.getEventDao().selectCount(eventType, date, data);
        Assertions.assertEquals(expectedEventCount, events, "Number of events");
    }

}
