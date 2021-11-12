package com.example.vaxnote;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserAuthentication{
    String result;
    Context context;

    UserAuthentication(Context context){
        this.context = context;
    }

    public void SignUp(String userName,String userSignUpEmail, String userSignUpPassword){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_SIGNUP,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        result = jsonObject.getString("message");
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                        if(Objects.equals(result, "Sign Up Successful")){
                            Intent intent = new Intent(context,MainActivity.class);
                            context.startActivity(intent);
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
                            SharedPrefManager.getInstance(context).userSignIn(jsonObject.getInt("userId"),
                                    jsonObject.getString("userName"),
                                    jsonObject.getString("userEmail")
                            );
                            Intent intent = new Intent(context,MainActivity.class);
                            context.startActivity(intent);
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
