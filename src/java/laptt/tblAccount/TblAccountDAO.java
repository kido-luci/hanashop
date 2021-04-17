package laptt.tblAccount;

import laptt.utilities.DBUtilities;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TblAccountDAO implements Serializable {

    //get account by user id and password
    public TblAccountDTO getTblAccountDTO(String userId, String password) throws SQLException, NamingException {

        TblAccountDTO tblAccountDTO = null;

        Connection connection = DBUtilities.getConnection();
        if (connection != null) {
            String sql = "SELECT [userId]\n" +
                    "      ,[password]\n" +
                    "      ,[fullName]\n" +
                    "      ,[roleId]\n" +
                    "      ,[gmail]\n" +
                    "  FROM [LAB1SE141031].[dbo].[tblAccount]\n" +
                    "  WHERE userId = ? AND password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int roleId = resultSet.getInt("roleId");
                String fullName = roleId == 1 ? ("Admin, ") + resultSet.getString("fullName") : resultSet.getString("fullName");
                tblAccountDTO = new TblAccountDTO(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        fullName,
                        roleId,
                        resultSet.getString("gmail")
                );
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return tblAccountDTO;
    }

    //get account by user gmail
    public TblAccountDTO getTblAccountDTO(String gmail) throws SQLException, NamingException {

        TblAccountDTO tblAccountDTO = null;

        Connection connection = DBUtilities.getConnection();
        if (connection != null) {
            String sql = "SELECT [userId]\n" +
                    "      ,[password]\n" +
                    "      ,[fullName]\n" +
                    "      ,[roleId]\n" +
                    "      ,[gmail]\n" +
                    "  FROM [LAB1SE141031].[dbo].[tblAccount]\n" +
                    "  WHERE gmail = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, gmail);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int roleId = resultSet.getInt("roleId");
                String fullName = roleId == 1 ? ("Admin, ") + resultSet.getString("fullName") : resultSet.getString("fullName");
                tblAccountDTO = new TblAccountDTO(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        fullName,
                        roleId,
                        resultSet.getString("gmail")
                );
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return tblAccountDTO;
    }
}
