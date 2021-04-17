package laptt.tblShoppingItemCategory;

import laptt.utilities.DBUtilities;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TblShoppingItemCategoryDAO implements Serializable {
    public List<String> getShoppingItemCategoryList() throws SQLException, NamingException {
        List<String> shoppingItemCategoryList = null;

        Connection connection = DBUtilities.getConnection();
        if (connection != null) {
            shoppingItemCategoryList = new ArrayList<>();

            String sql = "SELECT DISTINCT category FROM tblShoppingItemCategory";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                shoppingItemCategoryList.add(resultSet.getString("category"));
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return shoppingItemCategoryList;
    }
}
