package core.controllers;

import core.handlers.RequestHandler;
import core.handlers.UserRequestHandler;
import core.services.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vezh_bank.constants.Urls;
import vezh_bank.util.Logger;

import java.util.Map;

@Controller
@RequestMapping(Urls.USERS)
public class UserController { //TODO: log start/end operation
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ServiceProvider serviceProvider;

    /**
     * Required params: login, password, role, country, region, city, street, house, room, firstName, middleName,
     * patronymic, contactNumber
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestParam Map<String, String> params) {
        RequestHandler requestHandler = new UserRequestHandler(serviceProvider, params);
        return requestHandler.getResponse(HttpMethod.POST);
    }
}
