import java.util.ArrayList;

public class Command {
    String name;
    long commandId= System.currentTimeMillis();

    public void setName(String name) {
        this.name = name;
    }

    public void setIdSlaveList(ArrayList<Integer> idSlaveList) {
        this.idSlaveList = idSlaveList;
    }

    public ArrayList<Integer> getIdSlaveList() {
        return idSlaveList;
    }

    public String getName() {
        return name;
    }

    ArrayList<Integer> idSlaveList = new ArrayList<Integer>();
    public Command() {
    }

    public Command(String name) {
        this.name = name;
    }

    public Command(String name,ArrayList<Integer> idSlaveList) {
        this.name = name;
        this.idSlaveList = idSlaveList;
    }
}
