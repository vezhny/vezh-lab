package vezh_bank.assertions;

import core.dto.EventDTO;
import core.json.EventData;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import vezh_bank.enums.EventType;
import vezh_bank.persistence.entity.Event;

import java.util.List;

public class EventAsserts extends Asserts {

    @Step("Check event")
    public void checkEvent(EventType expectedEventType, String containingData, Event actualEvent) {
        checkObject(expectedEventType.toString(), actualEvent.getType(), "Event type");
        checkObject(containingData, actualEvent.getData(), "Event data");
        checkNotNull(actualEvent.getDate(), "Event date");
    }

    public void checkEventsCount(int expectedEventCount, List<Event> events) {
        checkItemsCount(expectedEventCount, events, "Number of events");
    }

    public void checkEvent(EventType expectedEventType, EventData expectedEventData, Event actualEvent) {
        checkEvent(expectedEventType, expectedEventData.toString(), actualEvent);
    }

    @Step("Check number of events. Expected {0}. Actual {1}")
    public void checkNumberOfEvents(int expected, int actual) {
        checkNumber(expected, actual, "Number of events");
    }
}
