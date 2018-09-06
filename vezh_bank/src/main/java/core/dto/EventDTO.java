package core.dto;

import com.google.gson.Gson;
import core.json.EventData;
import vezh_bank.enums.EventType;
import vezh_bank.persistence.entity.Event;

public class EventDTO extends AbstractDTO implements BaseDTO<Event> {
    private int id;
    private EventType type;
    private EventData eventData;
    private String date;

    public EventDTO(EventType type, EventData eventData) {
        this.type = type;
        this.eventData = eventData;
    }

    public EventDTO(Event event) {
        this.id = event.getId();
        this.date = event.getDate();
        this.eventData = new Gson().fromJson(event.getData(), EventData.class);
        this.type = EventType.valueOf(event.getType());
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

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "id=" + id +
                ", type=" + type +
                ", eventData=" + eventData +
                ", date='" + date + '\'' +
                '}';
    }
}
