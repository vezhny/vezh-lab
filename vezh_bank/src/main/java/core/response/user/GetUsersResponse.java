package core.response.user;

import core.dto.UserDTO;
import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import core.json.Users;
import core.response.VezhBankResponse;
import core.services.ServiceProvider;
import core.validators.UserRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.RequestParams;
import vezh_bank.enums.UserAccess;
import vezh_bank.persistence.entity.User;
import vezh_bank.util.Logger;
import vezh_bank.util.PageCounter;
import vezh_bank.util.TypeConverter;

import java.util.List;
import java.util.Map;

public class GetUsersResponse implements VezhBankResponse<Users> {
    private Logger logger = Logger.getLogger(this.getClass());
    private ServiceProvider serviceProvider;
    private Map<String, String> requestParams;
    private UserRequestValidator userRequestValidator;

    public GetUsersResponse(ServiceProvider serviceProvider, Map<String, String> requestParams) {
        this.serviceProvider = serviceProvider;
        this.requestParams = requestParams;
    }

    @Override
    public ResponseEntity<Users> build() {
        try {
            userRequestValidator = new UserRequestValidator(serviceProvider.getDataBaseService(), requestParams);
            userRequestValidator.checkUserId(requestParams.get(RequestParams.USER_ID), UserAccess.ADMIN_ONLY);
        } catch (ServerErrorException e) {
            return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return error(e);
        }

        User userEntity = serviceProvider.getDataBaseService().getUserDao()
                .getById(TypeConverter.stringToInt(requestParams.get(RequestParams.USER_ID)));
        UserDTO user = new UserDTO(userEntity, serviceProvider.getDataBaseService().getRoleDao().get(userEntity.getRole()));
        int rowsOnPage = user.getConfig().getUsersOnPage();
        logger.info("Rows on page: " + rowsOnPage);

        String login = requestParams.get(RequestParams.LOGIN);
        String role = requestParams.get(RequestParams.ROLE);
        String blocked = requestParams.get(RequestParams.BLOCKED);
        String data = requestParams.get(RequestParams.USER_DATA);

        int pagesCount = PageCounter.getPagesCount(rowsOnPage,
                serviceProvider.getDataBaseService().getUserDao().selectCount(login, role, blocked, data));

        int requiredPage = setRequiredPage(TypeConverter.stringToInt(requestParams.get(RequestParams.REQUIRED_PAGE),
                1), pagesCount);

        List<User> users = serviceProvider.getDataBaseService().getUserDao().select(login, role, blocked, data);

        List<UserDTO> userDTOS = serviceProvider.getUserService().getUsers(users);
        Users usersResponse = new Users(userDTOS);

        ResponseEntity<Users> response = getResponseEntity(usersResponse, requiredPage, pagesCount);
        return response;
    }
}
