package vezh_bank.assertions;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import vezh_bank.enums.UserRequestStatus;
import vezh_bank.persistence.entity.UserRequest;

import java.util.List;

public class UserRequestAsserts extends Asserts {

    @Step("Check user request. Expected user ID: {0}. Expected status: {1}. Expected data: {2}. Actual request: {3}")
    public void checkUserRequest(int expectedUserId, UserRequestStatus expectedRequestStatus,
                                 String expectedData, UserRequest actualUserRequest) {
        checkObject(expectedUserId, actualUserRequest.getUser().getId(), "User ID");
        checkObject(expectedRequestStatus.toString(), actualUserRequest.getStatus(), "Status");
        checkObject(expectedData, actualUserRequest.getData(), "Data");
    }

    public void checkUserRequestsCount(int expectedCount, List<UserRequest> userRequests) {
        checkItemsCount(expectedCount, userRequests, "Number of user requests");
    }
}
