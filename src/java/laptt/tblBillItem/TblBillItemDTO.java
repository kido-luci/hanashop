package laptt.tblBillItem;

import java.io.Serializable;

public class TblBillItemDTO implements Serializable {

    private String billItemId;
    private String billId;
    private int quantity;
    private float price;

    public TblBillItemDTO(String billItemId, String billId, int quantity, float price) {
        this.billItemId = billItemId;
        this.billId = billId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getBillItemId() {
        return billItemId;
    }

    public void setBillItemId(String billItemId) {
        this.billItemId = billItemId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
