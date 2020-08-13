/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.google;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author thehien
 */
public class GoogleUtil {

//    public static final String REDIRECT_URI = "http://localhost:8084/Lab03ResourceSharing/login-with-google";
    public static final String REDIRECT_URI = "https://ishare-device.herokuapp.com/login-with-google";
    public static final String CLIENT_ID = "745597423708-dsss4974it1io6r86bkegt501gfqjd67.apps.googleusercontent.com";
    public static final String CLIENT_SECRET = "-DzIRr6gif8O8HWetCWL0_5h";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo";
    public static final String LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static final String LINK_GET_CODE = "https://accounts.google.com/o/oauth2/auth";
    
    //Re-captcha integrated
    public static final String CAPTCHA_SITE_KEY = "6LchG7AZAAAAAJ6kCCjGcROQqv-m1YVcrze7CWNu";
    public static final String CAPTCHA_SECRET_KEY = "6LchG7AZAAAAAJIA8dURJvJ7NCTQfd7GSjqzVjKB";
    public static final String LINK_VERIFY_RECAPTCHA = "https://www.google.com/recaptcha/api/siteverify";
    
    

    public String getRedirectUrl() {
        String url = LINK_GET_CODE
                + "?scope=email profile"
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code"
                + "&client_id=" + CLIENT_ID;
        return url;
    }
    
    public String getCaptchaSiteKey(){
        return CAPTCHA_SITE_KEY;
    }
    
 

    public static String getAccessToken(String code) throws IOException {
        Form form = Form.form()
                .add("client_id", CLIENT_ID)
                .add("client_secret", CLIENT_SECRET)
                .add("redirect_uri", REDIRECT_URI)
                .add("code", code)
                .add("grant_type", GRANT_TYPE);

        String response = Request.Post(LINK_GET_TOKEN)
                .bodyForm(form.build())
                .execute()
                .returnContent()
                .asString();

        JsonObject json = new Gson().fromJson(response, JsonObject.class);

        String accessToken = json.get("access_token").toString().replace("\"", "");

        return accessToken;
    }

    public static GooglePojo getUserInfo(String accessToken) throws IOException, ClientProtocolException {

        String linkGetUserInfo = LINK_GET_USER_INFO
                + "?access_token=" + accessToken;

        String response = Request.Get(linkGetUserInfo)
                .execute()
                .returnContent()
                .asString();

        GooglePojo userInfo = new Gson().fromJson(response, GooglePojo.class);
        if(userInfo.getEmail() == null){
            return null;
        }
        
        return userInfo;
    }
}
