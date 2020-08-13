/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import hienht.devicerequest.DeviceRequestDAO;
import hienht.devicerequest.DeviceRequestDTO;
import hienht.devicerequestitem.DeviceRequestItemDAO;
import hienht.devicerequestitem.DeviceRequestItemDTO;
import hienht.util.Logger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thehien
 */
public class AcceptRequestAction {

    private int requestId;
    private List<String> errMsg;

    public AcceptRequestAction() {
    }

    public String execute() throws Exception {

        ActionContext ctx = ActionContext.getContext();
        try {
            if (requestId > 0) {
                DeviceRequestDAO deviceRequestDAO = new DeviceRequestDAO();
                DeviceRequestDTO deviceRequestDTO = deviceRequestDAO.getDeviceRequestById(requestId);
                boolean isValidRequest = deviceRequestDAO.isValidBookingDate(requestId);
                List<DeviceRequestItemDTO> listRequestItem = new DeviceRequestItemDAO().getRequestItemByRequestId(requestId);
                if (isValidRequest) {
                    DeviceRequestItemDAO deviceRequestItemDAO = new DeviceRequestItemDAO();
                    for (DeviceRequestItemDTO deviceRequestItemDTO : listRequestItem) {

                        int remainings = deviceRequestItemDAO.countLeftQuantity(
                                deviceRequestItemDTO.getDeviceItemId(),
                                new Date[]{deviceRequestDTO.getBookingDate(), deviceRequestDTO.getReturnDate()}
                        );
                        boolean isValidQuantity = remainings - deviceRequestItemDTO.getAmount() >= 0;
                        if (!isValidQuantity) {
                            if (errMsg == null) {
                                errMsg = new ArrayList<>();
                                errMsg.add("Request making at: " + deviceRequestDTO.getImportDateDisplay() + " of user " + deviceRequestDTO.getUserInfo().getName() + " is invalid!");
                            }
                            String msg = "   -- Item " + deviceRequestItemDTO.getDeviceItem().getName() + " remains " + remainings
                                    + "  <-> unit(s). (Request's amount is " + deviceRequestItemDTO.getAmount() + ")";
                            errMsg.add(msg);
                        }
                    }
                    if (errMsg == null) {
                        boolean rs = deviceRequestDAO.acceptRequestAdmin(requestId);
                        if (rs) {
                            return Action.SUCCESS;
                        }
                    } else {
                        return "fail";
                    }
                }
            }
        } catch (Exception e) {
            Logger.log(e.getMessage(), AcceptRequestAction.class);
        }
        return Action.ERROR;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public List<String> getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(List<String> errMsg) {
        this.errMsg = errMsg;
    }

}
