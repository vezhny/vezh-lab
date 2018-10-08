package core.handlers;

import core.response.VezhBankResponse;
import core.response.user.*;
import core.services.ServiceProvider;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.RequestParams;
import vezh_bank.enums.RequestType;
import vezh_bank.util.Logger;

import java.util.HashMap;
import java.util.Map;

public class UserRequestHandler implements RequestHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    private Map<String, String> requestParams;
    private VezhBankResponse vezhBankResponse;
    private RequestType requestType;
    private ServiceProvider serviceProvider;

    public UserRequestHandler(Map<String, String> requestParams, ServiceProvider serviceProvider, RequestType requestType) {
        this.requestParams = requestParams;
        this.requestType = requestType;
        this.serviceProvider = serviceProvider;
    }

    public UserRequestHandler(String login, ServiceProvider serviceProvider, RequestType requestType) {
        this.requestType = requestType;
        requestParams = new HashMap<>();
        requestParams.put(RequestParams.LOGIN, login);
        this.serviceProvider = serviceProvider;
    }

    @Override
    public ResponseEntity getResponse() {
        logRequestParams(logger, requestParams);
        switch (requestType) {
            case GET_USERS: {
                vezhBankResponse = new GetUsersResponse(serviceProvider, requestParams);
                break;
            }
            case DELETE_USER: {
                vezhBankResponse = new DeleteUserResponse(serviceProvider, requestParams);
                break;
            }
            case UPDATE_USER: {
                vezhBankResponse = new UpdateUserResponse(serviceProvider, requestParams);
                break;
            }
            case REGISTER_USER: {
                vezhBankResponse = new RegisterUserResponse(serviceProvider, requestParams);
                break;
            }
            case IS_USER_UNIQUE: {
                vezhBankResponse = new UserUniqueResponse(serviceProvider, requestParams.get(RequestParams.LOGIN));
                break;
            }
            case USER_SIGN_IN: {
                vezhBankResponse = new UserSignInResponse(serviceProvider, requestParams);
                break;
            }
            case GET_USER: {
                vezhBankResponse = new GetUserResponse(serviceProvider, requestParams);
                break;
            }
            case BLOCK_USER: {
                vezhBankResponse = new BlockUserResponse(serviceProvider, requestParams);
                break;
            }
        }
        ResponseEntity response = vezhBankResponse.build();
        logger.info(response.toString());
        return response;
    }
}
