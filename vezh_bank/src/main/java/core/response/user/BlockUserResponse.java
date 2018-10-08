package core.response.user;

import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import core.response.VezhBankResponse;
import core.services.ServiceProvider;
import core.validators.UserRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.RequestParams;
import vezh_bank.enums.UserAccess;
import vezh_bank.persistence.entity.User;
import vezh_bank.util.Logger;
import vezh_bank.util.TypeConverter;

import java.util.Map;

public class BlockUserResponse implements VezhBankResponse {
    private Logger logger = Logger.getLogger(this.getClass());
    private ServiceProvider serviceProvider;
    private Map<String, String> requestParams;
    private UserRequestValidator userRequestValidator;

    public BlockUserResponse(ServiceProvider serviceProvider, Map<String, String> requestParams) {
        this.serviceProvider = serviceProvider;
        this.requestParams = requestParams;
    }

    @Override
    public ResponseEntity build() {
        logger.info("Block user");

        try {
            userRequestValidator = new UserRequestValidator(serviceProvider.getDataBaseService(), requestParams);
            userRequestValidator.checkUserId(requestParams.get(RequestParams.USER_ID), UserAccess.ADMIN_ONLY);
            userRequestValidator.checkUserId(requestParams.get(RequestParams.TARGET_ID), UserAccess.ANY);
        } catch (ServerErrorException e) {
            return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return error(e);
        }

        User entity = serviceProvider.getDataBaseService().getUserDao()
                .getById(TypeConverter.stringToInt(requestParams.get(RequestParams.TARGET_ID)));
        serviceProvider.getUserService().blockUser(entity, serviceProvider.getEventService());

        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        return response;
    }
}
