/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import hienht.config.Config;
import hienht.devicerequest.DeviceRequestDAO;
import hienht.devicerequest.DeviceRequestDTO;
import hienht.util.Logger;
import hienht.util.Util;
import java.util.List;

/**
 *
 * @author thehien
 */
public class ViewRequestAdminAction {

    private List<DeviceRequestDTO> listDeviceRequest;
    private int page, totalSearchRow, totalPages;
    private List<Integer> pagingArr;
    private String urlRewriting;

    public ViewRequestAdminAction() {
    }

    public String execute() throws Exception {
        try {
            if (listDeviceRequest == null) {
                DeviceRequestDAO deviceRequestDAO = new DeviceRequestDAO();
                if (page > 0) {
                    listDeviceRequest = deviceRequestDAO.getDeviceRequestsPaging(page);
                } else {
                    listDeviceRequest = deviceRequestDAO.getDeviceRequestsPaging();
                }
                totalSearchRow = deviceRequestDAO.countTotalRequestRows();
                totalPages = (int) Math.ceil((double) totalSearchRow / Config.PAGE_SIZE);
                pagingArr = Util.generatePageArr(page, totalPages);
                urlRewriting = "view-request-admin?";
                if (page == 0) {
                    page = 1;
                }
            }
            return Action.SUCCESS;
        } catch (Exception e) {
            Logger.log(e.getMessage(), ViewRequestAdminAction.class);
        }
        return Action.ERROR;
    }

    public List<DeviceRequestDTO> getListDeviceRequest() {
        return listDeviceRequest;
    }

    public void setListDeviceRequest(List<DeviceRequestDTO> listDeviceRequest) {
        this.listDeviceRequest = listDeviceRequest;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalSearchRow() {
        return totalSearchRow;
    }

    public void setTotalSearchRow(int totalSearchRow) {
        this.totalSearchRow = totalSearchRow;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Integer> getPagingArr() {
        return pagingArr;
    }

    public void setPagingArr(List<Integer> pagingArr) {
        this.pagingArr = pagingArr;
    }

    public String getUrlRewriting() {
        return urlRewriting;
    }

    public void setUrlRewriting(String urlRewriting) {
        this.urlRewriting = urlRewriting;
    }

}
