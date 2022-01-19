package com.example.app.userapi;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {


    private static final String APIURL="https://deependra-009.github.io/db.json";

    static String ExtractData(){
        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;
        String ApiJSONString=null;
        try{
            Uri uri=Uri.parse(APIURL);
            URL requestURL=new URL(uri.toString());
            urlConnection=(HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream=urlConnection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder=new StringBuilder();
            String line;
            while((line= reader.readLine())!=null){
                builder.append(line);
                builder.append("\n");
            }

            if(builder.length()==0){
                return null;
            }
            ApiJSONString=builder.toString();
        }
        catch(Exception e){
            Log.e("Error",e.getMessage());
        }
        finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
//        Log.d("APIDATA1","-->"+ApiJSONString);
        return ApiJSONString;
    }

}
