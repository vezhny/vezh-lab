package core.json;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import core.dto.EventDTO;

import java.util.List;

public class Events {
    @Expose
    private List<EventDTO> events;

    public Events(List<EventDTO> events) {
        this.events = events;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
