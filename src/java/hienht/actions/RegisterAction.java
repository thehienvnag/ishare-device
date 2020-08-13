/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import hienht.registration.RegistrationDAO;
import hienht.registration.RegistrationDTO;
import hienht.util.Util;

/**
 *
 * @author thehien
 */
public class RegisterAction extends ActionSupport {

    private RegistrationDTO user;
    private String duplicateEmail;

    public RegisterAction() {
    }

    public String execute() throws Exception {
        try {
            RegistrationDAO userDAO = new RegistrationDAO();
            user.setPassword(Util.encrypt(user.getPassword()));
            boolean rs = userDAO.registerAccount(user);
            if (rs) {
                return Action.SUCCESS;
            }
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg.contains("duplicate")) {
                duplicateEmail = "Email entered is existed!";
                return Action.INPUT;
            }
        }
        return Action.ERROR;
    }

    public RegistrationDTO getUser() {
        return user;
    }

    public void setUser(RegistrationDTO user) {
        this.user = user;
    }

    public String getDuplicateEmail() {
        return duplicateEmail;
    }

    public void setDuplicateEmail(String duplicateEmail) {
        this.duplicateEmail = duplicateEmail;
    }

}
