/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.deviceitem;

import hienht.config.Config;
import hienht.dbutil.DBUtilities;
import hienht.devicerequestitem.DeviceRequestItemDAO;
import hienht.util.Util;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author thehien
 */
public class DeviceItemDAO implements Serializable {

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

    public DeviceItemDTO getItemById(int deviceItemId) throws SQLException, NamingException {
        DeviceItemDTO deviceItem = null;
        String sql = "SELECT "
                + "	\"Id\", "
                + "	\"Name\", "
                + "	\"Color\", "
                + "	\"CateId\", "
                + "     (SELECT \"Name\" FROM \"public\".\"Category\" C WHERE \"CateId\" = C.\"Id\") AS CateName, "
                + "	\"Quantity\", "
                + "	\"StatusId\" "
                + "FROM \"public\".\"DeviceItem\" "
                + "WHERE "
                + "	\"Id\" = ?";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, deviceItemId);

            rs = pst.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String color = rs.getString("Color");
                int quantity = rs.getInt("Quantity");
                int statusId = rs.getInt("StatusId");
                int cateId = rs.getInt("CateId");
                String cateName = rs.getString("CateName");
                deviceItem = new DeviceItemDTO(id, cateId, cateName, quantity, statusId, name, color);
            }
        } finally {
            closeConnection();
        }

        return deviceItem;
    }

    public DeviceItemDTO getItemById(int deviceItemId, Date[] dates) throws SQLException, NamingException {
        DeviceItemDTO deviceItem = null;
        String sql = "SELECT "
                + "	\"Id\", "
                + "	\"Name\", "
                + "	\"Color\", "
                + "	\"CateId\", "
                + "     (SELECT \"Name\" FROM \"public\".\"Category\" C WHERE \"CateId\" = C.\"Id\") AS CateName, "
                + "	\"Quantity\", "
                + "	\"StatusId\" "
                + "FROM \"public\".\"DeviceItem\" "
                + "WHERE "
                + "	\"Id\" = ? "
                + "     AND NOT( "
                + "                (SELECT \"BookingDate\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") >= ? "
                + "                OR (SELECT \"ReturnDate\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") <= ? "
                + "            ) "
                + "     AND (SELECT \"StatusId\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") = ?";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, deviceItemId);
            pst.setDate(2, dates[1]);
            pst.setDate(3, dates[0]);

            rs = pst.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String color = rs.getString("Color");
                int quantity = rs.getInt("Quantity");
                int statusId = rs.getInt("StatusId");
                int cateId = rs.getInt("CateId");
                String cateName = rs.getString("CateName");
                deviceItem = new DeviceItemDTO(id, cateId, cateName, quantity, statusId, name, color);
            }
        } finally {
            closeConnection();
        }

        return deviceItem;
    }

    public int countTotalSearch(String nameInput, int cateIdInput, Date[] dates) throws SQLException, NamingException {
        int totalSearch = 0;
        String sql = "SELECT"
                + "	COUNT(\"Id\") AS TotalSearch "
                + "FROM \"public\".\"DeviceItem\" DI "
                + "WHERE \"StatusId\" = ?";

        String dateRangeComparison = " AND \"Quantity\" >= COALESCE(( "
                + "    SELECT SUM(\"Amount\")  "
                + "    FROM \"public\".\"DeviceRequestItem\" DRI  "
                + "    WHERE   "
                + "        (SELECT \"StatusId\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") = ?  "
                + "        AND \"DeviceItemId\" =  DI.\"Id\" "
                + "        AND NOT( "
                + "        (SELECT \"BookingDate\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") >= ?  "
                + "        OR (SELECT \"ReturnDate\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") <= ? )  "
                + "    ) "
                + ", 0)";

        String cateIdComparison = " AND \"CateId\"  = ?";
        String nameComparison = " AND LOWER(\"Name\") LIKE ?";

        if (dates != null) {
            sql += dateRangeComparison;
        }

        if (Util.isNotEmptyParam(nameInput)) {
            sql += nameComparison;
        }

        if (cateIdInput > 0) {
            sql += cateIdComparison;
        }
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            int count = 1;
            pst.setInt(count++, Config.STAT_ACTIVE);
            if (dates != null) {
                pst.setInt(count++, Config.STAT_ACCEPT);
                pst.setDate(count++, dates[1]);
                pst.setDate(count++, dates[0]);
                
            }
            if (Util.isNotEmptyParam(nameInput)) {
                pst.setString(count++, "%" + nameInput.toLowerCase() + "%");
            }
            if (cateIdInput > 0) {
                pst.setInt(count++, cateIdInput);
            }
            rs = pst.executeQuery();
            if (rs.next()) {
                totalSearch = rs.getInt("TotalSearch");
            }

        } finally {
            closeConnection();
        }
        return totalSearch;
    }

    public List<DeviceItemDTO> searchItems(String nameInput, int cateIdInput, Date[] dates) throws SQLException, NamingException {
        return searchItems(nameInput, cateIdInput, dates, 1);
    }

    public List<DeviceItemDTO> searchItems(String nameInput, int cateIdInput, Date[] dates, int pageIndex) throws SQLException, NamingException {
        List<DeviceItemDTO> listDevice = null;

        String sql = "SELECT\n"
                + "	\"Id\", "
                + "	\"Name\", "
                + "	\"Color\", "
                + "	\"CateId\", "
                + "     (SELECT \"Name\" FROM \"public\".\"Category\" C WHERE \"CateId\" = C.\"Id\") AS CateName, "
                + "	\"Quantity\", "
                + "	\"StatusId\" "
                + "FROM \"public\".\"DeviceItem\" DI "
                + "WHERE \"StatusId\" = ?";

        String dateRangeComparison = " AND \"Quantity\" > COALESCE(( "
                + "    SELECT SUM(\"Amount\")  "
                + "    FROM \"public\".\"DeviceRequestItem\" DRI  "
                + "    WHERE   "
                + "        (SELECT \"StatusId\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") = ?  "
                + "        AND \"DeviceItemId\" =  DI.\"Id\" "
                + "        AND NOT( "
                + "        (SELECT \"BookingDate\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") >= ?  "
                + "        OR (SELECT \"ReturnDate\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") <= ? )  "
                + "    ) "
                + ", 0)";

        String cateIdComparison = " AND \"CateId\"  = ?";
        String nameComparison = " AND LOWER(\"Name\") LIKE ?";

        if (dates != null) {
            sql += dateRangeComparison;
        }

        if (Util.isNotEmptyParam(nameInput)) {
            sql += nameComparison;
        }

        if (cateIdInput > 0) {
            sql += cateIdComparison;
        }

        int pageSize = Config.PAGE_SIZE;
        int startIndex = (pageIndex - 1) * pageSize;

        sql += "     ORDER BY DI.\"Name\""
                + "     OFFSET " + startIndex + " ROWS"
                + "     FETCH NEXT " + pageSize + " ROWS ONLY";

        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            int count = 1;
            pst.setInt(count++, Config.STAT_ACTIVE);
            if (dates != null) {
                pst.setInt(count++, Config.STAT_ACCEPT);
                pst.setDate(count++, dates[1]);
                pst.setDate(count++, dates[0]);
                
            }
            if (Util.isNotEmptyParam(nameInput)) {
                pst.setString(count++, "%" + nameInput.toLowerCase() + "%");
            }
            if (cateIdInput > 0) {
                pst.setInt(count++, cateIdInput);
            }

            rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String color = rs.getString("Color");
                int quantity = rs.getInt("Quantity");
                int statusId = rs.getInt("StatusId");
                int cateId = rs.getInt("CateId");
                String cateName = rs.getString("CateName");
                DeviceItemDTO deviceItem = new DeviceItemDTO(id, cateId, cateName, quantity, statusId, name, color);
                DeviceRequestItemDAO deviceRequestItemDAO = new DeviceRequestItemDAO();
                int totalBooked = deviceRequestItemDAO.countTotalBooking(id, dates);
                deviceItem.setTotalBooked(totalBooked);
                if (listDevice == null) {
                    listDevice = new ArrayList<>();
                }
                listDevice.add(deviceItem);
            }
        } finally {
            closeConnection();
        }

        return listDevice;
    }

}
