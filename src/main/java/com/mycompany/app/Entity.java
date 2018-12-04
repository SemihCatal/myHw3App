public class Entity {
    private String Fname;
    private String Lname;
    private String entity_id;
    
    public String getEntity_id() {
        return entity_id;
    }
    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }
    public String getLname() {
        return Lname;
    }
    public void setLname(String lname) {
        Lname = lname;
    }
    public String getFname() {
        return Fname;
    }
    public void setFname(String fname) {
        Fname = fname;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Entity ID "+ getEntity_id()+"\n");
        sb.append("First Name "+ getFname()+"\n");
        sb.append("Last name "+ getLname()+"\n");
        return sb.toString();
    }
    
}
