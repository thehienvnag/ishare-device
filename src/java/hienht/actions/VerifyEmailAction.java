/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import hienht.config.Config;
import hienht.registration.RegistrationDAO;
import hienht.registration.RegistrationDTO;
import hienht.util.Logger;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class VerifyEmailAction {

    private String code;
    private String errMsg;

    public VerifyEmailAction() {
    }

    public String execute() throws Exception {
        try {
            Map sessionMap = ActionContext.getContext().getSession();
            String verifiedString = (String) sessionMap.get(Config.ATTR_SESSION_VERIFY_EMAIL);
            if (code != null && verifiedString != null && verifiedString.equals(code)) {
                Map session = ActionContext.getContext().getSession();
                String emailToVerify = (String) session.get(Config.ATTR_SESSION_EMAIL_TO_VERIFY);
                if (emailToVerify != null) {
                    RegistrationDAO regisDAO = new RegistrationDAO();
                    boolean rs = regisDAO.enactiveAccount(emailToVerify);
                    if (rs) {
                        RegistrationDTO user = regisDAO.getUserByEmail(emailToVerify);
                        if (user != null) {
                            session.put(Config.ATTR_SESSION_USER, user);
                            return Action.SUCCESS;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.log(e.getMessage(), VerifyEmailAction.class);
        }

        errMsg = "Verification code is incorrect!";
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
