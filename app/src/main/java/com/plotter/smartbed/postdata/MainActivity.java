package com.plotter.smartbed.postdata;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Values that will be used for streaming
        String sensorValue[] = new String[8];
        //Example values
        sensorValue[0]="85";
        sensorValue[1]="92";
        sensorValue[2]="13";
        sensorValue[3]="4";
        sensorValue[4]="32";
        sensorValue[5]="0";
        sensorValue[6]="0";
        sensorValue[7]="0";
        //Due to the cloud server's capacity, requests must be every 30-35 seconds
        new PostTask().execute(sensorValue);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class PostTask extends AsyncTask<String, Void, Void> {

        // Obtains wave values in an array
        @Override
        protected Void doInBackground(String... sensor) {

            // Stream to send out included on List
            List<NameValuePair> sensorData = new ArrayList<NameValuePair>(14);
            sensorData.add(new BasicNameValuePair("field1", sensor[0]));
            sensorData.add(new BasicNameValuePair("field2", sensor[1]));
            sensorData.add(new BasicNameValuePair("field3", sensor[2]));
            sensorData.add(new BasicNameValuePair("field4", sensor[3]));
            sensorData.add(new BasicNameValuePair("field5", sensor[4]));
            sensorData.add(new BasicNameValuePair("field6", sensor[5]));
            sensorData.add(new BasicNameValuePair("field7", sensor[6]));
            sensorData.add(new BasicNameValuePair("field8", sensor[7]));
            sensorData.add(new BasicNameValuePair("key", "863D169E1EFA41FD"));
            sensorData.add(new BasicNameValuePair("status", "operational"));
            sensorData.add(new BasicNameValuePair("lat", "0"));
            sensorData.add(new BasicNameValuePair("long", "0"));
            sensorData.add(new BasicNameValuePair("elevation", "0"));
            sensorData.add(new BasicNameValuePair("location", "RobotLand"));

            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://bots.myrobots.com/update");

            try {
                //Sending the stream data to the Cloud
                httppost.setEntity(new UrlEncodedFormEntity(sensorData));

                // Retrieving answer
                HttpResponse response = httpclient.execute(httppost);

                //The following code can be used for debugging purposes
                //HttpEntity resEntity =  response.getEntity();
                //if(resEntity != null){
                //    Log.i("RESPONSE", EntityUtils.toString(resEntity));
                //}

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                return null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return null;
            }

            //Task is finished
            return null;
        }

    }


}
