package com.example.vaxnote.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.vaxnote.Constants;
import com.example.vaxnote.activities.MainMenuActivity;
import com.example.vaxnote.activities.NewUserActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserAuthentication{
    String result;
    Context context;

    public UserAuthentication(Context context){
        this.context = context;
    }

    public void SignUp(String userName,String userSignUpEmail, String userSignUpPassword){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_SIGNUP,
                response -> {
                    Log.d("VaccineSIGNUP",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        result = jsonObject.getString("message");
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                        if(Objects.equals(result, "Sign Up Successful")){
                            SharedPrefManager.getInstance(context).userSignIn(userName,userSignUpEmail);
                            Intent intent = new Intent(context, NewUserActivity.class);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("SignUpError",error.getMessage())){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("userName",userName);
                params.put("userSignUpEmail",userSignUpEmail);
                params.put("userSignUpPassword",userSignUpPassword);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void SignIn(String userLoginEmail,String userLoginPassword){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_SIGNIN,
                response -> {
//                        progressDialog.dismiss();
                    try {
                        Log.d("SignInDebug",response);
                        JSONObject jsonObject = new JSONObject(response);
                        result = jsonObject.getString("message");
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                        if(Objects.equals(result,"Sign In Successful")) {
                            SharedPrefManager.getInstance(context).userSignIn(
                                    jsonObject.getString("userName"),
                                    jsonObject.getString("userEmail")
                            );
                            Intent intent = new Intent(context, MainMenuActivity.class);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("SignInError",error.getMessage())
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("userLoginEmail",userLoginEmail);
                params.put("userLoginPassword",userLoginPassword);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }
}
