

public class ChargeSheet {
    
    private int caseId;
    private String defendant;
    private String accuser;
    private String prosecutor;
    private String description;

    public ChargeSheet(int caseId, String defendant, String accuser, String prosecutor, String description) {
        this.caseId = caseId;
        this.defendant = defendant;
        this.accuser = accuser;
        this.prosecutor = prosecutor;
        this.description = description;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getAccuser() {
        return accuser;
    }

    public void setAccuser(String accuser) {
        this.accuser = accuser;
    }

    public String getProsecutor() {
        return prosecutor;
    }

    public void setProsecutor(String prosecutor) {
        this.prosecutor = prosecutor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
