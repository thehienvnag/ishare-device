/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import hienht.config.Config;
import hienht.google.GooglePojo;
import hienht.google.GoogleUtil;
import hienht.registration.RegistrationDAO;
import hienht.registration.RegistrationDTO;
import hienht.util.Logger;
import hienht.util.Util;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class LoginWithGoogleAction {

    private String code;
    private String errMsg;

    public LoginWithGoogleAction() {
    }

    public String execute() throws Exception {
        try {
            if (Util.isNotEmptyParam(code)) {
                String accessToken = GoogleUtil.getAccessToken(code);
                GooglePojo userInfoFromGoogle = GoogleUtil.getUserInfo(accessToken);
                if (userInfoFromGoogle != null) {
                    RegistrationDAO regisDAO = new RegistrationDAO();
                    String email = userInfoFromGoogle.getEmail();

                    RegistrationDTO userInfo = regisDAO.loginWithGoogle(email);
                    if (userInfo == null) {
                        String name = userInfoFromGoogle.getName();
                        String picture = userInfoFromGoogle.getPicture();
                        userInfo = regisDAO.registerAccountWithGoogle(email, name, picture);
                    }
                    Map session = ActionContext.getContext().getSession();
                    session.put(Config.ATTR_SESSION_USER, userInfo);
                    return Action.SUCCESS;
                }
            }
        } catch (Exception e) {
            Logger.log(e.getMessage(), LoginWithGoogleAction.class);
        }
        return Action.ERROR;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
