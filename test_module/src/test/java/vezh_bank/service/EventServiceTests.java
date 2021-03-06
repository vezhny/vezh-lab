package vezh_bank.service;

import core.dto.EventDTO;
import core.json.EventData;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import vezh_bank.enums.EventType;
import vezh_bank.extended_tests.ServiceTest;
import vezh_bank.persistence.entity.Event;

import java.util.List;

@Epic("Service")
@Story("Event service")
@Link(name = "Issue", value = "https://github.com/vezhny/vezh-lab/issues/11", url = "https://github.com/vezhny/vezh-lab/issues/11")
public class EventServiceTests extends ServiceTest {

    @Severity(SeverityLevel.NORMAL)
    @Feature("Insert entity")
    @Description("Add event test")
    @Test
    public void addEventTest() {
        testUtils.logTestStart("Add event test");

        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        EventType eventType = EventType.ACTIVATING_CARD;
        EventData eventData = new EventData("User activated card");
        EventDTO eventDTO = new EventDTO(eventType, eventData);
        serviceProvider.getEventService().addEvent(eventDTO);

        List<Event> events = serviceProvider.getDataBaseService().getEventDao().selectAll();
        eventAsserts.checkNumberOfEvents(1, events.size());
        eventAsserts.checkEvent(eventType, eventData, events.get(0));
    }
}
