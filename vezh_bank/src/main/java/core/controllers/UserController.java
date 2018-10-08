package core.controllers;

import core.dto.UserDTO;
import core.handlers.RequestHandler;
import core.handlers.UserRequestHandler;
import core.services.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vezh_bank.constants.RequestParams;
import vezh_bank.constants.Urls;
import vezh_bank.enums.RequestType;
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
        RequestHandler requestHandler = new UserRequestHandler(params, serviceProvider, RequestType.REGISTER_USER);
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
        RequestHandler requestHandler = new UserRequestHandler(params, serviceProvider, RequestType.GET_USERS);
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
        RequestHandler requestHandler = new UserRequestHandler(params, serviceProvider, RequestType.DELETE_USER);
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
    public ResponseEntity isUserUnique(@RequestParam(name = "login") String login) {
        logStartOperation(logger);
        RequestHandler requestHandler = new UserRequestHandler(login, serviceProvider, RequestType.IS_USER_UNIQUE);
        ResponseEntity responseEntity = requestHandler.getResponse();
        logEndOperation(logger);
        return responseEntity;
    }

    /**
     * Required params: userId, updatingUserId, password, country, region, city, street, house, room, firstName, middleName,
     * patronymic, birthDate, contactNumber, cardsOnPage, currenciesOnPage, eventsOnPage, paymentsOnPage, transactionsOnPage,
     * usersOnPage, userRequestsOnPage
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestParam Map<String, String> params) {
        logStartOperation(logger);
        RequestHandler requestHandler = new UserRequestHandler(params, serviceProvider, RequestType.UPDATE_USER);
        ResponseEntity responseEntity = requestHandler.getResponse();
        logEndOperation(logger);
        return responseEntity;
    }

    /**
     * Required params: login, password
     * @param params
     * @return
     */
    @RequestMapping(value = Urls.SIGN_IN, method = RequestMethod.POST)
    public ResponseEntity signIn(@RequestParam Map<String, String> params) {
        logStartOperation(logger);
        RequestHandler requestHandler = new UserRequestHandler(params, serviceProvider, RequestType.USER_SIGN_IN);
        ResponseEntity responseEntity = requestHandler.getResponse();
        logEndOperation(logger);
        return responseEntity;
    }

    /**
     * Required params: userId, targetId
     * @param params
     * @param targetId
     * @return
     */
    @RequestMapping(value = "/{targetId}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUser(@RequestParam Map<String, String> params,
                                           @PathVariable("targetId") String targetId) {
        logStartOperation(logger);
        params.put(RequestParams.TARGET_ID, targetId);
        RequestHandler requestHandler = new UserRequestHandler(params, serviceProvider, RequestType.GET_USER);
        ResponseEntity responseEntity = requestHandler.getResponse();
        logEndOperation(logger);
        return responseEntity;
    }

    /**
     * Required params: userId, targetId
     * @param params
     * @param targetId
     * @return
     */
    @RequestMapping(value = "/{targetId}" + Urls.BLOCK, method = RequestMethod.PUT)
    public ResponseEntity blockUser(@RequestParam Map<String, String> params,
                                    @PathVariable("targetId") String targetId) {
        logStartOperation(logger);
        params.put(RequestParams.TARGET_ID, targetId);
        RequestHandler requestHandler = new UserRequestHandler(params, serviceProvider, RequestType.BLOCK_USER);
        ResponseEntity responseEntity = requestHandler.getResponse();
        logEndOperation(logger);
        return responseEntity;
    }
}
