package core.response.user;

import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import core.response.VezhBankResponse;
import core.services.ServiceProvider;
import core.validators.UserRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vezh_bank.util.Logger;

public class UserUniqueResponse implements VezhBankResponse {
    private Logger logger = Logger.getLogger(this.getClass());
    private ServiceProvider serviceProvider;
    private String login;
    private UserRequestValidator userRequestValidator;

    public UserUniqueResponse(ServiceProvider serviceProvider, String login) {
        this.serviceProvider = serviceProvider;
        this.login = login;
    }

    @Override
    public ResponseEntity build() {
        logger.info("Is user unique");
        try {
            userRequestValidator = new UserRequestValidator(serviceProvider.getDataBaseService());
            userRequestValidator.checkLogin(login);
        } catch (ServerErrorException e) {
            return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return error(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
