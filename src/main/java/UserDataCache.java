import java.util.HashMap;
import java.util.Map;

public class UserDataCache  {
    private Map<Long, BotState> usersBotStates = new HashMap<>();
    //private Map<Integer, TrainSearchRequestData> trainSearchUsersData = new HashMap<>();
    //private Map<Long, List<Train>> searchFoundedTrains = new HashMap<>();

    //@Override
    public void setUsersCurrentBotState(Long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    //@Override
    public BotState getUsersCurrentBotState(Long userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.ON_MAIN_MENU;
        }

        return botState;
    }

   /* @Override
    public void saveTrainSearchData(int userId, TrainSearchRequestData trainSearchData) {
        trainSearchUsersData.put(userId, trainSearchData);
    }

    @Override
    public TrainSearchRequestData getUserTrainSearchData(int userId) {
        TrainSearchRequestData trainSearchData = trainSearchUsersData.get(userId);
        if (trainSearchData == null) {
            trainSearchData = new TrainSearchRequestData();
        }

        return trainSearchData;
    }

    @Override
    public void saveSearchFoundedTrains(long chatId, List<Train> foundTrains) {
        searchFoundedTrains.put(chatId, foundTrains);
    }

    @Override
    public List<Train> getSearchFoundedTrains(long chatId) {
        List<Train> foundedTrains = searchFoundedTrains.get(chatId);

        return Objects.isNull(foundedTrains) ? Collections.emptyList() : foundedTrains;
    }*/

}