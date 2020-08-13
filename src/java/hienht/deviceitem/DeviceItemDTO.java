/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.deviceitem;

import hienht.category.CategoryDTO;
import hienht.config.Config;
import java.io.Serializable;

/**
 *
 * @author thehien
 */
public class DeviceItemDTO implements Serializable {

    private int id, quantity, statusId, totalBooked, leftQuantity;
    private String name, color;
    private CategoryDTO cate;

    public DeviceItemDTO(String name, String color) {
        this.name = name;
        this.color = color;
    }

    
    
    public DeviceItemDTO(int id, int cateId, String cateName, int quantity, int statusId, String name, String color) {
        this.id = id;
        this.quantity = quantity;
        this.statusId = statusId;
        this.name = name;
        this.color = color;
        cate = new CategoryDTO(cateId, cateName);  
        leftQuantity = Config.LEFT_QNTY_NO_DATE_RANGE;
    }

    public int getLeftQuantity() {
        return leftQuantity;
    }

    public void setLeftQuantity(int leftQuantity) {
        
        this.leftQuantity = leftQuantity;
    }

    
    public int getTotalBooked() {
        return totalBooked;
    }

    public void setTotalBooked(int totalBooked) {
        this.totalBooked = totalBooked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorLower() {
        return color.toLowerCase();
    }

    public String getColor() {
        return color.toUpperCase();
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CategoryDTO getCate() {
        return cate;
    }

    public void setCate(CategoryDTO cate) {
        this.cate = cate;
    }

}
