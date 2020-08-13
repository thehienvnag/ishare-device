/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.devicerequestitem;

import hienht.config.Config;
import hienht.dbutil.DBUtilities;
import hienht.deviceitem.DeviceItemDTO;
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
public class DeviceRequestItemDAO implements Serializable {

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

    public List<DeviceRequestItemDTO> getRequestItemByRequestId(int requestId) throws SQLException, NamingException {
        List<DeviceRequestItemDTO> listRequestItem = null;
        String sql = "SELECT "
                + "	\"Id\", "
                + "     \"DeviceItemId\", "
                + "	(SELECT \"Name\" FROM \"public\".\"DeviceItem\" DI WHERE DI.\"Id\" = \"DeviceItemId\") AS DeviceName, "
                + "	(SELECT \"Color\" FROM \"public\".\"DeviceItem\" DI WHERE DI.\"Id\" = \"DeviceItemId\") AS DeviceColor, "
                + "	\"Amount\" "
                + "FROM \"public\".\"DeviceRequestItem\" "
                + "WHERE \n"
                + "	\"DeviceRequestId\" = ?";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, requestId);
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("Id");
                String name = rs.getString("DeviceName");
                String color = rs.getString("DeviceColor");
                int amount = rs.getInt("Amount");
                int deviceItemId = rs.getInt("DeviceItemId");
                DeviceItemDTO item = new DeviceItemDTO(name, color);
                DeviceRequestItemDTO requestItem = new DeviceRequestItemDTO(id, amount, requestId, item);
                requestItem.setDeviceItemId(deviceItemId);
                if(listRequestItem == null){
                    listRequestItem = new ArrayList<>();
                }
                listRequestItem.add(requestItem);
            }
            
        } finally {
            closeConnection();
        }

        return listRequestItem;
    }

    public boolean insertRequestItem(DeviceRequestItemDTO requestItem) throws SQLException, NamingException {
        boolean rs = false;
        String sql = "INSERT INTO \"public\".\"DeviceRequestItem\" ( \"DeviceRequestId\", \"DeviceItemId\", \"Amount\") "
                + "VALUES ( ?, ?, ? );";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, requestItem.getDeviceRequestId());
            pst.setInt(2, requestItem.getDeviceItemId());
            pst.setInt(3, requestItem.getAmount());

            rs = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return rs;
    }

    public int countTotalBooking(int itemId, Date[] dates) throws SQLException, NamingException {
        int totalBooked = 0;
        if (dates == null) {
            return 0;
        }
        String sql = "SELECT COUNT(\"Amount\") AS TotalBooked "
                + "        FROM \"public\".\"DeviceRequestItem\" DRI "
                + "        WHERE  "
                + "            (SELECT \"StatusId\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") = ? "
                + "            AND \"DeviceItemId\" = ?"
                + "            AND NOT( "
                + "                (SELECT \"BookingDate\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") >= ? "
                + "                OR (SELECT \"ReturnDate\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") <= ? "
                + "            ) ";

        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setInt(1, Config.STAT_ACCEPT);
            pst.setInt(2, itemId);
            pst.setDate(3, dates[1]);
            pst.setDate(4, dates[0]);

            rs = pst.executeQuery();
            if (rs.next()) {
                totalBooked = rs.getInt("TotalBooked");
            }
        } finally {
            closeConnection();
        }

        return totalBooked;
    }

    public int countLeftQuantity(int itemId, Date[] dates) throws SQLException, NamingException {
        int totalBooked = 0;
        if (dates == null) {
            return 0;
        }
        String sql = "SELECT (\"Quantity\" - COALESCE( "
                + "    (SELECT SUM(\"Amount\") "
                + "    FROM \"public\".\"DeviceRequestItem\" DRI "
                + "    WHERE  "
                + "    (SELECT \"StatusId\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") = ? "
                + "    AND \"DeviceItemId\" = ? "
                + "     AND NOT( "
                + "    (SELECT \"BookingDate\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") >= ? "
                + "    OR (SELECT \"ReturnDate\" FROM \"public\".\"DeviceRequest\" WHERE \"Id\" = DRI.\"DeviceRequestId\") <= ? ) "
                + ")"
                //+ "    GROUP BY \"DeviceItemId\") "
                + ", 0)) AS Remainings "
                + "FROM \"public\".\"DeviceItem\" "
                + "WHERE \"Id\" = ?";

        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setInt(1, Config.STAT_ACCEPT);
            pst.setInt(2, itemId);
            pst.setDate(3, dates[1]);
            pst.setDate(4, dates[0]);
            pst.setInt(5, itemId);
            rs = pst.executeQuery();
            if (rs.next()) {
                totalBooked = rs.getInt("Remainings");
            }
        } finally {
            closeConnection();
        }

        return totalBooked;
    }
}
