package com.ost.cmarker;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: work5538
 * Date: 7/17/12
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class RESTControllerIT {

//    static String BASE_URL = "http://localhost:8080/server/service";
    static Logger log = LoggerFactory.getLogger(RESTControllerIT.class);

    @Test
    public void testPostService(){
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//        map.add(RESTController.LATITUDE, "0");
        new RestTemplate().postForLocation("http://97.95.224.70:9090/cMarkerServer/service/post", new String("0~0"));
//        log.debug("vPostResponse: "+response);
        assertTrue(true);
    }

    @Test
    public void testGetService(){
        new RestTemplate().getForObject("http://localhost:9090/cMarkerServer/service/get", ArrayList.class);

        assertTrue(true);
    }


}
