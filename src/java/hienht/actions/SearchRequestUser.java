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
import hienht.util.Util;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class SearchRequestUser {

    private String deviceName;
    private String dateString;
    private List<DeviceRequestDTO> listDeviceRequest;

    public SearchRequestUser() {
    }

    public String execute() throws Exception {
        try {
            Date importDate = Util.getDateFromString(dateString);
            if (Util.isNotEmptyParam(deviceName) || importDate != null) {
                Map session = ActionContext.getContext().getSession();
                RegistrationDTO user = (RegistrationDTO) session.get(Config.ATTR_SESSION_USER);

                if (user == null) {
                    return Action.ERROR;
                }

                DeviceRequestDAO deviceRequestDAO = new DeviceRequestDAO();
                listDeviceRequest = deviceRequestDAO.searchRequestUser(deviceName, importDate, user.getUserId());

                return Action.SUCCESS;
            }
            return Action.NONE;
        } catch (Exception e) {
            Logger.log(e.getMessage(), SearchRequestUser.class);
        }
        return Action.ERROR;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public List<DeviceRequestDTO> getListDeviceRequest() {
        return listDeviceRequest;
    }

    public void setListDeviceRequest(List<DeviceRequestDTO> listDeviceRequest) {
        this.listDeviceRequest = listDeviceRequest;
    }

}
