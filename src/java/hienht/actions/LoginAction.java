/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import hienht.registration.RegistrationDAO;
import hienht.registration.RegistrationDTO;
import hienht.util.Logger;
import hienht.util.Util;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class LoginAction {

    private String txtEmail, txtPassword;
    private String errMsg;

    public LoginAction() {
    }

    public String execute() throws Exception {

        try {

            if (Util.isNotEmptyParam(txtEmail) && Util.isNotEmptyParam(txtPassword)) {
                RegistrationDAO userDAO = new RegistrationDAO();
                RegistrationDTO user = userDAO.checkLogin(txtEmail, Util.encrypt(txtPassword));
                if (user != null) {
                    Map session = ActionContext.getContext().getSession();
                    session.put("USER", user);
                    return Action.SUCCESS;
                }
                errMsg = "Invalid username or password!";
                return Action.LOGIN;
            }
        } catch (Exception e) {
            Logger.log(e.getMessage(), LoginAction.class);
        }
        return Action.ERROR;
    }

    public String getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail = txtEmail;
    }

    public String getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword = txtPassword;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
