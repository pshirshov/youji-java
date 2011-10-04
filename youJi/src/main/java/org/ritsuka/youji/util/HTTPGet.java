package org.ritsuka.youji.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Date: 04.10.11
 * Time: 16:24
 */
public class HTTPGet {
    static public String getHTML(String urlToRead) throws Exception {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        url = new URL(urlToRead);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = rd.readLine()) != null) {
            result += line;
        }
        rd.close();
        return result;
    }

 public static String urlencode(String aURLFragment){
     String result = null;
     try {
       result = URLEncoder.encode(aURLFragment, "UTF-8");
     }
     catch (UnsupportedEncodingException ex){
       throw new RuntimeException("UTF-8 not supported", ex);
     }
     return result;
   }
}
