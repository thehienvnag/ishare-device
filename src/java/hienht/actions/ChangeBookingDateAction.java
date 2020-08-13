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
public class ChangeBookingDateAction {

    private String dateRange;

    public ChangeBookingDateAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            DeviceRequestDTO deviceRequest = (DeviceRequestDTO) session.get(Config.ATTR_SESSION_DEVICE_REQUEST);

            if (deviceRequest == null) {
                return Action.ERROR;
            }

            BookingObject items = deviceRequest.getItems();
            Date[] dates = Util.getDateRanges(dateRange);
            if (dates == null) {
                deviceRequest.setDateRange(null);
                items.removeDateRange();
                return Action.INPUT;
            }

            deviceRequest.setDateRange(dateRange);

            DeviceRequestItemDAO deviceRequestItemDAO = new DeviceRequestItemDAO();
            for (DeviceRequestItemDTO item : items.values()) {
                int leftQuantity = deviceRequestItemDAO.countLeftQuantity(item.getDeviceItemId(), dates);
                item.getDeviceItem().setLeftQuantity(leftQuantity);
            }

            session.put(Config.ATTR_SESSION_DEVICE_REQUEST, deviceRequest);

            return Action.SUCCESS;
        } catch (Exception e) {
            Logger.log(e.getMessage(), ChangeBookingDateAction.class);
        }
        return Action.ERROR;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

}
