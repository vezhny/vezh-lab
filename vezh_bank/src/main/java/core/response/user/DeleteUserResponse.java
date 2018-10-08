package core.response.user;

import core.dto.EventDTO;
import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import core.json.EventData;
import core.response.VezhBankResponse;
import core.services.ServiceProvider;
import core.validators.UserRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.EventDescriptions;
import vezh_bank.constants.RequestParams;
import vezh_bank.enums.EventType;
import vezh_bank.enums.UserAccess;
import vezh_bank.persistence.entity.User;
import vezh_bank.util.Logger;

import java.util.Map;

import static vezh_bank.util.TypeConverter.stringToInt;

public class DeleteUserResponse implements VezhBankResponse {
    private Logger logger = Logger.getLogger(this.getClass());
    private ServiceProvider serviceProvider;
    private Map<String, String> requestParams;
    private UserRequestValidator userRequestValidator;

    public DeleteUserResponse(ServiceProvider serviceProvider, Map<String, String> requestParams) {
        this.serviceProvider = serviceProvider;
        this.requestParams = requestParams;
    }

    @Override
    public ResponseEntity build() {
        try {
            userRequestValidator = new UserRequestValidator(serviceProvider.getDataBaseService(), requestParams);
            userRequestValidator.checkUserId(requestParams.get(RequestParams.USER_ID), UserAccess.ADMIN_ONLY);
            userRequestValidator.checkDeletingUser();
        } catch (ServerErrorException e) {
            return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return error(e);
        }
        logger.info("Delete user");

        User deletingUser = serviceProvider.getDataBaseService().getUserDao()
                .getById(stringToInt(requestParams.get(RequestParams.DELETING_USER_ID)));
        User user = serviceProvider.getDataBaseService().getUserDao()
                .getById(stringToInt(requestParams.get(RequestParams.USER_ID)));

        serviceProvider.getDataBaseService().getUserDao().delete(deletingUser.getId());
        serviceProvider.getEventService().addEvent(new EventDTO(EventType.USER_DELETE,
                new EventData(EventDescriptions.userDeletedUser(user.getLogin(), user.getRole(),
                        deletingUser.getLogin(), deletingUser.getRole()))));
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
        return responseEntity;
    }
}
