package core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezh_bank.persistence.DataBaseService;

@Service
public class ServiceProvider {

    @Autowired
    private DataBaseService dataBaseService;

    private EventService eventService;

    public ServiceProvider() {
        eventService = new EventService(dataBaseService);
    }

    public DataBaseService getDataBaseService() {
        return dataBaseService;
    }

    public EventService getEventService() {
        return eventService;
    }
}
