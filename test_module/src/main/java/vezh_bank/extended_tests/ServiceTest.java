package vezh_bank.extended_tests;

import core.dto.EventDTO;
import core.json.EventData;
import org.junit.jupiter.api.Assertions;
import vezh_bank.enums.EventType;
import vezh_bank.persistence.entity.Event;

public class ServiceTest extends RootTest {
    protected void checkEvent(EventType expectedEventType, EventData expectedEventData, Event actualEvent) {
        EventDTO eventDTO = new EventDTO(actualEvent);

        Assertions.assertNotNull(eventDTO.getDate(), "Date");
        Assertions.assertEquals(expectedEventType, eventDTO.getType());
        Assertions.assertEquals(expectedEventData.toString(), eventDTO.getEventData().toString());
    }
}
