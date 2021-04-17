package laptt.tblShoppingItem;

import java.io.Serializable;
import java.util.Date;

public class TblShoppingItemDTO implements Serializable {

    private String shoppingItemId;
    private String shoppingItemName;
    private String shoppingItemImage;
    private String shoppingItemCategory;
    private String shoppingItemDescription;
    private Date shoppingItemCreateDate;
    private boolean shoppingItemStatus;
    private int shoppingItemQuantity;
    private float shoppingItemPrice;

    public TblShoppingItemDTO(String shoppingItemId, String shoppingItemName, String shoppingItemImage, String shoppingItemCategory, String shoppingItemDescription, Date shoppingItemCreateDate, boolean shoppingItemStatus, int shoppingItemQuantity, float shoppingItemPrice) {
        this.shoppingItemId = shoppingItemId;
        this.shoppingItemName = shoppingItemName;
        this.shoppingItemImage = shoppingItemImage;
        this.shoppingItemCategory = shoppingItemCategory;
        this.shoppingItemDescription = shoppingItemDescription;
        this.shoppingItemCreateDate = shoppingItemCreateDate;
        this.shoppingItemStatus = shoppingItemStatus;
        this.shoppingItemQuantity = shoppingItemQuantity;
        this.shoppingItemPrice = shoppingItemPrice;
    }

    public String getShoppingItemId() {
        return shoppingItemId;
    }

    public void setShoppingItemId(String shoppingItemId) {
        this.shoppingItemId = shoppingItemId;
    }

    public String getShoppingItemName() {
        return shoppingItemName;
    }

    public void setShoppingItemName(String shoppingItemName) {
        this.shoppingItemName = shoppingItemName;
    }

    public String getShoppingItemImage() {
        return shoppingItemImage;
    }

    public void setShoppingItemImage(String shoppingItemImage) {
        this.shoppingItemImage = shoppingItemImage;
    }

    public String getShoppingItemCategory() {
        return shoppingItemCategory;
    }

    public void setShoppingItemCategory(String shoppingItemCategory) {
        this.shoppingItemCategory = shoppingItemCategory;
    }

    public String getShoppingItemDescription() {
        return shoppingItemDescription;
    }

    public void setShoppingItemDescription(String shoppingItemDescription) {
        this.shoppingItemDescription = shoppingItemDescription;
    }

    public Date getShoppingItemCreateDate() {
        return shoppingItemCreateDate;
    }

    public void setShoppingItemCreateDate(Date shoppingItemCreateDate) {
        this.shoppingItemCreateDate = shoppingItemCreateDate;
    }

    public boolean getShoppingItemStatus() {
        return shoppingItemStatus;
    }

    public void setShoppingItemStatus(boolean shoppingItemStatus) {
        this.shoppingItemStatus = shoppingItemStatus;
    }

    public int getShoppingItemQuantity() {
        return shoppingItemQuantity;
    }

    public void setShoppingItemQuantity(int shoppingItemQuantity) {
        this.shoppingItemQuantity = shoppingItemQuantity;
    }

    public float getShoppingItemPrice() {
        return shoppingItemPrice;
    }

    public void setShoppingItemPrice(float shoppingItemPrice) {
        this.shoppingItemPrice = shoppingItemPrice;
    }

    @Override
    public String toString() {
        return "TblShoppingItemDTO{" +
                "shoppingItemId='" + shoppingItemId + '\'' +
                ", shoppingItemName='" + shoppingItemName + '\'' +
                ", shoppingItemImage='" + shoppingItemImage + '\'' +
                ", shoppingItemCategory='" + shoppingItemCategory + '\'' +
                ", shoppingItemDescription='" + shoppingItemDescription + '\'' +
                ", shoppingItemCreateDate=" + shoppingItemCreateDate +
                ", shoppingItemStatus=" + shoppingItemStatus +
                ", shoppingItemQuantity=" + shoppingItemQuantity +
                ", shoppingItemPrice=" + shoppingItemPrice +
                '}';
    }
}
