package core.controllers;

import core.services.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vezh_bank.constants.Urls;
import vezh_bank.util.Logger;

@Controller
@RequestMapping(Urls.USERS)
public class UserController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ServiceProvider serviceProvider;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<>
}
