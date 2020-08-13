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
import hienht.devicerequest.DeviceRequestDTO;
import hienht.registration.RegistrationDTO;
import hienht.util.Logger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class ViewRequestHistoryAction {

    private List<DeviceRequestDTO> listDeviceRequest;

    public ViewRequestHistoryAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            RegistrationDTO user = (RegistrationDTO) session.get(Config.ATTR_SESSION_USER);
            listDeviceRequest = new DeviceRequestDAO().getUserDeviceRequests(user.getUserId());
            return Action.SUCCESS;
        } catch (Exception e) {
            Logger.log(e.getMessage(), ViewRequestHistoryAction.class);
        }
        return Action.ERROR;
    }

    public List<DeviceRequestDTO> getListDeviceRequest() {
        return listDeviceRequest;
    }

    public void setListDeviceRequest(List<DeviceRequestDTO> listDeviceRequest) {
        this.listDeviceRequest = listDeviceRequest;
    }

}
