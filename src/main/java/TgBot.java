import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class TgBot extends TelegramLongPollingBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;

    public long getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(long USER_ID) {
        this.USER_ID = USER_ID;
    }

    private long USER_ID;
    //private BotState botState = BotState.ON_START;
    BotStateCache botStateCache = new BotStateCache();
   // public BotState getBotState() {
      //  return botState;
    //}
    //public void setBotState(BotState BOT_STATE) {
     //   this.botState = BOT_STATE;
    //}


    ReplyKeyboardMarkup replyKeyboardMarkup;

    private TelegramFacade telegramFacade;

    public TgBot(String botName, String botToken ) {
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;

        //this.botState = BOT_STATE;
        initKeyboard();
        //создаём вспомогательный класс для работы с сообщениями, не являющимися командами
        /*this.nonCommand = new NonCommand();
        //регистрируем команды
        register(new StartCommand("start", "Старт"));
        register(new PlusCommand("plus", "Сложение"));
        register(new MinusCommand("minus", "Вычитание"));
        register(new PlusMinusCommand("plusminus", "Сложение и вычитание"));
        register(new HelpCommand("help","Помощь"));
        register(new SettingsCommand("settings", "Мои настройки"));*/


    }


    @Override
    public void onUpdateReceived(Update update) {

        //положение автомата бота
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            setUSER_ID(update.getMessage().getFrom().getId());
            //botStateCache.getUsersCurrentBotState();
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            stateAnalyzer(botStateCache.getUsersCurrentBotState(getUSER_ID()));
             commandMessage(chat_id,message_text);

        }
    }
    public void stateAnalyzer(BotState botState){
        switch (botState){
            case ON_HELP_MENU:case ON_MAIN_MENU:
                String a;
            break;
            case INPUT_COMMAND_SLAVE:
                String ab;
                break;
            case INPUT_COMMAND_NAME:
                String c;
                break;
            /*case INPUT_COMMAND_NAME:
                String d;
                break;*/


        }
    }

    public void commandMessage(long chatId, String textMsg) {
        String response;

        switch (textMsg) {
            case("/start"):
                sendMessage(chatId,"Приветствую, бот создан, чтобы конролировать рабочие процессы, но пока что он ничего не умеет");
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_MAIN_MENU);
               // setBotState(BotState.ON_MAIN_MENU);
                break;
            case("/help"): case("Помощь"):
                sendMessage(chatId,"Этот бот создан, чтобы делать страшные вещи");
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_HELP_MENU);
                //setBotState(BotState.ON_HELP_MENU);
                break;
            case("/createcomand"): case("Создать команду"):
                sendMessage(chatId,"Введите имя команды");
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.INPUT_COMMAND_NAME);
                //setBotState(BotState.INPUT_COMMAND_NAME);
                createCommand(chatId);
                break;
            case("токен"):
                response = String.valueOf(botStateCache.getUsersCurrentBotState(getUSER_ID()));
                //setBotState(BotState.SHOW_HELP_MENU);
                break;
            default:
                response = null;
        }

       // return response;
    }
    public void createCommand(long chatId) {
        sendMessage(chatId, "Введите название команды");

    }
    void initKeyboard()
    {
        //Создаем объект будущей клавиатуры и выставляем нужные настройки
         replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(false); //скрываем после использования

        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        //Создаем один ряд кнопок и добавляем его в список
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRows.add(keyboardRow);
        //Добавляем одну кнопку с текстом "Просвяти" наш ряд
        keyboardRow.add(new KeyboardButton("Помощь"));
        keyboardRow.add(new KeyboardButton("Создать команду"));
        //добавляем лист с одним рядом кнопок в главный объект
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }
    public void sendMessage(long chatId, String textMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textMessage);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    /*
    public void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendInlineKeyBoardMessage(long chatId, String messageText, String buttonText, String callbackData) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton(buttonText);

        if (callbackData != null) {
            keyboardButton.setCallbackData(callbackData);
        }

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(keyboardButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage sm = new SendMessage(String.valueOf(chatId),messageText);
        sm.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendAnswerCallbackQuery(String callbackId, String message) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackId);
        answer.setText(message);
        answer.setShowAlert(true);

        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendChangedInlineButtonText(CallbackQuery callbackQuery, String buttonText, String callbackData) {
        final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        final List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        final long message_id = callbackQuery.getMessage().getMessageId();
        final long chat_id = callbackQuery.getMessage().getChatId();
        final List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(buttonText).setCallbackData(callbackData));
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);

        EditMessageText editMessageText = new EditMessageText().setChatId(String.valueOf(chat_id)).setMessageId( (message_id)).
                setText(callbackQuery.getMessage().getText());

        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
