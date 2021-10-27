package com.example.vaxnote;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    public BackgroundWorker(Context ctx) {
        this.context=ctx;
    }

    @Override
    protected String doInBackground(String... strings) {
        String SignIn_url = "http://10.0.2.2/login.php";
        //String SignIn_url = "http://192.168.56.1/login.php";
        try {
            String userLoginEmail = strings[0];
            String userLoginPassword = strings[1];
            URL url = new URL(SignIn_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("userLoginEmail","UTF-8") + "=" + URLEncoder.encode(userLoginEmail,"UTF-8") + "&" + URLEncoder.encode("userLoginPassword", "UTF-8") + "=" + URLEncoder.encode(userLoginPassword, "UTF-8");
            bufferedWriter.write(post_data);
            Log.d("post_data", post_data); //debugging
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
            String result = "";
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                result+=line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
//            Log.d("SignInAsyncTask",result);
//            Toast.makeText(context, "Result", Toast.LENGTH_SHORT).show();
            return  result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(unused);
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }
}
