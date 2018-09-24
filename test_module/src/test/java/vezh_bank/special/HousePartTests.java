package vezh_bank.special;

import core.json.UserAddress;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import vezh_bank.extended_tests.RootTest;

@Feature("User address: house part")
public class HousePartTests extends RootTest {

    @Description("House part test")
    @Link("https://github.com/vezhny/vezh-lab/issues/15")
    @Test
    public void housePart() {
        testUtils.logTestStart("House part test");

        String house = "2";
        String housePart = "3";
        UserAddress userAddress = new UserAddress("country", "region", "city", "street",
                house, housePart, "201");

        Assertions.assertEquals("2/3", userAddress.getHouse(), "House part");
    }
}
