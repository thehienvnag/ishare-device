/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import hienht.bookingobject.BookingObject;
import hienht.config.Config;
import hienht.devicerequest.DeviceRequestDTO;
import hienht.registration.RegistrationDTO;
import hienht.util.Logger;
import hienht.util.Util;
import java.sql.Date;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class BookingAction {

    private int deviceItemId;
    private String redirectUrl, deviceName, color;

    public BookingAction() {
    }

    public String execute() throws Exception {

        try {
            Map session = ActionContext.getContext().getSession();
            DeviceRequestDTO deviceRequest = (DeviceRequestDTO) session.get(Config.ATTR_SESSION_DEVICE_REQUEST);
            if (deviceRequest == null) {
                RegistrationDTO userInfo = (RegistrationDTO) session.get(Config.ATTR_SESSION_USER);
                deviceRequest = new DeviceRequestDTO(userInfo.getUserId());
            }

            redirectUrl = redirectUrl.replaceAll("amp;", "");

            BookingObject bookingObject = deviceRequest.getItems();
            Date[] dates = Util.getDateRanges(deviceRequest.getDateRange());

            boolean isSuccessful = true;
            if (dates == null) {
                bookingObject.addNewItem(deviceItemId);
            } else {
                isSuccessful = bookingObject.addNewItem(deviceItemId, dates);
            }

            if (isSuccessful) {
                String successMsg = deviceName + " with color (" + color + ") has successfully added!";
                session.put(Config.ATTR_SESSION_SUCCESS_MSG, successMsg);
            } else {
                String errMsg = deviceName + " with color (" + color + ") remains 0 items during: " + deviceRequest.getDateRange();
                session.put(Config.ATTR_SESSION_ERROR_MSG, errMsg);
            }

            deviceRequest.setItems(bookingObject);
            session.put(Config.ATTR_SESSION_DEVICE_REQUEST, deviceRequest);

            return Action.SUCCESS;
        } catch (Exception e) {
            Logger.log(e.getMessage(), BookingAction.class);
        }
        return Action.ERROR;
    }

    public int getDeviceItemId() {
        return deviceItemId;
    }

    public void setDeviceItemId(int deviceItemId) {
        this.deviceItemId = deviceItemId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
