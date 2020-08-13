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
import hienht.devicerequestitem.DeviceRequestItemDAO;
import hienht.devicerequestitem.DeviceRequestItemDTO;
import hienht.registration.RegistrationDTO;
import hienht.util.Logger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class ViewRequestDetailAction {

    private int requestId;
    private List<DeviceRequestItemDTO> listRequestItem;

    public ViewRequestDetailAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            RegistrationDTO userInfo = (RegistrationDTO) session.get(Config.ATTR_SESSION_USER);

            if (userInfo != null) {
                boolean isAdmin = userInfo.getRoleId() == Config.ROLE_MANAGER;
                DeviceRequestDAO deviceRequestDAO = new DeviceRequestDAO();
                boolean isAuthenticatedUser = deviceRequestDAO.isValidUserAuthenticated(userInfo.getUserId(), requestId);
                if (isAdmin || isAuthenticatedUser) {
                    listRequestItem = new DeviceRequestItemDAO().getRequestItemByRequestId(requestId);
                    return Action.SUCCESS;
                }
            }

            return Action.LOGIN;
        } catch (Exception e) {
            Logger.log(e.getMessage(), ViewRequestDetailAction.class);
        }
        return Action.ERROR;
    }

    public List<DeviceRequestItemDTO> getListRequestItem() {
        return listRequestItem;
    }

    public void setListRequestItem(List<DeviceRequestItemDTO> listRequestItem) {
        this.listRequestItem = listRequestItem;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

}
