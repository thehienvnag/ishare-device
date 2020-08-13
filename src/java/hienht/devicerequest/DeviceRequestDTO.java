/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.devicerequest;

import hienht.bookingobject.BookingObject;
import hienht.registration.RegistrationDAO;
import hienht.registration.RegistrationDTO;
import hienht.util.Util;
import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;

/**
 *
 * @author thehien
 */
public class DeviceRequestDTO implements Serializable {

    private int id, userId, statusId;
    private Date bookingDate, returnDate;
    private String dateRange;
    private Timestamp importDate;

    private BookingObject items;
    private RegistrationDTO userInfo;
    

    public DeviceRequestDTO(int id, int userId, int statusId, Date bookingDate, Date returnDate, Timestamp importDate) throws SQLException, NamingException {
        this.id = id;
        this.userId = userId;
        this.statusId = statusId;
        this.bookingDate = bookingDate;
        this.returnDate = returnDate;
        this.importDate = importDate;
        this.userInfo = new RegistrationDAO().getUserById(userId);
    }

    public RegistrationDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(RegistrationDTO userInfo) {
        this.userInfo = userInfo;
    }

    
    
    public boolean getExpired() {
        return bookingDate.compareTo(Util.getCurrentDatePart()) < 0;
    }

    public DeviceRequestDTO(int userId) {
        this.userId = userId;
    }

    public String getImportDateDisplay() {
        return Util.displayDate(importDate);
    }

    public Timestamp getImportDate() {
        return importDate;
    }

    public void setImportDate(Timestamp importDate) {
        this.importDate = importDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public String getBookingDateDisplay() {
        return Util.displayDate(bookingDate);
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String getReturnDateDisplay() {
        return Util.displayDate(returnDate);
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BookingObject getItems() {
        if (items == null) {
            items = new BookingObject();
        }
        return items;
    }

    public void setItems(BookingObject items) {
        this.items = items;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

}
