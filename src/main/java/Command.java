import java.util.HashMap;
import java.util.Map;

public class UserCommandCache {

    private Map<Long,UserRole> comandsUsers = new HashMap<>();

    public void inputUser(long userId, UserRole userRole) {
        comandsUsers.put(userId, userRole);
    }


    public UserRole getUserRole(long userId) {
        UserRole userRole = comandsUsers.get(userId);
        /*if (userRole == null) { //?
            userRole = BotState.ON_MAIN_MENU;
        }*/


        return userRole;
    }
}
