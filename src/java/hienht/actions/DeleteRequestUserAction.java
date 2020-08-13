/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import hienht.config.Config;
import hienht.devicerequest.DeviceRequestDAO;
import hienht.registration.RegistrationDTO;
import hienht.util.Logger;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class DeleteRequestUserAction {

    private int requestId;

    public DeleteRequestUserAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            RegistrationDTO userInfo = (RegistrationDTO) session.get(Config.ATTR_SESSION_USER);
            if (userInfo != null) {
                DeviceRequestDAO deviceRequestDAO = new DeviceRequestDAO();
                boolean isValidRequest = deviceRequestDAO.isValidBookingDate(requestId);
                if (isValidRequest) {
                    boolean rs = deviceRequestDAO.deleteRequestUser(requestId, userInfo.getUserId());
                    if (rs) {
                        return Action.SUCCESS;
                    }
                }

            }
        } catch (Exception e) {
            Logger.log(e.getMessage(), DeleteRequestAdminAction.class);
        }
        return Action.ERROR;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

}
