package com.ost.cmarker;

import org.iq80.leveldb.DBIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jworkman1
 * Date: 7/19/12
 * Time: 10:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class GeoFactory {

    public List<String> getLocations(){
        List<String> list = new ArrayList<String>();
        DBIterator iterator = DB.INSTANCE.getConnection().iterator();
        while(iterator.hasNext()){
            Map.Entry<byte[], byte[]> map = iterator.next();
            String key= new String(map.getKey());
            list.add(key);
        }
        return list;
    }
}
