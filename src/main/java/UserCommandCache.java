import java.util.HashMap;
import java.util.Map;

public class UserCommandCache {

    private Map<Long,UserRole> usersCommands = new HashMap<>();

    public void setUsersCurrentBotState(long commandId, UserRole userRole) {
        usersCommands.put(commandId, userRole);
    }


    public UserRole getUsersCurrentBotState(long commandId) {
        UserRole userRole = usersCommands.get(commandId);
        /*if (userRole == null) { //?
            userRole = BotState.ON_MAIN_MENU;
        }*/

        return userRole;
    }
}
