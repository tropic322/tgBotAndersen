import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramFacade {
    private UserDataCache userDataCache;
    private BotStateCache botStateContext;
    private CallbackQueryFacade callbackQueryFacade;

    public TelegramFacade(UserDataCache userDataCache, BotStateCache botStateContext,
                          CallbackQueryFacade callbackQueryFacade) {
        this.userDataCache = userDataCache;
        this.botStateContext = botStateContext;
        this.callbackQueryFacade = callbackQueryFacade;
    }

    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            //log.info("New callbackQuery from User: {} with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                  //  update.getCallbackQuery().getData());
           // return callbackQueryFacade.processCallbackQuery(update.getCallbackQuery());
        }


        Message message = update.getMessage();
        if (message != null && message.hasText()) {
           // log.info("New message from User:{}, chatId: {},  with text: {}",
               //     message.getFrom().getUserName(), message.getChatId(), message.getText());
           // replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    /*private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        Long userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {

            case "Помощь":
                botState = BotState.SHOW_HELP_MENU;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }

        userDataCache.setUsersCurrentBotState(userId, botState);

        //replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }
*/

}
