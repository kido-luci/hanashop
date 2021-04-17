package laptt.tblBillItem;

import laptt.tblShoppingItem.TblShoppingItemDAO;
import laptt.utilities.DBUtilities;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TblBillItemDAO implements Serializable {

    private List<TblBillItemDTO> tblBillItemDTOList;

    public TblBillItemDAO() {
        this.tblBillItemDTOList = new ArrayList<>();
    }

    public TblBillItemDAO(List<TblBillItemDTO> tblBillItemDTOList) {
        this.tblBillItemDTOList = tblBillItemDTOList;
    }

    public void setTblBillItemDTOList(String billId) throws SQLException, NamingException {
        Connection connection = DBUtilities.getConnection();
        if (connection != null) {
            this.tblBillItemDTOList = new ArrayList<>();

            String sql = "SELECT billItemId, quantity, price FROM tblBillItem WHERE billID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, billId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                this.tblBillItemDTOList.add(new TblBillItemDTO(
                        resultSet.getString("billItemId"),
                        billId,
                        resultSet.getInt("quantity"),
                        resultSet.getFloat("price")
                ));
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }
    }

    public boolean insertBillItemToDatabase(String billId, Map<String, Integer> mapBillItem) throws SQLException, NamingException {

        boolean isInsert = false;

        Connection connection = DBUtilities.getConnection();
        if (connection != null) {
            String sql = "INSERT INTO tblBillItem(billId, billItemId, quantity, price) VALUES(?, ?, ?, (SELECT shoppingItemPrice FROM tblShoppingItem WHERE shoppingItemId = ?))";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            Set<String> listBillItemId = mapBillItem.keySet();
            for (String billItemId : listBillItemId) {
                preparedStatement.setString(1, billId);
                preparedStatement.setString(2, billItemId);
                preparedStatement.setInt(3, mapBillItem.get(billItemId));
                preparedStatement.setString(4, billItemId);

                //If insert failed break loop
                isInsert = preparedStatement.executeUpdate() > 0;

                TblShoppingItemDAO tblShoppingItemDAO = new TblShoppingItemDAO();
                tblShoppingItemDAO.updateTblShoppingItemDTOInDatabase(billItemId, mapBillItem.get(billItemId));
            }

            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return isInsert;
    }

    public List<TblBillItemDTO> getTblBillItemDTOList() {
        return tblBillItemDTOList;
    }

    public void setTblBillItemDTOList(List<TblBillItemDTO> tblBillItemDTOList) {
        this.tblBillItemDTOList = tblBillItemDTOList;
    }
}
