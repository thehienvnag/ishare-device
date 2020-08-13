/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.config;

/**
 *
 * @author thehien
 */
public class Config {
    
    public static final int PAGE_SIZE = 20;
    
    //DeviceItem final
    public static final int LEFT_QNTY_NO_DATE_RANGE = -1;
    
    //Email username and password
    public static final String EMAIL = "ishare.noreply@gmail.com";
    public static final String PASSWORD = "123456abcX124";
    
    
    //Status
    public static final int STAT_ACTIVE = 1;
    public static final int STAT_NEW = 2;
    public static final int STAT_INACTIVE = 5;
    public static final int STAT_ACCEPT = 6;
    public static final int STAT_DELETE = 7;
    
    //Role
    public static final int ROLE_MANAGER = 1;
    public static final int ROLE_LEADER = 2;
    public static final int ROLE_EMPLOYEE = 3;
    
    //Attribute
    //Session
    public static final String ATTR_SESSION_USER = "USER"; 
    public static final String ATTR_SESSION_VERIFY_EMAIL = "VERIFY_EMAIL"; 
    public static final String ATTR_SESSION_DEVICE_REQUEST = "DEVICE_REQUEST"; 
    public static final String ATTR_SESSION_EMAIL_TO_VERIFY = "EMAIL_TO_VERIFY"; 
    public static final String ATTR_SESSION_SUCCESS_MSG = "SUCCESS_MSG"; 
    public static final String ATTR_SESSION_ERROR_MSG = "ERROR_MSG"; 
    //Request scope
    public static final String ATTR_REQUEST_ERROR_CAPTCHA = "ERROR_CAPTCHA"; 
    
    
}
