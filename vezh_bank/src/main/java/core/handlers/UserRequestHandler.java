package core.handlers;

import core.dto.UserDTO;
import core.services.ServiceProvider;
import core.validators.UserRequestValidator;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import vezh_bank.util.Logger;

import java.util.Map;

public class UserRequestHandler implements RequestHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    private ServiceProvider serviceProvider;
    private Map<String, String> requestParams;
    private UserRequestValidator userRequestValidator;

    public UserRequestHandler(ServiceProvider serviceProvider, Map<String, String> requestParams) {
        this.serviceProvider = serviceProvider;
        this.requestParams = requestParams;
        this.userRequestValidator = new UserRequestValidator(serviceProvider.getDataBaseService(),
                requestParams);
    }

    @Override
    public ResponseEntity getResponse(HttpMethod httpMethod) {
        if (httpMethod.equals(HttpMethod.POST)) {

        }
        return null;
    }

    private ResponseEntity<UserDTO> registerUserResponse() {

    }
}
