package core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezh_bank.persistence.DataBaseService;

@Service
public class ServiceProvider {

    @Autowired
    private DataBaseService dataBaseService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    public ServiceProvider() {
        eventService = new EventService(dataBaseService);
    }

    public DataBaseService getDataBaseService() {
        return dataBaseService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public UserService getUserService() {
        return userService;
    }
}
