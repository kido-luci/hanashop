package laptt.shoppingCart;

import laptt.tblShoppingItem.TblShoppingItemDAO;
import laptt.tblShoppingItem.TblShoppingItemDTO;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart extends HashMap<String, Integer> implements Serializable{

    public Map<String, Integer> getItems() {
        return this;
    }

    public void addItemToCart(String id, int quantity) {
        if (this.containsKey(id)) {
            quantity += this.get(id);
        }
        this.put(id, quantity);
    }

    public boolean removeItemFromCart(String id) {
        this.remove(id);
        return true;
    }

    public int updateItemQuantity(String id, int quantity) throws SQLException, NamingException {
        TblShoppingItemDAO tblShoppingItemDAO = new TblShoppingItemDAO();
        tblShoppingItemDAO.setTblShoppingItemDTOList();

        int shoppingItemQuantity = 0;
        int isUpdateQuantity = -1;

        for (TblShoppingItemDTO tblShoppingItemDTO : tblShoppingItemDAO.getTblShoppingItemDTOList()) {
            if (tblShoppingItemDTO.getShoppingItemId().equals(id)) {
                shoppingItemQuantity = tblShoppingItemDTO.getShoppingItemQuantity();
                break;
            }
        }

        if (quantity < 1 || quantity > shoppingItemQuantity) {
            isUpdateQuantity = shoppingItemQuantity;
        } else {
            this.replace(id, quantity);
        }

        return isUpdateQuantity;
    }
}
