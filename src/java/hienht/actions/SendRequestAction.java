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
import hienht.devicerequest.DeviceRequestDAO;
import hienht.devicerequest.DeviceRequestDTO;
import hienht.devicerequestitem.DeviceRequestItemDAO;
import hienht.devicerequestitem.DeviceRequestItemDTO;
import hienht.util.Logger;
import hienht.util.Util;
import java.sql.Date;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class SendRequestAction {

    private boolean notValid;

    public SendRequestAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            DeviceRequestDTO deviceRequest = (DeviceRequestDTO) session.get(Config.ATTR_SESSION_DEVICE_REQUEST);

            String dateRange = deviceRequest.getDateRange();
            Date[] dates = Util.getDateRanges(dateRange);
            if (dates == null) {
                notValid = true;
                return Action.INPUT;
            }
            deviceRequest.setBookingDate(dates[0]);
            deviceRequest.setReturnDate(dates[1]);
            BookingObject items = deviceRequest.getItems();

            boolean isValidItems = true;
            DeviceRequestItemDAO deviceRequestItemDAO = new DeviceRequestItemDAO();
            for (DeviceRequestItemDTO requestItem : items.values()) {
                int leftQuantity = deviceRequestItemDAO.countLeftQuantity(requestItem.getDeviceItemId(), dates);
                requestItem.getDeviceItem().setLeftQuantity(leftQuantity);
                isValidItems = leftQuantity >= requestItem.getAmount();
            }
            if (!isValidItems) {
                notValid = true;
                return Action.INPUT;
            }
            DeviceRequestDAO deviceRequestDAO = new DeviceRequestDAO();
            int deviceRequestId = deviceRequestDAO.insertDeviceRequest(deviceRequest);
            if (deviceRequestId != -1) {
                for (DeviceRequestItemDTO item : items.values()) {
                    item.setDeviceRequestId(deviceRequestId);
                    deviceRequestItemDAO.insertRequestItem(item);
                }
                session.remove(Config.ATTR_SESSION_DEVICE_REQUEST);
                return Action.SUCCESS;
            }
        } catch (Exception e) {
            Logger.log(e.getMessage(), SendRequestAction.class);
        }

        notValid = true;
        return Action.ERROR;
    }

    public boolean isNotValid() {
        return notValid;
    }

    public void setNotValid(boolean notValid) {
        this.notValid = notValid;
    }

}
