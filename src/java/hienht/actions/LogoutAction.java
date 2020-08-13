/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import hienht.util.Logger;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class LogoutAction {

    public LogoutAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            session.clear();
            return Action.SUCCESS;
        } catch (Exception e) {
            Logger.log(e.getMessage(), LogoutAction.class);
        }
        return Action.ERROR;
    }

}
