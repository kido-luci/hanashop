package laptt.tblAccount;

import java.io.Serializable;

public class TblAccountDTO implements Serializable {
    private String userId;
    private String password;
    private String fullName;
    private int roleId;
    private String gmail;

    public TblAccountDTO(String userId, String password, String fullName, int roleId, String gmail) {
        this.userId = userId;
        this.password = password;
        this.fullName = fullName;
        this.roleId = roleId;
        this.gmail = gmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    @Override
    public String toString() {
        return "TblAccountDTO{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", roleId='" + roleId + '\'' +
                ", gmail='" + gmail + '\'' +
                '}';
    }
}
