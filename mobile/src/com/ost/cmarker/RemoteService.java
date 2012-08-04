package com.ost.cmarker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all the interaction between the remote Server
 *
 * @author jworkman
 */
public abstract class RemoteService {

    /*default URL to server*/
    private static String BASE_URL = "http://97.95.224.70:9090/cMarkerServer";

    /**
     * Post geo to the server
     *
     * @param latitude
     * @param longitude
     */
    public static void postToServer(double latitude, double longitude) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(BASE_URL + "/service/post");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            String geo = latitude + "~" + longitude;
            httppost.setEntity(new StringEntity(geo));
            httpclient.execute(httppost, responseHandler);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Gets the locations from server
     *
     * @return List
     */
    public static List<String> getFromServer() throws Exception {
        List<String> locations = new ArrayList<String>();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(BASE_URL + "/service/get");
        try {
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    JSONArray jArray = new JSONArray(line);
                    for (int i = 0; i < jArray.length(); i++) {
                        String location = jArray.getString(i);
                        locations.add(location);
                    }
                }
                instream.close();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return locations;
    }
}
