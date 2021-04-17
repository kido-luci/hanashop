package laptt.tblBill;

import laptt.tblBillItem.TblBillItemDAO;
import laptt.utilities.DBUtilities;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class TblBillDAO implements Serializable {

    private List<TblBillDTO> tblBillDTOList;

    public TblBillDAO() {
        this.tblBillDTOList = new ArrayList<>();
    }

    public TblBillDAO(List<TblBillDTO> tblBillDTOList) {
        this.tblBillDTOList = tblBillDTOList;
    }

    public List<TblBillDTO> getTblBillDTOList() {
        return tblBillDTOList;
    }

    public void setTblBillDTOList(String userId) throws SQLException, NamingException, ParseException {
        Connection connection = DBUtilities.getConnection();

        if (connection != null) {
            this.tblBillDTOList = new ArrayList<>();

            String sql = "SELECT [billId]\n" +
                    "      ,[dateCheckOut]\n" +
                    "      ,[isPayment]\n" +
                    "  FROM [tblBill] WHERE [userId] = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TblBillItemDAO tblBillItemDAO = new TblBillItemDAO();
                tblBillItemDAO.setTblBillItemDTOList(resultSet.getString("billId"));

                this.getTblBillDTOList().add(
                        new TblBillDTO(resultSet.getString("billId"),
                            userId,
                            resultSet.getDate("dateCheckOut"),
                            resultSet.getBoolean("isPayment"),
                            tblBillItemDAO.getTblBillItemDTOList()
                        )
                );
            }
        }
    }

    public boolean insertBillToDatabase(String userId, Map<String, Integer> billItemMap, boolean isPayment) throws SQLException, NamingException {

        boolean isInsert = true;

        Connection connection = DBUtilities.getConnection();
        if (connection != null) {
            String sql1 = "DECLARE @id uniqueidentifier; SET @id = NEWID(); SELECT @id;\n";

            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);

            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                String billId = resultSet.getString("");
                System.out.println("build Id:" + billId);
                if (billId != null) {
                    String sql2 = "INSERT INTO tblBill(billId, userId, dateCheckOut, isPayment) VALUES(?, ?, CURRENT_TIMESTAMP, ?)";

                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setString(1, billId);
                    preparedStatement2.setString(2, userId);
                    preparedStatement2.setBoolean(3, isPayment);

                    isInsert = preparedStatement2.executeUpdate() > 0;

                    if (isInsert) {
                        TblBillItemDAO tblBillItemDAO = new TblBillItemDAO();
                        isInsert = tblBillItemDAO.insertBillItemToDatabase(billId, billItemMap);
                    }
                } else {
                    isInsert = false;
                }
            } else {
                isInsert = false;
            }
        } else {
            isInsert = false;
        }

        return isInsert;
    }
}
