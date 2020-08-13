/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.category;

import hienht.dbutil.DBUtilities;
import java.io.Serializable;
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
public class CategoryDAO implements Serializable {

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

    public List<CategoryDTO> getAllCategory() throws SQLException, NamingException {
        List<CategoryDTO> listCategory = null;
        String sql = "SELECT \"Id\", \"Name\" FROM \"public\".\"Category\" ";
        try {
            conn = DBUtilities.getConnection();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");

                CategoryDTO cate = new CategoryDTO(id, name);
                if (listCategory == null) {
                    listCategory = new ArrayList<>();
                }
                listCategory.add(cate);
            }
        } finally {
            closeConnection();
        }
        return listCategory;
    }
}
