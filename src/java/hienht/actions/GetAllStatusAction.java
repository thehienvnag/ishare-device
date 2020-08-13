/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import hienht.status.StatusDAO;
import hienht.status.StatusDTO;
import hienht.util.Logger;
import java.util.List;

/**
 *
 * @author thehien
 */
public class GetAllStatusAction {

    private List<StatusDTO> listStatus;

    public GetAllStatusAction() {
    }

    public String execute() throws Exception {
        try {
            listStatus = new StatusDAO().getAllStatus();
            return Action.SUCCESS;
        } catch (Exception e) {
            Logger.log(e.getMessage(), GetAllStatusAction.class);
        }
        return Action.ERROR;

    }

    public List<StatusDTO> getListStatus() {
        return listStatus;
    }

    public void setListStatus(List<StatusDTO> listStatus) {
        this.listStatus = listStatus;
    }

}
