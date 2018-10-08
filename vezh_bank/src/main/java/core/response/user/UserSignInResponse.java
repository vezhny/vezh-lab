package core.response.user;

import core.dto.UserDTO;
import core.exceptions.FailedAuthorizationException;
import core.response.VezhBankResponse;
import core.services.ServiceProvider;
import core.validators.UserRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.DatePatterns;
import vezh_bank.constants.ExceptionMessages;
import vezh_bank.constants.RequestParams;
import vezh_bank.constants.UserDefault;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.util.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        UserDTO user = null;

        try {
            user = getEntity(requestParams.get(RequestParams.LOGIN));

            userBlocked(user);
            comparePasswords(user, requestParams.get(RequestParams.PASSWORD));


            ResponseEntity responseEntity = new ResponseEntity(user.getId(), HttpStatus.OK);
            return responseEntity;

        } catch (FailedAuthorizationException e) {
            if (user != null) {
                removeAttempt(user.getEntity());
            }
            return error(e);
        }
    }

    private void userBlocked(UserDTO user) throws FailedAuthorizationException {
        if (user.isBlocked()) {
            logger.info("User is blocked");
            throw new FailedAuthorizationException(ExceptionMessages.USER_IS_BLOCKED, user.getLogin());
        }
    }

    private void comparePasswords(UserDTO user, String password) throws FailedAuthorizationException {
        if (!user.getPassword().equals(password)) {
            logger.info("Passwords doesn't match");
            throw new FailedAuthorizationException(ExceptionMessages.INVALID_LOGIN_OR_PASSWORD);
        }
        signInSuccess(user);
    }

    private void removeAttempt(User entity) {
        if (entity.getAttemptsToSignIn() == 1) {
            logger.info("Blocking user");
            entity.setBlocked(true);
            entity.setAttemptsToSignIn(entity.getAttemptsToSignIn() - 1);
        } else if (entity.getAttemptsToSignIn() > 1) {
            logger.info("Removing attempt to sign in");
            entity.setAttemptsToSignIn(entity.getAttemptsToSignIn() - 1);
        }
        serviceProvider.getDataBaseService().getUserDao().update(entity);
    }

    private UserDTO getEntity(String login) throws FailedAuthorizationException {
        User entity = serviceProvider.getDataBaseService().getUserDao().select(login);
        if (entity == null) {
            throw new FailedAuthorizationException(ExceptionMessages.INVALID_LOGIN_OR_PASSWORD);
        }
        UserRole role = serviceProvider.getDataBaseService().getRoleDao().get(entity.getRole());
        UserDTO user = new UserDTO(entity, role);
        return user;
    }

    private void signInSuccess(UserDTO user) {
        user.setAttemptsToSignIn(UserDefault.ATTEMPTS_TO_SIGN_IN);
        user.setLastSignIn(new SimpleDateFormat(DatePatterns.DEFAULT_DATE_PATTERN).format(new Date()));
        serviceProvider.getUserService().updateUser(user.getEntity(), user.getPassword(),
                user.getData(), user.getConfig(), user.getAttemptsToSignIn(), user.getLastSignIn(), user.isBlocked());
    }
}
