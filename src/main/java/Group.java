public class Group {
    int id = Integer.parseInt(String.valueOf(System.currentTimeMillis()));
    String collor;
    int teamleadID;

    public Group( String collor) {
        this.collor = collor;

    }

    public int getTeamleadID() {
        return teamleadID;
    }

    public void setTeamleadID(int teamleadID) {
        this.teamleadID = teamleadID;
    }

    public String getCollor() {
        return collor;
    }

    public void setCollor(String collor) {
        this.collor = collor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
