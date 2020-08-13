/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import hienht.category.CategoryDAO;
import hienht.category.CategoryDTO;
import hienht.config.Config;
import hienht.registration.RegistrationDTO;
import hienht.util.Logger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thehien
 */
public class GoHomeAction {

    private List<CategoryDTO> listCate;

    public GoHomeAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            RegistrationDTO userInfo = (RegistrationDTO) session.get(Config.ATTR_SESSION_USER);
            if (userInfo == null) {
                return Action.LOGIN;
            }

            CategoryDAO cateDAO = new CategoryDAO();
            listCate = cateDAO.getAllCategory();
            return Action.SUCCESS;
        } catch (Exception e) {
            Logger.log(e.getMessage(), GoHomeAction.class);
        }
        return Action.SUCCESS;
    }

    public List<CategoryDTO> getListCate() {
        return listCate;
    }

    public void setListCate(List<CategoryDTO> listCate) {
        this.listCate = listCate;
    }

}
