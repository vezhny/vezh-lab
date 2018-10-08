package core.dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import core.json.EventData;
import vezh_bank.enums.EventType;
import vezh_bank.persistence.entity.Event;

public class EventDTO implements BaseDTO<Event> {
    @Expose
    private int id;

    @Expose
    private EventType type;

    @Expose
    private EventData eventData;

    @Expose
    private String date;

    private transient Event event;

    public EventDTO(EventType type, EventData eventData) {
        this.type = type;
        this.eventData = eventData;
    }

    public EventDTO(Event event) {
        this.id = event.getId();
        this.date = event.getDate();
        this.eventData = new Gson().fromJson(event.getData(), EventData.class);
        this.type = EventType.valueOf(event.getType());
        this.event = event;
    }

    @Override
    public Event getEntity() {
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
