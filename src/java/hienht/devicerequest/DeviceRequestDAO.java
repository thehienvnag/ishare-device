/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.devicerequest;

import hienht.config.Config;
import hienht.dbutil.DBUtilities;
import hienht.util.Util;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author thehien
 */
public class DeviceRequestDAO implements Serializable {

    private PreparedStatement pst;
    private Connection conn;
    private ResultSet rs;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pst != null) {
            pst.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public boolean isValidUserAuthenticated(int userId, int deviceRequestId) throws SQLException, NamingException {
        boolean res = false;
        String sql = "SELECT "
                + "	\"Id\" "
                + "FROM \"public\".\"DeviceRequest\" "
                + "WHERE "
                + "	\"UserId\" = ? "
                + "	AND \"Id\" = ? ";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.setInt(2, deviceRequestId);

            rs = pst.executeQuery();
            res = rs.next();
        } finally {
            closeConnection();
        }
        return res;
    }

    public boolean isValidBookingDate(int requestId) throws SQLException, NamingException {
        boolean res = false;
        String sql = "SELECT "
                + "	\"Id\" "
                + "FROM \"public\".\"DeviceRequest\" "
                + "WHERE \"Id\" = ? AND "
                + "\"BookingDate\" >= CURRENT_DATE;";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, requestId);

            rs = pst.executeQuery();
            res = rs.next();
        } finally {
            closeConnection();
        }
        return res;
    }

    public boolean acceptRequestAdmin(int requestId) throws SQLException, NamingException {
        boolean rs = false;
        String sql = "UPDATE \"public\".\"DeviceRequest\" SET "
                + "	\"StatusId\" = ? "
                + "WHERE\n"
                + "	      \"Id\" = ? ";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Config.STAT_ACCEPT);
            pst.setInt(2, requestId);

            rs = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return rs;
    }

    public boolean deleteRequestAdmin(int requestId) throws SQLException, NamingException {
        boolean rs = false;
        String sql = "UPDATE \"public\".\"DeviceRequest\" SET "
                + "	\"StatusId\" = ? "
                + "WHERE\n"
                + "	      \"Id\" = ? ";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Config.STAT_DELETE);
            pst.setInt(2, requestId);

            rs = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return rs;
    }

    public boolean deleteRequestUser(int requestId, int userId) throws SQLException, NamingException {
        boolean rs = false;
        String sql = "UPDATE \"public\".\"DeviceRequest\" SET "
                + "	\"StatusId\" = ? "
                + "WHERE\n"
                + "	      \"Id\" = ? "
                + "           \"UserId\" = ?;";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Config.STAT_INACTIVE);
            pst.setInt(2, requestId);
            pst.setInt(3, userId);

            rs = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return rs;
    }
//    public boolean deleteRequestManager(int requestId) throws SQLException, NamingException {
//        boolean rs = false;
//        String sql = "UPDATE \"public\".\"DeviceRequest\" SET "
//                + "	\"StatusId\" = ? "
//                + "WHERE\n"
//                + "	      \"Id\" = ? "
//                + "           \"StatusId\" <> ? ";
//        try {
//            conn = DBUtilities.getConnection();
//            pst = conn.prepareStatement(sql);
//            pst.setInt(1, requestId);
//            pst.setInt(2, Config.STAT_INACTIVE);
//
//            rs = pst.executeUpdate() > 0;
//        } finally {
//            closeConnection();
//        }
//
//        return rs;
//    }

    public int countSearchRequest(String deviceName, String userRequest, int statusId, Date importDate) throws SQLException, NamingException {
        int totalRowSearch = -1;
        String sql = "SELECT COUNT(\"Id\") AS TotalRows "
                + "FROM \"public\".\"DeviceRequest\" "
                + "WHERE 1 = 1 ";
        String deviceNameComparison = "	AND \"Id\" IN (SELECT \"DeviceRequestId\" "
                + "                    FROM \"public\".\"DeviceRequestItem\" "
                + "                    WHERE (SELECT LOWER(\"Name\") FROM \"public\".\"DeviceItem\" DI WHERE \"DeviceItemId\" = DI.\"Id\") LIKE ? "
                + "                   )";
        String userRequestComparison = " AND \"UserId\" IN (SELECT \"Id\" FROM \"public\".\"Registration\" WHERE \"Name\" LIKE ?) ";

        String statusIdComparison = " AND \"StatusId\" = ? ";
        String importDateComparison = " AND date(\"ImportDate\") = ? ";

        boolean isValidDeviceName = Util.isNotEmptyParam(deviceName);
        boolean isValidUserRequest = Util.isNotEmptyParam(userRequest);
        boolean isValidStatus = statusId > 0;
        boolean isValidImportDate = importDate != null;
        if (isValidDeviceName) {
            sql += deviceNameComparison;
        }
        if (isValidUserRequest) {
            sql += userRequestComparison;
        }
        if (isValidStatus) {
            sql += statusIdComparison;
        }
        if (isValidImportDate) {
            sql += importDateComparison;
        }
        int count = 1;
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            if (isValidDeviceName) {
                pst.setString(count++, "%" + deviceName.toLowerCase() + "%");
            }
            if (isValidUserRequest) {
                pst.setString(count++, "%" + userRequest.toLowerCase() + "%");
            }
            if (isValidStatus) {
                pst.setInt(count++, statusId);
            }
            if (isValidImportDate) {
                pst.setDate(count, importDate);
            }
            rs = pst.executeQuery();
            if (rs.next()) {
                totalRowSearch = rs.getInt("TotalRows");
            }

        } finally {
            closeConnection();
        }
        return totalRowSearch;
    }

    public List<DeviceRequestDTO> searchRequest(String deviceName, String userRequest, int statusId, Date importDate) throws SQLException, NamingException {
        return searchRequest(deviceName, userRequest, statusId, importDate, 1);
    }

    public List<DeviceRequestDTO> searchRequest(String deviceName, String userRequest, int statusId, Date importDate, int pageIndex) throws SQLException, NamingException {
        List<DeviceRequestDTO> listDeviceRequest = null;
        String sql = "SELECT "
                + "	\"Id\", "
                + "	\"UserId\", "
                + "	\"BookingDate\", "
                + "	\"ReturnDate\", "
                + "	\"StatusId\", "
                + "	\"ImportDate\" "
                + "FROM \"public\".\"DeviceRequest\" "
                + "WHERE 1 = 1 ";
        String deviceNameComparison = "	AND \"Id\" IN (SELECT \"DeviceRequestId\" "
                + "                    FROM \"public\".\"DeviceRequestItem\" "
                + "                    WHERE (SELECT LOWER(\"Name\") FROM \"public\".\"DeviceItem\" DI WHERE \"DeviceItemId\" = DI.\"Id\") LIKE ? "
                + "                   )";
        String userRequestComparison = " AND \"UserId\" IN (SELECT \"Id\" FROM \"public\".\"Registration\" WHERE \"Name\" LIKE ?) ";

        String statusIdComparison = " AND \"StatusId\" = ? ";
        String importDateComparison = " AND date(\"ImportDate\") = ? ";

        boolean isValidDeviceName = Util.isNotEmptyParam(deviceName);
        boolean isValidUserRequest = Util.isNotEmptyParam(userRequest);
        boolean isValidStatus = statusId > 0;
        boolean isValidImportDate = importDate != null;
        if (isValidDeviceName) {
            sql += deviceNameComparison;
        }

        if (isValidUserRequest) {
            sql += userRequestComparison;
        }

        if (isValidStatus) {
            sql += statusIdComparison;
        }
        if (isValidImportDate) {
            sql += importDateComparison;
        }
        int pageSize = Config.PAGE_SIZE;
        int startIndex = (pageIndex - 1) * pageSize;
        sql += "     ORDER BY \"ImportDate\" Desc "
                + "     OFFSET " + startIndex + " ROWS"
                + "     FETCH NEXT " + pageSize + " ROWS ONLY";
        int count = 1;
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            if (isValidDeviceName) {
                pst.setString(count++, "%" + deviceName.toLowerCase() + "%");
            }
            if (isValidUserRequest) {
                pst.setString(count++, "%" + userRequest.toLowerCase() + "%");
            }
            if (isValidStatus) {
                pst.setInt(count++, statusId);
            }
            if (isValidImportDate) {
                pst.setDate(count, importDate);
            }

            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                int userId = rs.getInt("UserId");
                Date bookingDate = rs.getDate("BookingDate");
                Date returnDate = rs.getDate("ReturnDate");
                int statusIdRequest = rs.getInt("StatusId");
                Timestamp importDateTimestamp = rs.getTimestamp("ImportDate");
                DeviceRequestDTO deviceRequest = new DeviceRequestDTO(id, userId, statusIdRequest, bookingDate, returnDate, importDateTimestamp);
                if (listDeviceRequest == null) {
                    listDeviceRequest = new ArrayList<>();
                }
                listDeviceRequest.add(deviceRequest);
            }

        } finally {
            closeConnection();
        }
        return listDeviceRequest;
    }

    public List<DeviceRequestDTO> searchRequestUser(String deviceName, Date importDate, int userId) throws SQLException, NamingException {
        List<DeviceRequestDTO> listDeviceRequest = null;

        String dateComparison = " AND date(\"ImportDate\") = ? ";
        String deviceNameComparison = "	AND \"Id\" IN (SELECT \"DeviceRequestId\" "
                + "                    FROM \"public\".\"DeviceRequestItem\" "
                + "                    WHERE (SELECT LOWER(\"Name\") FROM \"public\".\"DeviceItem\" DI WHERE \"DeviceItemId\" = DI.\"Id\") LIKE ? "
                + "                   )";

        String sql = "SELECT "
                + "	\"Id\", "
                + "	\"UserId\", "
                + "	\"BookingDate\", "
                + "	\"ReturnDate\", "
                + "	\"StatusId\", "
                + "	\"ImportDate\" "
                + "FROM \"public\".\"DeviceRequest\" "
                + "WHERE  "
                + "	\"UserId\" = ? ";

        boolean isDeviceNameSearch = Util.isNotEmptyParam(deviceName);
        boolean isImportDateSearch = importDate != null;

        if (isDeviceNameSearch) {
            sql += deviceNameComparison;
        }

        if (isImportDateSearch) {
            sql += dateComparison;
        }
        sql += " ORDER BY \"ImportDate\" Desc;";
        int pos = 2;
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            if (isDeviceNameSearch) {
                pst.setString(pos++, "%" + deviceName.toLowerCase() + "%");
            }
            if (isImportDateSearch) {
                pst.setDate(pos, importDate);
            }
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                Date bookingDate = rs.getDate("BookingDate");
                Date returnDate = rs.getDate("ReturnDate");
                int statusId = rs.getInt("StatusId");
                Timestamp importDateTimestamp = rs.getTimestamp("ImportDate");
                DeviceRequestDTO deviceRequest = new DeviceRequestDTO(id, userId, statusId, bookingDate, returnDate, importDateTimestamp);
                if (listDeviceRequest == null) {
                    listDeviceRequest = new ArrayList<>();
                }
                listDeviceRequest.add(deviceRequest);
            }

        } finally {
            closeConnection();
        }

        return listDeviceRequest;
    }

    public List<DeviceRequestDTO> getUserDeviceRequests(int userId) throws SQLException, NamingException {
        String sql = "SELECT "
                + "	\"Id\", "
                + "	\"BookingDate\", "
                + "	\"ReturnDate\", "
                + "	\"StatusId\", "
                + "	\"ImportDate\" "
                + "FROM \"public\".\"DeviceRequest\" "
                + "WHERE  "
                + "	\"UserId\" = ? "
                + "ORDER BY \"ImportDate\" Desc;";
        List<DeviceRequestDTO> listDeviceRequest = null;
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);

            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                Date bookingDate = rs.getDate("BookingDate");
                Date returnDate = rs.getDate("ReturnDate");
                int statusId = rs.getInt("StatusId");
                Timestamp importDate = rs.getTimestamp("ImportDate");
                DeviceRequestDTO deviceRequest = new DeviceRequestDTO(id, userId, statusId, bookingDate, returnDate, importDate);
                if (listDeviceRequest == null) {
                    listDeviceRequest = new ArrayList<>();
                }
                listDeviceRequest.add(deviceRequest);
            }
        } finally {
            closeConnection();
        }
        return listDeviceRequest;
    }

    public int countTotalRequestRows() throws SQLException, NamingException {
        int totalRequestRows = -1;
        String sql = "SELECT COUNT(\"Id\") AS TotalRequestRows "
                + "FROM \"public\".\"DeviceRequest\" ";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                totalRequestRows = rs.getInt("TotalRequestRows");
            }
        } finally {
            closeConnection();
        }
        return totalRequestRows;
    }

    public List<DeviceRequestDTO> getDeviceRequestsPaging() throws SQLException, NamingException {
        return getDeviceRequestsPaging(1);
    }


    public List<DeviceRequestDTO> getDeviceRequestsPaging(int pageIndex) throws SQLException, NamingException {
        int pageSize = Config.PAGE_SIZE;
        int startIndex = (pageIndex - 1) * pageSize;
        String sql = "SELECT "
                + "	\"Id\", "
                + "	\"UserId\", "
                + "	\"BookingDate\", "
                + "	\"ReturnDate\", "
                + "	\"StatusId\", "
                + "	\"ImportDate\" "
                + "FROM \"public\".\"DeviceRequest\" "
                + "     ORDER BY \"ImportDate\" Desc "
                + "     OFFSET " + startIndex + " ROWS"
                + "     FETCH NEXT " + pageSize + " ROWS ONLY";

        List<DeviceRequestDTO> listDeviceRequest = null;
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                int userId = rs.getInt("UserId");
                Date bookingDate = rs.getDate("BookingDate");
                Date returnDate = rs.getDate("ReturnDate");
                int statusId = rs.getInt("StatusId");
                Timestamp importDate = rs.getTimestamp("ImportDate");
                DeviceRequestDTO deviceRequest = new DeviceRequestDTO(id, userId, statusId, bookingDate, returnDate, importDate);
                if (listDeviceRequest == null) {
                    listDeviceRequest = new ArrayList<>();
                }
                listDeviceRequest.add(deviceRequest);
            }
        } finally {
            closeConnection();
        }
        return listDeviceRequest;
    }

    public DeviceRequestDTO getDeviceRequestById(int requestId) throws SQLException, NamingException {
        
        String sql = "SELECT "
                + "	\"Id\", "
                + "	\"UserId\", "
                + "	\"BookingDate\", "
                + "	\"ReturnDate\", "
                + "	\"StatusId\", "
                + "	\"ImportDate\" "
                + "FROM \"public\".\"DeviceRequest\" "
                + "WHERE \"Id\" = ?";

        DeviceRequestDTO deviceRequest = null;
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, requestId);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                int userId = rs.getInt("UserId");
                Date bookingDate = rs.getDate("BookingDate");
                Date returnDate = rs.getDate("ReturnDate");
                int statusId = rs.getInt("StatusId");
                Timestamp importDate = rs.getTimestamp("ImportDate");
                deviceRequest = new DeviceRequestDTO(id, userId, statusId, bookingDate, returnDate, importDate);
                
            }
        } finally {
            closeConnection();
        }
        return deviceRequest;
    }

    public int insertDeviceRequest(DeviceRequestDTO deviceRequest) throws SQLException, NamingException {
        int deviceRequestId = -1;
        String sql = "INSERT INTO \"public\".\"DeviceRequest\" ( \"UserId\", \"BookingDate\", \"ReturnDate\", \"StatusId\", \"ImportDate\") "
                + "VALUES ( ?, ?, ?, ?, ? ) "
                + "RETURNING \"Id\"";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, deviceRequest.getUserId());
            pst.setDate(2, deviceRequest.getBookingDate());
            pst.setDate(3, deviceRequest.getReturnDate());
            pst.setInt(4, Config.STAT_NEW);
            pst.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            rs = pst.executeQuery();
            if (rs.next()) {
                deviceRequestId = rs.getInt("Id");
            }

        } finally {
            closeConnection();
        }

        return deviceRequestId;
    }
}
