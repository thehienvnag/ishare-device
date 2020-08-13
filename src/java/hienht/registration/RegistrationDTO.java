/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.registration;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author thehien
 */
public class RegistrationDTO implements Serializable{
    
    
    private String email;
    private String password;
    private String address;
    private String phone;
    private String name;
    private String picture;
    private int userId, roleId, statudId;
    private Date createDate;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String email, String name, String picture, int userId, int roleId, int statudId, Date createDate) {
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.userId = userId;
        this.roleId = roleId;
        this.statudId = statudId;
        this.createDate = createDate;
    }
    
    

    public RegistrationDTO(String email, String address, String phone, String name, int userId, int roleId, int statudId, Date createDate, String picture) {
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.name = name;
        this.userId = userId;
        this.roleId = roleId;
        this.statudId = statudId;
        this.createDate = createDate;
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNameLetter() {
        String[] nameParts = name.split(" ");
        return nameParts[nameParts.length - 1].substring(0, 1);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

   
    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStatudId() {
        return statudId;
    }

    public void setStatudId(int statudId) {
        this.statudId = statudId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    
}
