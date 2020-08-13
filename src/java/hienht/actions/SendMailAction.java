/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import hienht.config.Config;
import hienht.registration.RegistrationDTO;
import hienht.util.Logger;
import hienht.util.MailUtil;
import hienht.util.Util;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class SendMailAction {

    private RegistrationDTO user;

    public SendMailAction() {
    }

    public String execute() throws Exception {
        try {
            String verifyString = Util.generateRandomConfirm();
            Map sessionMap = ActionContext.getContext().getSession();
            sessionMap.put(Config.ATTR_SESSION_VERIFY_EMAIL, verifyString);

            MailUtil.sendMail(user.getEmail(), verifyString);
            Map session = ActionContext.getContext().getSession();
            session.put(Config.ATTR_SESSION_EMAIL_TO_VERIFY, user.getEmail());

            return Action.SUCCESS;
        } catch (Exception e) {
            Logger.log(e.getMessage(), SendMailAction.class);
        }
        return Action.ERROR;
    }

    public RegistrationDTO getUser() {
        return user;
    }

    public void setUser(RegistrationDTO user) {
        this.user = user;
    }

}
