package com.example.vaxnote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
import java.util.Map;
import java.util.Set;

public class UserAuthentication extends Thread{
    String result = "";
    Context context;
   public static final String fileName = "Login";
    UserAuthentication(Context context){
        this.context = context;
    }

    public void AuthenticateUser(String userLoginEmail, String userLoginPassword){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //DO IN BACKGROUND
                SignIn(userLoginEmail,userLoginPassword);

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

                        /* NOTE : if the condition is changed here change in signin.php also */
                        Log.d("Login",result);
                        if (result.equals("Sign In Successful")) {
                            //Adding userLoginEmail to Shared Preferences
                            SharedPreferences sharedPreferences;
                            sharedPreferences = context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Email",userLoginEmail);
                            editor.putString("Password",userLoginPassword);
                            editor.apply();

                            //Opening the next activity
                            Intent open_main_activity = new Intent(context, MainActivity.class);
                            ((Activity)context).startActivity(open_main_activity);
                        }
                    }
                });
            }
        }).start();
    }

    public void AuthenticateUser(String userName, String userSignUpEmail, String userSignUpPassword){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //DO IN BACKGROUND
                SignUp(userName,userSignUpEmail,userSignUpPassword);

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

                        /* NOTE : if the condition is changed here change in signup.php also */
                        if(result.equals("Sign Up Successful")) {
                            //Adding userLoginEmail to Shared Preferences
                            SharedPreferences sharedPreferences;
                            sharedPreferences = context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Email",userSignUpEmail);
                            editor.putString("Password",userSignUpPassword);
                            editor.apply();

                            //Opening next activity
                            Intent open_main_activity = new Intent(context, MainActivity.class);
                            ((Activity)context).startActivity(open_main_activity);
                        }
                    }
                });
            }
        }).start();
    }

    private void SignIn(String userLoginEmail, String userLoginPassword){
        String SignIn_url = "https://vaxnote.000webhostapp.com/signin.php";
        try {
            URL url = new URL(SignIn_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            String post_data = URLEncoder.encode("userLoginEmail","UTF-8") + "=" + URLEncoder.encode(userLoginEmail,"UTF-8") + "&" + URLEncoder.encode("userLoginPassword", "UTF-8") + "=" + URLEncoder.encode(userLoginPassword, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
            String line;
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void SignUp(String userName, String userSignUpEmail, String userSignUpPassword){
        String SignUp_url = "https://vaxnote.000webhostapp.com/signup.php";
        try {
            URL url = new URL(SignUp_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            String post_data = URLEncoder.encode("userName","UTF-8") + "=" + URLEncoder.encode(userName,"UTF-8") + "&"
                    + URLEncoder.encode("userSignUpEmail","UTF-8") + "=" + URLEncoder.encode(userSignUpEmail,"UTF-8") + "&"
                    + URLEncoder.encode("userSignUpPassword","UTF-8") + "=" + URLEncoder.encode(userSignUpPassword,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
            String line;
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
