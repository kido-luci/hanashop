package laptt.tblShoppingItem;

import laptt.utilities.DBUtilities;
import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TblShoppingItemDAO implements Serializable {

    private int quantityOfItem;
    private List<TblShoppingItemDTO> tblShoppingItemDTOList;

    public TblShoppingItemDAO() {
        quantityOfItem = 0;
        tblShoppingItemDTOList = new ArrayList<>();
    }

    public List<TblShoppingItemDTO> getTblShoppingItemDTOList() {
        return tblShoppingItemDTOList;
    }

    public int getQuantityOfItem() {
        return quantityOfItem;
    }

    public void setTblShoppingItemDTOList(String searchValue, String shoppingItemCategory, float firstRangeOfPrice, float secondRageOfPrice, int page) throws SQLException, NamingException {
        Connection connection = DBUtilities.getConnection();

        if (connection != null) {

            tblShoppingItemDTOList = new ArrayList<>();

            float minPrice = 0;
            float maxPrice = 0;

            String sql1 = "SELECT [shoppingItemId] \n" +
                    "      ,[shoppingItemName] \n" +
                    "      ,[shoppingItemImage] \n" +
                    "      ,[shoppingItemCategory] \n" +
                    "      ,[shoppingItemDescription] \n" +
                    "      ,[shoppingItemCreateDate] \n" +
                    "      ,[shoppingItemStatus] \n" +
                    "      ,[shoppingItemQuantity] \n" +
                    "      ,[shoppingItemPrice]  \n" +
                    "FROM (SELECT ROW_NUMBER() OVER ( ORDER BY shoppingItemCreateDate DESC ) AS RowNum, \n" +
                    "       [shoppingItemId] \n" +
                    "      ,[shoppingItemName] \n" +
                    "      ,[shoppingItemImage] \n" +
                    "      ,[shoppingItemCategory] \n" +
                    "      ,[shoppingItemDescription] \n" +
                    "      ,[shoppingItemCreateDate] \n" +
                    "      ,[shoppingItemStatus] \n" +
                    "      ,[shoppingItemQuantity] \n" +
                    "      ,[shoppingItemPrice]  \n" +
                    "       FROM tblShoppingItem \n";

            if (firstRangeOfPrice == 0 &&  secondRageOfPrice == 0) {
                sql1 += "       WHERE (shoppingItemName LIKE ? AND shoppingItemCategory LIKE ?)) \n" +
                        "AS RowConstrainedResult WHERE RowNum >= ? AND RowNum <= ? \n" +
                        "ORDER BY RowNum";
            } else {
                sql1 +=  "       WHERE (shoppingItemName LIKE ? AND shoppingItemCategory LIKE ?) AND shoppingItemPrice >= ? AND shoppingItemPrice <= ?) \n" +
                        "AS RowConstrainedResult WHERE RowNum >= ? AND RowNum <= ? \n" +
                        "ORDER BY RowNum";

                maxPrice = Math.max(firstRangeOfPrice, secondRageOfPrice);
                minPrice = Math.min(firstRangeOfPrice, secondRageOfPrice);
            }

            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, "%" + searchValue + "%");
            if (shoppingItemCategory.equals("All")) {
                preparedStatement1.setString(2, "%%");
            } else {
                preparedStatement1.setString(2, shoppingItemCategory);
            }


            if (firstRangeOfPrice == 0 &&  secondRageOfPrice == 0) {
                preparedStatement1.setInt(3, page*6 - 5);
                preparedStatement1.setInt(4, page*6);
            } else {
                preparedStatement1.setFloat(3, minPrice);
                preparedStatement1.setFloat(4, maxPrice);
                preparedStatement1.setInt(5, page*6 - 5);
                preparedStatement1.setInt(6, page*6);
            }

            ResultSet resultSet = preparedStatement1.executeQuery();

            while (resultSet.next()) {
                TblShoppingItemDTO tblShoppingItemDTO = new TblShoppingItemDTO(
                        resultSet.getString("shoppingItemId"),
                        resultSet.getString("shoppingItemName"),
                        resultSet.getString("shoppingItemImage"),
                        resultSet.getString("shoppingItemCategory"),
                        resultSet.getString("shoppingItemDescription"),
                        resultSet.getDate("shoppingItemCreateDate"),
                        resultSet.getBoolean("shoppingItemStatus"),
                        resultSet.getInt("shoppingItemQuantity"),
                        resultSet.getFloat("shoppingItemPrice")
                );

                tblShoppingItemDTOList.add(tblShoppingItemDTO);
            }

            String sql2 = "SELECT COUNT(shoppingItemId) FROM tblShoppingItem \n";

            if (firstRangeOfPrice == 0 &&  secondRageOfPrice == 0) {
                sql2 += "WHERE (shoppingItemName LIKE ? AND shoppingItemCategory LIKE ?)";
            } else {
                sql2 += "WHERE (shoppingItemName LIKE ? AND shoppingItemCategory LIKE ?) AND shoppingItemPrice >= ? AND shoppingItemPrice <= ?";
            }

            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setString(1, "%" + searchValue + "%");
            if (shoppingItemCategory.equals("All")) {
                preparedStatement2.setString(2, "%%");
            } else {
                preparedStatement2.setString(2, shoppingItemCategory);
            }

            if (!(firstRangeOfPrice == 0 &&  secondRageOfPrice == 0)) {
                preparedStatement2.setFloat(3, minPrice);
                preparedStatement2.setFloat(4, maxPrice);
            }

            ResultSet resultSet2 = preparedStatement2.executeQuery();

            if(resultSet2.next()) {
                quantityOfItem = resultSet2.getInt("");
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closeResultSet(resultSet2);
            DBUtilities.closePreparedStatement(preparedStatement1);
            DBUtilities.closePreparedStatement(preparedStatement2);
            DBUtilities.closeConnection(connection);
        }
    }

    public void setTblShoppingItemDTOListUserRole(String searchValue, String shoppingItemCategory, float firstRangeOfPrice, float secondRageOfPrice, int page) throws SQLException, NamingException {
        Connection connection = DBUtilities.getConnection();

        if (connection != null) {

            tblShoppingItemDTOList = new ArrayList<>();

            float minPrice = 0;
            float maxPrice = 0;

            String sql1 = "SELECT [shoppingItemId] \n" +
                    "      ,[shoppingItemName] \n" +
                    "      ,[shoppingItemImage] \n" +
                    "      ,[shoppingItemCategory] \n" +
                    "      ,[shoppingItemDescription] \n" +
                    "      ,[shoppingItemCreateDate] \n" +
                    "      ,[shoppingItemStatus] \n" +
                    "      ,[shoppingItemQuantity] \n" +
                    "      ,[shoppingItemPrice]  \n" +
                    "FROM (SELECT ROW_NUMBER() OVER ( ORDER BY shoppingItemCreateDate DESC ) AS RowNum, \n" +
                    "       [shoppingItemId] \n" +
                    "      ,[shoppingItemName] \n" +
                    "      ,[shoppingItemImage] \n" +
                    "      ,[shoppingItemCategory] \n" +
                    "      ,[shoppingItemDescription] \n" +
                    "      ,[shoppingItemCreateDate] \n" +
                    "      ,[shoppingItemStatus] \n" +
                    "      ,[shoppingItemQuantity] \n" +
                    "      ,[shoppingItemPrice]  \n" +
                    "       FROM tblShoppingItem \n";

            if (firstRangeOfPrice == 0 &&  secondRageOfPrice == 0) {
                sql1 += "       WHERE (shoppingItemName LIKE ? AND shoppingItemCategory LIKE ? AND shoppingItemStatus = 1 AND shoppingItemQuantity > 0)) \n" +
                        "AS RowConstrainedResult WHERE RowNum >= ? AND RowNum <= ? \n" +
                        "ORDER BY RowNum";
            } else {
                sql1 +=  "       WHERE (shoppingItemName LIKE ? AND shoppingItemCategory LIKE ?) AND shoppingItemPrice >= ? AND shoppingItemPrice <= ? AND shoppingItemStatus = 1 AND shoppingItemQuantity > 0) \n" +
                        "AS RowConstrainedResult WHERE RowNum >= ? AND RowNum <= ? \n" +
                        "ORDER BY RowNum";

                maxPrice = Math.max(firstRangeOfPrice, secondRageOfPrice);
                minPrice = Math.min(firstRangeOfPrice, secondRageOfPrice);
            }

            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, "%" + searchValue + "%");
            if (shoppingItemCategory.equals("All")) {
                preparedStatement1.setString(2, "%%");
            } else {
                preparedStatement1.setString(2, shoppingItemCategory);
            }

            if (firstRangeOfPrice == 0 &&  secondRageOfPrice == 0) {
                preparedStatement1.setInt(3, page*6 - 5);
                preparedStatement1.setInt(4, page*6);
            } else {
                preparedStatement1.setFloat(3, minPrice);
                preparedStatement1.setFloat(4, maxPrice);
                preparedStatement1.setInt(5, page*6 - 5);
                preparedStatement1.setInt(6, page*6);
            }

            ResultSet resultSet = preparedStatement1.executeQuery();

            while (resultSet.next()) {
                TblShoppingItemDTO tblShoppingItemDTO = new TblShoppingItemDTO(
                        resultSet.getString("shoppingItemId"),
                        resultSet.getString("shoppingItemName"),
                        resultSet.getString("shoppingItemImage"),
                        resultSet.getString("shoppingItemCategory"),
                        resultSet.getString("shoppingItemDescription"),
                        resultSet.getDate("shoppingItemCreateDate"),
                        resultSet.getBoolean("shoppingItemStatus"),
                        resultSet.getInt("shoppingItemQuantity"),
                        resultSet.getFloat("shoppingItemPrice")
                );

                tblShoppingItemDTOList.add(tblShoppingItemDTO);
            }

            String sql2 = "SELECT COUNT(shoppingItemId) FROM tblShoppingItem \n";

            if (firstRangeOfPrice == 0 &&  secondRageOfPrice == 0) {
                sql2 += "WHERE (shoppingItemName LIKE ? AND shoppingItemCategory LIKE ?)  AND shoppingItemStatus = 1 AND shoppingItemQuantity > 0";
            } else {
                sql2 += "WHERE (shoppingItemName LIKE ? AND shoppingItemCategory LIKE ?) AND shoppingItemPrice >= ? AND shoppingItemPrice <= ? AND shoppingItemStatus = 1 AND shoppingItemQuantity > 0";
            }

            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setString(1, "%" + searchValue + "%");
            if (shoppingItemCategory.equals("All")) {
                preparedStatement2.setString(2, "%%");
            } else {
                preparedStatement2.setString(2, shoppingItemCategory);
            }

            if (!(firstRangeOfPrice == 0 &&  secondRageOfPrice == 0)) {
                preparedStatement2.setFloat(3, minPrice);
                preparedStatement2.setFloat(4, maxPrice);
            }

            ResultSet resultSet2 = preparedStatement2.executeQuery();

            if(resultSet2.next()) {
                quantityOfItem = resultSet2.getInt("");
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closeResultSet(resultSet2);
            DBUtilities.closePreparedStatement(preparedStatement1);
            DBUtilities.closePreparedStatement(preparedStatement2);
            DBUtilities.closeConnection(connection);
        }
    }

    public void setTblShoppingItemDTOList() throws SQLException, NamingException {

        Connection connection = DBUtilities.getConnection();

        if (connection != null) {

            tblShoppingItemDTOList = new ArrayList<>();

            String sql = "SELECT [shoppingItemId] \n" +
                    "      ,[shoppingItemName] \n" +
                    "      ,[shoppingItemImage] \n" +
                    "      ,[shoppingItemCategory] \n" +
                    "      ,[shoppingItemDescription] \n" +
                    "      ,[shoppingItemCreateDate] \n" +
                    "      ,[shoppingItemStatus] \n" +
                    "      ,[shoppingItemQuantity] \n" +
                    "      ,[shoppingItemPrice]  \n" +
                    "       FROM tblShoppingItem \n";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TblShoppingItemDTO tblShoppingItemDTO = new TblShoppingItemDTO(
                        resultSet.getString("shoppingItemId"),
                        resultSet.getString("shoppingItemName"),
                        resultSet.getString("shoppingItemImage"),
                        resultSet.getString("shoppingItemCategory"),
                        resultSet.getString("shoppingItemDescription"),
                        resultSet.getDate("shoppingItemCreateDate"),
                        resultSet.getBoolean("shoppingItemStatus"),
                        resultSet.getInt("shoppingItemQuantity"),
                        resultSet.getFloat("shoppingItemPrice")
                );

                tblShoppingItemDTOList.add(tblShoppingItemDTO);
            }

            quantityOfItem = this.tblShoppingItemDTOList.size();

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }
    }

    public TblShoppingItemDTO getTblShoppingItemDTO(String shoppingItemId) throws SQLException, NamingException {
        TblShoppingItemDTO tblShoppingItemDTO = null;

        Connection connection = DBUtilities.getConnection();
        if (connection != null) {
            String sql = "SELECT [shoppingItemId] \n" +
                    "      ,[shoppingItemName] \n" +
                    "      ,[shoppingItemImage] \n" +
                    "      ,[shoppingItemCategory] \n" +
                    "      ,[shoppingItemDescription] \n" +
                    "      ,[shoppingItemCreateDate] \n" +
                    "      ,[shoppingItemStatus] \n" +
                    "      ,[shoppingItemQuantity] \n" +
                    "      ,[shoppingItemPrice]  \n" +
                    "       FROM tblShoppingItem WHERE shoppingItemId = ?\n";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, shoppingItemId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                tblShoppingItemDTO = new TblShoppingItemDTO(
                        resultSet.getString("shoppingItemId"),
                        resultSet.getString("shoppingItemName"),
                        resultSet.getString("shoppingItemImage"),
                        resultSet.getString("shoppingItemCategory"),
                        resultSet.getString("shoppingItemDescription"),
                        resultSet.getDate("shoppingItemCreateDate"),
                        resultSet.getBoolean("shoppingItemStatus"),
                        resultSet.getInt("shoppingItemQuantity"),
                        resultSet.getFloat("shoppingItemPrice")
                );
            }
        }

        return tblShoppingItemDTO;
    }

    public boolean updateTblShoppingItemDTOInDatabase(String shoppingItemId, String shoppingItemName, String shoppingItemImage, String shoppingItemCategory, String shoppingItemDescription, boolean shoppingItemStatus, int shoppingItemQuantity, float shoppingItemPrice) throws SQLException, NamingException {
        boolean isUpdate = false;

        Connection connection = DBUtilities.getConnection();
        if (connection != null) {
            String sql = "UPDATE [tblShoppingItem] \n" +
                    "SET [shoppingItemName] = ?\n" +
                    "      ,[shoppingItemCategory] = ?\n" +
                    "      ,[shoppingItemDescription] = ?\n" +
                    "      ,[shoppingItemStatus] = ?\n" +
                    "      ,[shoppingItemQuantity] = ?\n" +
                    "      ,[shoppingItemPrice] = ?\n";

            if (shoppingItemImage != null && !shoppingItemImage.isEmpty()) {
                sql += "      ,[shoppingItemImage] = ?\n" +
                        "WHERE [shoppingItemId] = ?";
            } else {
                sql += "WHERE [shoppingItemId] = ?";
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, shoppingItemName);
            preparedStatement.setString(2, shoppingItemCategory);
            preparedStatement.setString(3, shoppingItemDescription);
            preparedStatement.setBoolean(4, shoppingItemStatus);
            preparedStatement.setInt(5, shoppingItemQuantity);
            preparedStatement.setFloat(6, shoppingItemPrice);

            if (shoppingItemImage != null && !shoppingItemImage.isEmpty()) {
                preparedStatement.setString(7, shoppingItemImage);
                preparedStatement.setString(8, shoppingItemId);
            } else {
                preparedStatement.setString(7, shoppingItemId);
            }


            isUpdate = preparedStatement.executeUpdate() > 0;

            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return isUpdate;
    }

    public boolean createTblShoppingItemDTOInDatabase(String shoppingItemName, String shoppingItemImage, String shoppingItemCategory, String shoppingItemDescription, int shoppingItemQuantity, float shoppingItemPrice) throws SQLException, NamingException {
        boolean isCreate = false;

        Connection connection = DBUtilities.getConnection();
        if (connection != null) {
            String sql = "INSERT INTO [tblShoppingItem] ([shoppingItemId]\n" +
                    "      ,[shoppingItemName]\n" +
                    "      ,[shoppingItemCategory]\n" +
                    "      ,[shoppingItemDescription]\n" +
                    "      ,[shoppingItemStatus]\n" +
                    "      ,[shoppingItemQuantity]\n" +
                    "      ,[shoppingItemPrice]\n" +
                    "      ,[shoppingItemImage]\n" +
                    "      ,[shoppingItemCreateDate]\n)" +
                    "VALUES (NEWID(), ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, shoppingItemName);
            preparedStatement.setString(2, shoppingItemCategory);
            preparedStatement.setString(3, shoppingItemDescription);
            preparedStatement.setBoolean(4, true);
            preparedStatement.setInt(5, shoppingItemQuantity);
            preparedStatement.setFloat(6, shoppingItemPrice);
            preparedStatement.setString(7, shoppingItemImage);

            isCreate = preparedStatement.executeUpdate() > 0;

            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return isCreate;
    }

    public boolean updateTblShoppingItemDTOInDatabase(String shoppingItemId, int shoppingItemQuantity) throws SQLException, NamingException {
        boolean isUpdate = false;

        Connection connection = DBUtilities.getConnection();
        if (connection != null) {
            String sql = "UPDATE [tblShoppingItem] \n" +
                    "SET [shoppingItemQuantity] -= ? WHERE shoppingItemId = ?\n";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, shoppingItemQuantity);
            preparedStatement.setString(2, shoppingItemId);

            isUpdate = preparedStatement.executeUpdate() > 0;

            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return isUpdate;
    }
}
