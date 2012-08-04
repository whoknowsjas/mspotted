package com.ost.cmarker;

import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: jworkman1
 * Date: 7/19/12
 * Time: 10:23 AM
 * To change this template use File | Settings | File Templates.
 */
public enum DB {
    INSTANCE;

    private org.iq80.leveldb.DB db;

    public synchronized org.iq80.leveldb.DB getConnection() {
        if(null == db){
            Options options = new Options().createIfMissing(true).compressionType(CompressionType.SNAPPY);
            try {
                db = Iq80DBFactory.factory.open(new File("/Users/work5538/Desktop/Deploy/cmarker-db"), options);
//                db = Iq80DBFactory.factory.open(new File("/cmarker-db"), options);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return db;
    }
}
