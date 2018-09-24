package vezh_bank.extended_tests;

import core.dto.EventDTO;
import core.json.EventData;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import vezh_bank.enums.EventType;
import vezh_bank.persistence.entity.Event;

public class ServiceTest extends RootTest {

    @Step("Check event. Expected type: {0}. Expected data: {1}. Actual event: {2}")
    protected void checkEvent(EventType expectedEventType, EventData expectedEventData, Event actualEvent) {
        EventDTO eventDTO = new EventDTO(actualEvent);

        Assertions.assertNotNull(eventDTO.getDate(), "Date");
        Assertions.assertEquals(expectedEventType, eventDTO.getType());
        Assertions.assertEquals(expectedEventData.toString(), eventDTO.getEventData().toString());
    }

}
