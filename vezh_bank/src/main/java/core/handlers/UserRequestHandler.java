package core.handlers;

import core.dto.UserDTO;
import core.dto.UserRoleDTO;
import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import core.json.UserAddress;
import core.json.UserData;
import core.services.ServiceProvider;
import core.validators.UserRequestValidator;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.RequestParams;
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
    }

    @Override
    public ResponseEntity getResponse(HttpMethod httpMethod) {
        logRequestParams(logger, requestParams);
        try {
            userRequestValidator = new UserRequestValidator(serviceProvider.getDataBaseService(), requestParams);
            if (HttpMethod.POST.equals(httpMethod)) {
                return registerUserResponse();
            }
        } catch (ServerErrorException e) {
            return error(e);
        } catch (BadRequestException e) {
            return error(e);
        }
        return null;
    }

    private ResponseEntity registerUserResponse() throws BadRequestException {
        logger.info("User registration");
        userRequestValidator.checkUserRegistrationParams();
        String login = requestParams.get(RequestParams.LOGIN);
        String password = requestParams.get(RequestParams.PASSWORD);
        UserRoleDTO roleDTO = new UserRoleDTO(requestParams.get(RequestParams.ROLE));
        String firstName = requestParams.get(RequestParams.FIRST_NAME);
        String middleName = requestParams.get(RequestParams.MIDDLE_NAME);
        String patronymic = requestParams.get(RequestParams.PATRONYMIC);
        String birthDate = requestParams.get(RequestParams.BIRTH_DATE);
        String country = requestParams.get(RequestParams.COUNTRY);
        String region = requestParams.get(RequestParams.REGION);
        String city = requestParams.get(RequestParams.CITY);
        String street = requestParams.get(RequestParams.STREET);
        String house = requestParams.get(RequestParams.HOUSE);
        String housePart = requestParams.get(RequestParams.HOUSE_PART);
        String room = requestParams.get(RequestParams.ROOM);
        UserAddress userAddress = new UserAddress(country, region, city, street, house, housePart, room);
        String contactNumber = requestParams.get(RequestParams.CONTACT_NUMBER);
        String email = requestParams.get(RequestParams.EMAIL);
        UserData userData = new UserData(firstName, middleName, patronymic, birthDate, userAddress,
                contactNumber, email);
        UserDTO userDTO = new UserDTO(login, password, roleDTO, userData);

        serviceProvider.getDataBaseService().getUserDao().insert(userDTO.getEntity());
        return new ResponseEntity(HttpStatus.OK);
    }
}