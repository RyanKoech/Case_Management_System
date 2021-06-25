public class Litigants {
    
    private String surName;
    private String firstName;
    private String middleName;
    private int idNo;
    private String email;
    private int roleId;

    public Litigants(String surName, String firstName, String middleName, int idNo, String email, int roleId) {
        this.surName = surName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.idNo = idNo;
        this.email = email;
        this.roleId = roleId;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getIdNo() {
        return idNo;
    }

    public void setIdNo(int idNo) {
        this.idNo = idNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
    
    
    
}
