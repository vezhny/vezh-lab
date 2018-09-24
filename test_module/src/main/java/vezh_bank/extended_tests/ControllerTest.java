package vezh_bank.extended_tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ControllerTest extends RootTest {

    @Step("Http GET to {0}")
    protected MockHttpServletResponse httpGet(String url, MultiValueMap<String, String> params) {
        try {
            return mockMvc.perform(get(url).params(params)).andReturn().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Step("Http POST to {0}")
    protected MockHttpServletResponse httpPost(String url, MultiValueMap<String, String> params) {
        try {
            return mockMvc.perform(post(url).params(params)).andReturn().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Step("Check response code. Expected {0}. Actual {1}")
    protected void checkResponseCode(int expectedCode, int actualCode) {
        Assertions.assertEquals(expectedCode, actualCode, "Response code");
    }

    private String getQueryString(Map<String, String> params) {
        String query = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            query += entry.getKey() + "=" + entry.getValue() + "&";
        }
        return query;
    }
}
