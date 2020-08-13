/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.interceptors;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import hienht.config.Config;
import hienht.registration.RegistrationDTO;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class UserAuthInterceptor implements Interceptor {
    
    public UserAuthInterceptor() {
    }
    
    public void destroy() {
    }
    
    public void init() {
    }
    
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        Map session = ActionContext.getContext().getSession();
        RegistrationDTO userInfo = (RegistrationDTO) session.get(Config.ATTR_SESSION_USER);
        if(userInfo != null && userInfo.getRoleId() != Config.ROLE_MANAGER){
            return actionInvocation.invoke();
        }
        return Action.LOGIN;
    }
    
}
