/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.bookingobject;

import hienht.deviceitem.DeviceItemDTO;
import hienht.devicerequestitem.DeviceRequestItemDAO;
import hienht.devicerequestitem.DeviceRequestItemDTO;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import javax.naming.NamingException;

/**
 *
 * @author thehien
 */
public class BookingObject extends HashMap<Integer, DeviceRequestItemDTO> {

    public DeviceItemDTO getItemDTO(int deviceItemId){
        DeviceRequestItemDTO deviceRequestItem = this.get(deviceItemId);

        if(deviceRequestItem == null){
            return null;
        }
        return deviceRequestItem.getDeviceItem();
    }
    
    public void removeDateRange() {
        for (DeviceRequestItemDTO item : this.values()) {
            item.getDeviceItem().setLeftQuantity(-1);
        }
    }

    public boolean addNewItem(int deviceItemId, Date[] dates) throws SQLException, NamingException {
        DeviceRequestItemDAO deviceRequestItemDAO = new DeviceRequestItemDAO();
        int leftQuantity = deviceRequestItemDAO.countLeftQuantity(deviceItemId, dates);
        if (deviceItemId == 0 || leftQuantity < 1) {
            return false;
        }
        if (this.containsKey(deviceItemId)) {
            DeviceRequestItemDTO requestItem = this.get(deviceItemId);
            requestItem.setAmount(requestItem.getAmount() + 1);
        } else {
            DeviceRequestItemDTO requestItem = new DeviceRequestItemDTO(deviceItemId, leftQuantity);
            this.put(deviceItemId, requestItem);
        }
        return true;
    }

    public void addNewItem(int deviceItemId) throws SQLException, NamingException {
        if (deviceItemId == 0) {
            return;
        }
        if (this.containsKey(deviceItemId)) {
            DeviceRequestItemDTO requestItem = this.get(deviceItemId);
            requestItem.setAmount(requestItem.getAmount() + 1);
        } else {
            DeviceRequestItemDTO requestItem = new DeviceRequestItemDTO(deviceItemId);
            this.put(deviceItemId, requestItem);
        }
    }

    public void updateItem(int deviceItem, int amount) {
        if (deviceItem == 0 || amount <= 0) {
            return;
        }
        if (this.containsKey(deviceItem)) {
            DeviceRequestItemDTO requestItem = this.get(deviceItem);
            requestItem.setAmount(amount);
        }
    }

    public void removeItem(int deviceItem) {
        if (deviceItem == 0) {
            return;
        }
        this.remove(deviceItem);
    }
}
