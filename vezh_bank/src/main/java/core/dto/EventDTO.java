package core.dto;

import core.json.EventData;
import vezh_bank.enums.EventType;
import vezh_bank.persistence.entity.Event;

public class EventDTO implements BaseDTO<Event> {
    private EventType type;
    private EventData eventData;

    public EventDTO(EventType type, EventData eventData) {
        this.type = type;
        this.eventData = eventData;
    }

    @Override
    public Event getEntity() {
        Event event = new Event(type, eventData.toString());
        return event;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EventData getEventData() {
        return eventData;
    }

    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "type=" + type +
                ", eventData=" + eventData +
                '}';
    }
}
