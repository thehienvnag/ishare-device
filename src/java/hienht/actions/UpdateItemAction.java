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
import hienht.util.Logger;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class UpdateItemAction {

    private int deviceItemId, amount;

    public UpdateItemAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            DeviceRequestDTO deviceRequest = (DeviceRequestDTO) session.get(Config.ATTR_SESSION_DEVICE_REQUEST);

            if (deviceRequest == null) {
                return Action.ERROR;
            }

            BookingObject items = deviceRequest.getItems();

            items.updateItem(deviceItemId, amount);
            session.put(Config.ATTR_SESSION_DEVICE_REQUEST, deviceRequest);

            return Action.SUCCESS;
        } catch (Exception e) {
            Logger.log(e.getMessage(), UpdateItemAction.class);
        }
        return Action.ERROR;

    }

    public int getDeviceItemId() {
        return deviceItemId;
    }

    public void setDeviceItemId(int deviceItemId) {
        this.deviceItemId = deviceItemId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
