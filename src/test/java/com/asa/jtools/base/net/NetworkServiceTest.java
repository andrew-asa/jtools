package com.asa.jtools.base.net;

import junit.framework.TestCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author andrew_asa
 * @date 2021/10/9.
 */
public class NetworkServiceTest extends TestCase {

    public void testGET() throws Exception{
        //java -Djavax.net.debug=all -Djavax.net.ssl.trustStore=trustStore
        //System.setProperty("javax.net.debug","all");
        //System.setProperty("javax.net.ssl.trustStore","trustStore");
        NetworkService service = new NetworkService();
        String url = "https://cdn.jsdelivr.net/gh/521xueweihan/GitHub520@main/hosts";
        String host = "";
        //String host = service.GET("https://raw.hellogithub.com/hosts", String.class);
        RestTemplate restTemplate = new RestTemplate();
        String obj  = restTemplate.getForObject(new URI(url), String.class);
        System.out.println(obj);
    }
}