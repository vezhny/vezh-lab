package core.controllers;

import core.handlers.RequestHandler;
import core.handlers.UserRequestHandler;
import core.json.Users;
import core.response.VezhBankResponse;
import core.response.user.DeleteUserResponse;
import core.response.user.GetUsersResponse;
import core.response.user.RegisterUserResponse;
import core.response.user.UserUniqueResponse;
import core.services.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController implements BaseController {
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
        logStartOperation(logger);
        RequestHandler requestHandler = new UserRequestHandler(params, new RegisterUserResponse(serviceProvider, params));
        ResponseEntity responseEntity = requestHandler.getResponse();
        logEndOperation(logger);
        return responseEntity;
    }

    /**
     * Required params: userId
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getUsers(@RequestParam Map<String, String> params) {
        logStartOperation(logger);
        VezhBankResponse<Users> response = new GetUsersResponse(serviceProvider, params);
        RequestHandler requestHandler = new UserRequestHandler(params, response);
        ResponseEntity responseEntity = requestHandler.getResponse();
        logEndOperation(logger);
        return responseEntity;
    }

    /**
     * Required params: userId, deletingUserId
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestParam Map<String, String> params) {
        logStartOperation(logger);
        VezhBankResponse response = new DeleteUserResponse(serviceProvider, params);
        RequestHandler requestHandler = new UserRequestHandler(params, response);
        ResponseEntity responseEntity = requestHandler.getResponse();
        logEndOperation(logger);
        return responseEntity;
    }

    /**
     * Required params: login
     * @param login
     * @return
     */
    @RequestMapping(value = Urls.IS_UNIQUE, method = RequestMethod.GET)
    public ResponseEntity<Boolean> isUserUnique(@RequestParam(name = "login") String login) {
        logStartOperation(logger);
        VezhBankResponse<Boolean> response = new UserUniqueResponse(serviceProvider, login);
        RequestHandler requestHandler = new UserRequestHandler(login, response);
        ResponseEntity responseEntity = requestHandler.getResponse();
        logEndOperation(logger);
        return responseEntity;
    }
}
