package vezh_bank.special;

import core.json.UserAddress;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import vezh_bank.extended_tests.RootTest;

@Epic("Special")
@Story("User address: house part")
public class HousePartTests extends RootTest {

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("House part getting")
    @Link(name = "Issue", value = "https://github.com/vezhny/vezh-lab/issues/15", url = "https://github.com/vezhny/vezh-lab/issues/15")
    @Description("House part test")
    @Test
    public void housePart() {
        testUtils.logTestStart("House part test");

        String house = "2";
        String housePart = "3";
        UserAddress userAddress = new UserAddress("country", "region", "city", "street",
                house, housePart, "201");

        asserts.checkObject("2/3", userAddress.getHouse(), "House part");
    }
}
