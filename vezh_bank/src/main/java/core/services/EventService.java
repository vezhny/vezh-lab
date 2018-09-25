package core.services;

import core.dto.EventDTO;
import org.springframework.stereotype.Service;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.Event;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private DataBaseService dataBaseService;

    public EventService(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    public void addEvent(EventDTO eventDTO) {
        Event event = new Event(eventDTO.getType(), eventDTO.getEventData().toString());
        dataBaseService.getEventDao().insert(event);
    }

    public List<EventDTO> getEvents(List<Event> events) {
        List<EventDTO> eventDTOS = new ArrayList<>();
        for (Event event : events) {
            eventDTOS.add(new EventDTO(event));
        }
        return eventDTOS;
    }
}
