/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.registration;

import hienht.config.Config;
import hienht.dbutil.DBUtilities;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author thehien
 */
public class RegistrationDAO implements Serializable {

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

    public RegistrationDTO getUserById(int userId) throws SQLException, NamingException {
        RegistrationDTO user = null;

        String sql = "SELECT \"Name\" "
                + "FROM \"public\".\"Registration\" "
                + "WHERE \"UserId\" = ? "
                + "AND  \"StatusId\" = ?";

        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.setInt(2, Config.STAT_ACTIVE);

            rs = pst.executeQuery();
            if (rs.next()) {
                String name = rs.getString("Name");

                user = new RegistrationDTO();
                user.setUserId(userId);
                user.setName(name);
            }

        } finally {
            closeConnection();
        }
        return user;
    }

    public RegistrationDTO getUserByEmail(String email) throws SQLException, NamingException {
        RegistrationDTO user = null;

        String sql = "SELECT "
                + "	\"Address\", "
                + "	\"CreateDate\", "
                + "	\"Email\", "
                + "	\"Name\", "
                + "	\"RoleId\", "
                + "	\"StatusId\", "
                + "	\"UserId\", "
                + "	\"Picture\", "
                + "	\"Phone\" "
                + "FROM \"public\".\"Registration\" "
                + "WHERE "
                + "	\"Email\" = ? "
                + "AND  \"StatusId\" = ?";

        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            pst.setInt(2, Config.STAT_ACTIVE);

            rs = pst.executeQuery();
            if (rs.next()) {
                String address = rs.getString("Address");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone");
                String picture = rs.getString("Picture");
                Date createDate = rs.getDate("CreateDate");
                int statusId = rs.getInt("StatusId");
                int userId = rs.getInt("UserId");
                int roleId = rs.getInt("RoleId");

                user = new RegistrationDTO(email, address, phone, name, userId, roleId, statusId, createDate, picture);
            }

        } finally {
            closeConnection();
        }
        return user;
    }

    public boolean enactiveAccount(String email) throws SQLException, NamingException {
        boolean result = false;
        String sql = "UPDATE \"public\".\"Registration\" SET "
                + "	\"StatusId\" = ? "
                + "     WHERE "
                + "	\"Email\" = ?";

        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Config.STAT_ACTIVE);
            pst.setString(2, email);

            result = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean registerAccount(RegistrationDTO user) throws SQLException, NamingException {
        boolean res = false;

        String sql = "INSERT INTO \"public\".\"Registration\" "
                + "( "
                + "\"Address\", "
                + "\"CreateDate\", "
                + "\"Email\", "
                + "\"Name\", "
                + "\"RoleId\", "
                + "\"StatusId\", "
                + "\"Phone\", "
                + "\"Password\""
                + ") "
                + "     VALUES ( ?, ?, ?, ?, ?, ?, ?, ? ) ";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getAddress());
            pst.setDate(2, new Date(System.currentTimeMillis()));
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getName());
            pst.setInt(5, Config.ROLE_EMPLOYEE);
            pst.setInt(6, Config.STAT_NEW);
            pst.setString(7, user.getPhone());
            pst.setString(8, user.getPassword());

            res = pst.executeUpdate() > 0;

        } finally {
            closeConnection();
        }

        return res;
    }

    public RegistrationDTO loginWithGoogle(String email) throws SQLException, NamingException {
        RegistrationDTO userInfo = null;
        String sql = "SELECT \"Address\", \"CreateDate\", \"Name\", \"RoleId\", \"StatusId\", \"UserId\", \"Phone\", \"Picture\" "
                + "	FROM public.\"Registration\""
                + "     WHERE \"Email\" = ? ";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, email);

            rs = pst.executeQuery();
            if (rs.next()) {
                String address = rs.getString("Address");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone");
                String picture = rs.getString("Picture");
                Date createDate = rs.getDate("CreateDate");
                int statusId = rs.getInt("StatusId");
                int userId = rs.getInt("UserId");
                int roleId = rs.getInt("RoleId");

                userInfo = new RegistrationDTO(email, address, phone, name, userId, roleId, statusId, createDate, picture);
            }

        } finally {
            closeConnection();
        }
        return userInfo;
    }

    public RegistrationDTO registerAccountWithGoogle(String email, String name, String picture) throws SQLException, NamingException {
        RegistrationDTO userInfo = null;

        Date current = new Date(System.currentTimeMillis());
        String sql = "INSERT INTO \"public\".\"Registration\" "
                + "( "
                + "\"CreateDate\", "
                + "\"RoleId\", "
                + "\"StatusId\", "
                + "\"Email\", "
                + "\"Name\", "
                + "\"Picture\" "
                + ") "
                + "     VALUES ( ?, ?, ?, ?, ?, ? ) "
                + "RETURNING \"UserId\"";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setDate(1, current);
            pst.setInt(2, Config.ROLE_EMPLOYEE);
            pst.setInt(3, Config.STAT_ACTIVE);
            pst.setString(4, email);
            pst.setString(5, name);
            pst.setString(6, picture);

            rs = pst.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("UserId");
                userInfo = new RegistrationDTO(email, name, picture, userId, Config.ROLE_EMPLOYEE, Config.STAT_ACTIVE, current);
            }

        } finally {
            closeConnection();
        }

        return userInfo;
    }

    public RegistrationDTO checkLogin(String email, String password) throws SQLException, NamingException {
        RegistrationDTO user = null;
        String sql = "SELECT \"Address\", \"CreateDate\", \"Name\", \"RoleId\", \"StatusId\", \"UserId\", \"Phone\", \"Picture\" "
                + "	FROM public.\"Registration\""
                + "     WHERE \"Email\" = ? "
                + "     AND \"Password\" = ? "
                + "     AND \"StatusId\" = ?";

        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            pst.setInt(3, Config.STAT_ACTIVE);

            rs = pst.executeQuery();
            if (rs.next()) {
                String address = rs.getString("Address");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone");
                String picture = rs.getString("Picture");
                Date createDate = rs.getDate("CreateDate");
                int statusId = rs.getInt("StatusId");
                int userId = rs.getInt("UserId");
                int roleId = rs.getInt("RoleId");

                user = new RegistrationDTO(email, address, phone, name, userId, roleId, statusId, createDate, picture);
            }

        } finally {
            closeConnection();
        }

        return user;
    }

}
