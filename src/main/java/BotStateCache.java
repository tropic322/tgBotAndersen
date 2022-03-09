import java.util.HashMap;
import java.util.Map;

public class BotStateCache {
    private Map<Long, BotState> usersBotStates = new HashMap<>();

    public void setUsersCurrentBotState(long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }


    public BotState getUsersCurrentBotState(long userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.ON_MAIN_MENU;
        }

        return botState;
    }


}