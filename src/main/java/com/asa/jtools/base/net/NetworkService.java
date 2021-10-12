package com.asa.jtools.base.net;

import com.asa.base.utils.MapUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author andrew_asa
 * @date 2021/10/9.
 */
public class NetworkService {

    private RestTemplate restTemplate = new RestTemplate();

    public <T> T GET(String url, Map<String, Object> params,
                     Class<T> responseTyp) {

        HttpHeaders headers = customRequireHeader();
        if (params == null) {
            params = new HashMap<>();
        }
        if (MapUtils.isNotEmptyMap(params)) {
            url = addURIVariables(url, params.keySet().toArray(new String[0]));
        }
        HttpEntity<T> res = restTemplate
                .exchange(url, HttpMethod.GET, new HttpEntity<Map>(null, headers),
                          responseTyp, params);
        T body = res.getBody();
        return body;
    }

    private HttpHeaders customRequireHeader() {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT,
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
        return headers;
    }

    public static String addURIVariables(String url, String... uriVariables) {

        StringBuffer ret = new StringBuffer();
        ret.append(url);
        if (uriVariables.length > 0) {
            ret.append("?");
            for (String uriVariable : uriVariables) {
                ret.append(uriVariable + "={" + uriVariable + "}&");
            }
        }
        return ret.toString();
    }

    public <T> T GET(String url, Class<T> responseTyp) {

        return GET(url, (Map<String, Object>) null,responseTyp);
    }

    public Map GET(String url, Map<String, Object> params) {

        return GET(url, params, Map.class);
    }

    public Map GET(String url) {

        return GET(url, (Map<String, Object>) null);
    }
}
