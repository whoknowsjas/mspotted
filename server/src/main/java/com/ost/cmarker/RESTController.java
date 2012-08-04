package com.ost.cmarker;

import org.iq80.leveldb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: work5538
 * Date: 7/17/12
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class RESTController {

    static Logger log = LoggerFactory.getLogger(RESTController.class);


    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";

    @RequestMapping(method=RequestMethod.GET, value="/get")
    @ResponseBody
    public final List<String> get() {
        log.debug("getting list from server");
        List<String> list = new ArrayList<String>();
        DBIterator iterator = DB.INSTANCE.getConnection().iterator();
        while(iterator.hasNext()){
            Map.Entry<byte[], byte[]> map = iterator.next();
            String key= new String(map.getKey());
//            String value= new String(map.getValue());
//            String msg = key +": "+value;
            list.add(key);
            log.debug(key);

        }
        return list;
    }

    @RequestMapping(method= RequestMethod.POST, value="/post")
    @ResponseBody
    public final void post(@RequestBody final String geo) {
        log.debug(geo + ", posting to server.");
        DB.INSTANCE.getConnection().put(geo.getBytes(), geo.getBytes());
    }

//    @RequestMapping(method= RequestMethod.POST, value="/post")
//    @ResponseBody
//    public final void post(@RequestParam(LATITUDE) String latitude, @RequestParam(LONGITUDE) String longitude) {
//        log.debug(LATITUDE.getBytes()+": " + latitude.getBytes() + ", posting to server.");
//        log.debug(LONGITUDE.getBytes()+": " + longitude.getBytes() + ", posting to server.");
//        getDbInstance().put(LATITUDE.getBytes(), latitude.getBytes());
//        getDbInstance().put(LONGITUDE.getBytes(), longitude.getBytes());
//    }


}
