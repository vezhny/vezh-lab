package core.response.user;

import core.dto.EventDTO;
import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import core.json.EventData;
import core.json.UserAddress;
import core.json.UserConfig;
import core.json.UserData;
import core.response.VezhBankResponse;
import core.services.ServiceProvider;
import core.validators.UserRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vezh_bank.constants.EventDescriptions;
import vezh_bank.constants.RequestParams;
import vezh_bank.enums.EventType;
import vezh_bank.persistence.entity.User;
import vezh_bank.util.Logger;

import java.util.Map;

import static vezh_bank.util.TypeConverter.stringToInt;

public class UpdateUserResponse implements VezhBankResponse {
    private Logger logger = Logger.getLogger(this.getClass());
    private ServiceProvider serviceProvider;
    private Map<String, String> requestParams;
    private UserRequestValidator userRequestValidator;

    public UpdateUserResponse(ServiceProvider serviceProvider, Map<String, String> requestParams) {
        this.serviceProvider = serviceProvider;
        this.requestParams = requestParams;
    }

    @Override
    public ResponseEntity build() {
        logger.info("User update");

        try {
            userRequestValidator = new UserRequestValidator(serviceProvider.getDataBaseService(), requestParams);
            userRequestValidator.checkUpdateUserParams();
        } catch (ServerErrorException e) {
            return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return error(e);
        }

        User updater = serviceProvider.getDataBaseService().getUserDao()
                .getById(stringToInt(requestParams.get(RequestParams.USER_ID)));
        User victim = serviceProvider.getDataBaseService().getUserDao()
                .getById(stringToInt(requestParams.get(RequestParams.UPDATING_USER_ID)));

        String password = requestParams.get(RequestParams.PASSWORD);

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
        String contactNumber = requestParams.get(RequestParams.CONTACT_NUMBER);
        String email = requestParams.get(RequestParams.EMAIL);

        int cardsOnPage = stringToInt(requestParams.get(RequestParams.CARDS_ON_PAGE));
        int currenciesOnPage = stringToInt(requestParams.get(RequestParams.CURRENCIES_ON_PAGE));
        int eventsOnPage = stringToInt(requestParams.get(RequestParams.EVENTS_ON_PAGE));
        int paymentsOnPage = stringToInt(requestParams.get(RequestParams.PAYMENTS_ON_PAGE));
        int transactionsOnPage = stringToInt(requestParams.get(RequestParams.TRANSACTIONS_ON_PAGE));
        int usersOnPage = stringToInt(requestParams.get(RequestParams.USERS_ON_PAGE));
        int userRequestsOnPage = stringToInt(requestParams.get(RequestParams.USER_REQUESTS_ON_PAGE));

        UserAddress userAddress = new UserAddress(country, region, city, street, house, housePart, room);
        UserData userData = new UserData(firstName, middleName, patronymic, birthDate, userAddress,
                contactNumber, email);
        UserConfig userConfig = new UserConfig(cardsOnPage, currenciesOnPage, eventsOnPage, paymentsOnPage,
                transactionsOnPage, userRequestsOnPage, usersOnPage);

        serviceProvider.getUserService().updateUser(victim, password, userData, userConfig,
                victim.getAttemptsToSignIn(), victim.getLastSignIn(), victim.isBlocked());

        EventData eventData = new EventData(EventDescriptions.userHasBeenUpdatedBy(updater.getLogin(),
                updater.getRole(), victim.getLogin(), victim.getRole()));
        EventDTO eventDTO = new EventDTO(EventType.USER_UPDATE, eventData);
        serviceProvider.getEventService().addEvent(eventDTO);

        return new ResponseEntity(HttpStatus.OK);
    }
}
