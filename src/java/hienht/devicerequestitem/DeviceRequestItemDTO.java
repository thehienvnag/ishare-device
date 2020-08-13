/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.devicerequestitem;

import hienht.deviceitem.DeviceItemDAO;
import hienht.deviceitem.DeviceItemDTO;
import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author thehien
 */
public class DeviceRequestItemDTO implements Serializable {

    private int id, amount, deviceRequestId, deviceItemId;
    private DeviceItemDTO deviceItem;

    public DeviceRequestItemDTO(int id, int amount, int deviceRequestId, DeviceItemDTO deviceItem) {
        this.id = id;
        this.amount = amount;
        this.deviceRequestId = deviceRequestId;
        this.deviceItem = deviceItem;
    }

    
    
    public DeviceRequestItemDTO(int deviceItemId, int leftQuantity) throws SQLException, NamingException {
        this.deviceItemId = deviceItemId;
        this.amount = 1;
        this.deviceItem = new DeviceItemDAO().getItemById(deviceItemId);
        deviceItem.setLeftQuantity(leftQuantity);
    }
    public DeviceRequestItemDTO(int deviceItemId) throws SQLException, NamingException {
        this.deviceItemId = deviceItemId;
        this.amount = 1;
        this.deviceItem = new DeviceItemDAO().getItemById(deviceItemId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDeviceRequestId() {
        return deviceRequestId;
    }

    public void setDeviceRequestId(int deviceRequestId) {
        this.deviceRequestId = deviceRequestId;
    }

    public int getDeviceItemId() {
        return deviceItemId;
    }

    public void setDeviceItemId(int deviceItemId) {
        this.deviceItemId = deviceItemId;
    }

    public DeviceItemDTO getDeviceItem() {
        return deviceItem;
    }

    public void setDeviceItem(Date[] dates) {
        this.deviceItem = deviceItem;
    }

}
