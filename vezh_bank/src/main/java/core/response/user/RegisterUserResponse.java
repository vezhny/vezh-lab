package core.response.user;

import core.dto.EventDTO;
import core.dto.UserDTO;
import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import core.json.EventData;
import core.json.UserAddress;
import core.json.UserData;
import core.response.VezhBankResponse;
import core.services.ServiceProvider;
import core.validators.UserRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.EventDescriptions;
import vezh_bank.constants.RequestParams;
import vezh_bank.enums.EventType;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.util.Logger;

import java.util.Map;

public class RegisterUserResponse implements VezhBankResponse {
    private Logger logger = Logger.getLogger(this.getClass());
    private ServiceProvider serviceProvider;
    private Map<String, String> requestParams;
    private UserRequestValidator userRequestValidator;

    public RegisterUserResponse(ServiceProvider serviceProvider, Map<String, String> requestParams) {
        this.serviceProvider = serviceProvider;
        this.requestParams = requestParams;
    }

    @Override
    public ResponseEntity build() {
        try {
            userRequestValidator = new UserRequestValidator(serviceProvider.getDataBaseService(), requestParams);
            userRequestValidator.checkUserRegistrationParams();
        } catch (ServerErrorException e) {
            return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return error(e);
        }
        logger.info("User registration");

        String login = requestParams.get(RequestParams.LOGIN);
        String password = requestParams.get(RequestParams.PASSWORD);
        UserRole userRole = serviceProvider.getDataBaseService().getRoleDao().get(requestParams.get(RequestParams.ROLE));
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
        UserDTO userDTO = new UserDTO(login, password, userRole, userData);

        serviceProvider.getUserService().addUser(userDTO);
        EventDTO eventDTO = new EventDTO(EventType.USER_SIGN_UP,
                new EventData(EventDescriptions.registeredUserWithLogin(login)));
        serviceProvider.getEventService().addEvent(eventDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
