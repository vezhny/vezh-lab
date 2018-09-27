package vezh_bank.extended_tests;

import io.qameta.allure.Step;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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

    @Step("Http DELETE to {0}")
    protected MockHttpServletResponse httpDelete(String url, MultiValueMap<String, String> params) {
        try {
            return mockMvc.perform(delete(url).params(params)).andReturn().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
