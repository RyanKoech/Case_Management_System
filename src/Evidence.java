public class Evidence {
    
    private String evidence;
    private boolean type;
    private int evidenceId;

    public Evidence(String evidence, boolean type, int evidenceId) {
        this.evidence = evidence;
        this.type = type;
        this.evidenceId = evidenceId;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(int evidenceId) {
        this.evidenceId = evidenceId;
    }
    
    
}
