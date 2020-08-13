/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.status;

import hienht.dbutil.DBUtilities;
import java.sql.Connection;
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
public class StatusDAO {

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

    public List<StatusDTO> getAllStatus() throws SQLException, NamingException {
        String sql = "SELECT "
                + "	\"StatusId\", "
                + "	\"StatusName\" "
                + "FROM \"public\".\"Status\";";
        List<StatusDTO> listStatus = null;
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            
            rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("StatusId");
                String name = rs.getString("StatusName");
                StatusDTO stat = new StatusDTO();
                stat.setId(id);
                stat.setName(name);
                if(listStatus == null){
                    listStatus = new ArrayList<>();
                }
                listStatus.add(stat);
            }
            
        } finally{
            closeConnection();
        }
        return listStatus;
    }
}
