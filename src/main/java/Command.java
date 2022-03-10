
import java.util.HashMap;
import java.util.Map;

public class Command {
    String name;
    long idLector;



    long commandId= System.currentTimeMillis();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public Command() {
    }

    public Command(String name, long lector) {
        this.idLector = lector;
        this.name = name;

    }

    @Override
    public String toString() {//шлак
        return "Command{" +
                "name='" + name + '\'' +
                ", commandsUsers=" + commandsUsers +'}';
    }

    private Map<Long,UserRole> commandsUsers = new HashMap<>();

    public void inputUser(long userId, UserRole userRole) {
        commandsUsers.put(userId, userRole);
    }


    public UserRole getUserRole(long userId) {
        UserRole userRole = commandsUsers.get(userId);
        /*if (userRole == null) { //?
            userRole = BotState.ON_MAIN_MENU;
        }*/


        return userRole;
    }
    public long getCommandId() {
        return commandId;
    }

    public void setCommandId(long commandId) {
        this.commandId = commandId;
    }
}
