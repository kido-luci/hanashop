package laptt.tblBill;

import laptt.tblBillItem.TblBillItemDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TblBillDTO implements Serializable {
    private String billId;
    private String userId;
    private Date dateCheckOut;
    private boolean isPayment;
    private List<TblBillItemDTO> tblBillItemDTOList;

    public TblBillDTO(String billId, String userId, Date dateCheckOut, boolean isPayment, List<TblBillItemDTO> tblBillItemDTOList) {
        this.billId = billId;
        this.userId = userId;
        this.dateCheckOut = dateCheckOut;
        this.isPayment = isPayment;
        this.tblBillItemDTOList = tblBillItemDTOList;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(Date dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

    public boolean getPayment() {
        return isPayment;
    }

    public void setPayment(boolean payment) {
        isPayment = payment;
    }

    public List<TblBillItemDTO> getTblBillItemDTOList() {
        return tblBillItemDTOList;
    }

    public void setTblBillItemDTOList(List<TblBillItemDTO> tblBillItemDTOList) {
        this.tblBillItemDTOList = tblBillItemDTOList;
    }
}
