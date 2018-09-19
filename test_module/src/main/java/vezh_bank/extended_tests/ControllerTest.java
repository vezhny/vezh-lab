package vezh_bank.extended_tests;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class ControllerTest extends RootTest {

    protected MockHttpServletResponse httpGet(String url, MultiValueMap<String, String> params) {
        try {
            return mockMvc.perform(get(url).params(params)).andReturn().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
