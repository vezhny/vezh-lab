package core.services;

import core.dto.EventDTO;
import org.springframework.stereotype.Service;
import vezh_bank.persistence.DataBaseService;

@Service
public class EventService {
    // TODO: get events
// TODO: event tests
    private DataBaseService dataBaseService;

    public EventService(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    public void addEvent(EventDTO eventDTO) {
        dataBaseService.getEventDao().insert(eventDTO.getEntity());
    }
}
