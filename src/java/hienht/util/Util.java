package hienht.util;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.apache.struts2.ServletActionContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thehien
 */
public class Util {
    
    public static Date getCurrentDatePartSQL(){
        return new Date(getCurrentDatePart().getTime());
    }
    
    public static java.util.Date getCurrentDatePart(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dateStr = sdf.format(new Date(System.currentTimeMillis()));
        try {
            return sdf.parse(dateStr);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String displayDate(Timestamp time) {
        java.util.Date date = new java.util.Date(time.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        return formatter.format(date);
    }
    public static String displayDate(Date time) {
        java.util.Date date = new java.util.Date(time.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        return formatter.format(date);
    }
    
    public static String encrypt(String password) {
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
    }

    public static Properties getMailProp() throws IOException {
        return getProperties("/resource/mail.properties");
    }

    public static Properties getProperties(String path) throws IOException {
        ServletContext ctx = ServletActionContext.getServletContext();
        Properties prop = new Properties();
        prop.load(ctx.getResourceAsStream(path));
        return prop;
    }

    public static String generateRandomConfirm() {
        int count = 6;
        String rand = "0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * rand.length());
            builder.append(rand.charAt(character));
        }
        return builder.toString();
    }

    public static boolean isNotEmptyParam(String param) {
        return param != null && !param.isEmpty();
    }

    public static Date getDateFromString(String dateStr){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date date = sdf.parse(dateStr);
            return new Date(date.getTime());
        } catch (ParseException ex) {
        }
        return null;
    }
    
    public static Date getDate(String dateStr) throws ParseException {
        dateStr = dateStr.replaceAll("/", "-");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = sdf.parse(dateStr);
        return new Date(date.getTime());
    }

    public static Date[] getDateRanges(String dateRange) {
        Date[] dates = null;
        try {
            if (dateRange.isEmpty()) {
                return null;
            }
            String[] dateStr = dateRange.split(" - ");
            Date bookingDate = getDate(dateStr[0]);
            Date returnDate = getDate(dateStr[1]);
            dates = new Date[2];
            dates[0] = bookingDate;
            dates[1] = returnDate;
            
        } catch (Exception e) {
        }
        return dates;
    }

    public static boolean isValidDateRange(String dateRange) {
        try {
            Date[] dates = getDateRanges(dateRange);
            if (dates == null) {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public static List<Integer> generatePageArr(int pageIndex, int totalPage) {
        if (pageIndex == 0) {
            pageIndex = 1;
        }
        List<Integer> pagingArr = new ArrayList<>();

        int startIndex = pageIndex - 2;
        int endIndex = pageIndex + 2;
        if (endIndex > totalPage) {
            startIndex = totalPage - 5;
            endIndex = totalPage;
        }
        if (startIndex < 1) {
            startIndex = 1;
        }
        for (int i = startIndex; i <= endIndex; i++) {
            pagingArr.add(i);
        }

        return pagingArr;
    }
}
