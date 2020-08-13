/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import hienht.devicerequest.DeviceRequestDAO;
import hienht.util.Logger;

/**
 *
 * @author thehien
 */
public class DeleteRequestAdminAction {

    private int requestId;

    public DeleteRequestAdminAction() {
    }

    public String execute() throws Exception {

        try {
            if (requestId > 0) {
                DeviceRequestDAO deviceRequestDAO = new DeviceRequestDAO();
                boolean isValidRequest = deviceRequestDAO.isValidBookingDate(requestId);
                if (isValidRequest) {
                    boolean rs = deviceRequestDAO.deleteRequestAdmin(requestId);
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
