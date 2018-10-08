package core.response.user;

import core.dto.UserDTO;
import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import core.response.VezhBankResponse;
import core.services.ServiceProvider;
import core.validators.UserRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.RequestParams;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.util.Logger;
import vezh_bank.util.TypeConverter;

import java.util.Map;

public class GetUserResponse implements VezhBankResponse<UserDTO> {
    private Logger logger = Logger.getLogger(this.getClass());
    private ServiceProvider serviceProvider;
    private Map<String, String> requestParams;
    private UserRequestValidator userRequestValidator;

    public GetUserResponse(ServiceProvider serviceProvider, Map<String, String> requestParams) {
        this.serviceProvider = serviceProvider;
        this.requestParams = requestParams;
    }

    @Override
    public ResponseEntity<UserDTO> build() {
        logger.info("Get user");

        try {
            userRequestValidator = new UserRequestValidator(serviceProvider.getDataBaseService(), requestParams);
            userRequestValidator.checkGetUserParams();
        } catch (ServerErrorException e) {
            return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return error(e);
        }

        User entity = serviceProvider.getDataBaseService().getUserDao()
                .getById(TypeConverter.stringToInt(requestParams.get(RequestParams.TARGET_ID)));
        UserRole role = serviceProvider.getDataBaseService().getRoleDao().get(entity.getRole());
        UserDTO userDTO = new UserDTO(entity, role);

        ResponseEntity<UserDTO> response = new ResponseEntity<>(userDTO, HttpStatus.OK);
        return response;
    }
}
