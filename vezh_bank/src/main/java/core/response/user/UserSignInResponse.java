package core.response.user;

import core.dto.UserDTO;
import core.exceptions.FailedAuthorizationException;
import core.exceptions.ServerErrorException;
import core.response.VezhBankResponse;
import core.services.ServiceProvider;
import core.validators.UserRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.ExceptionMessages;
import vezh_bank.constants.RequestParams;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.util.Logger;

import java.util.Map;

public class UserSignInResponse implements VezhBankResponse {
    private Logger logger = Logger.getLogger(this.getClass());
    private ServiceProvider serviceProvider;
    private Map<String, String> requestParams;
    private UserRequestValidator userRequestValidator;

    public UserSignInResponse(ServiceProvider serviceProvider, Map<String, String> requestParams) {
        this.serviceProvider = serviceProvider;
        this.requestParams = requestParams;
    }

    @Override
    public ResponseEntity build() {
        logger.info("User sign in");
        UserDTO user;

        try {
            userRequestValidator = new UserRequestValidator(serviceProvider.getDataBaseService(), requestParams);
            userRequestValidator.checkUserSignInParams();

            User entity = serviceProvider.getDataBaseService().getUserDao().select(requestParams.get(RequestParams.LOGIN));
            UserRole role = serviceProvider.getDataBaseService().getRoleDao().get(entity.getRole());
            user = new UserDTO(entity, role);

            userBlocked(user);
            comparePasswords(entity, requestParams.get(RequestParams.PASSWORD));

            ResponseEntity responseEntity = new ResponseEntity(entity.getId(), HttpStatus.OK);
            return responseEntity;

        } catch (ServerErrorException e) {
            return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FailedAuthorizationException e) {
            return error(e);
        }
    }

    private void userBlocked(UserDTO user) throws FailedAuthorizationException {
        if (user.isBlocked()) {
            throw new FailedAuthorizationException(ExceptionMessages.USER_IS_BLOCKED, user.getLogin());
        }
    }

    private void comparePasswords(User entity, String password) throws FailedAuthorizationException {
        if (!entity.getPassword().equals(password)) {
            removeAttempt(entity);
            throw new FailedAuthorizationException(ExceptionMessages.INVALID_LOGIN_OR_PASSWORD);
        }
    }

    private void removeAttempt(User entity) {
        if (entity.getAttemptsToSignIn() == 1) {
            entity.setBlocked(true);
        }
        entity.setAttemptsToSignIn(entity.getAttemptsToSignIn() - 1);
    }
}
