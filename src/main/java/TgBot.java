import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TgBot extends TelegramLongPollingBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private Map<Long ,Command> commands = new HashMap();
    private static int groupIdContainer =0;
    private int commandIdContainer;
    int otherId =0;
    public long getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(long USER_ID) {
        this.USER_ID = USER_ID;
    }

    private long USER_ID;

    BotStateCache botStateCache = new BotStateCache();



    ReplyKeyboardMarkup replyKeyboardMarkup;



    public TgBot(String botName, String botToken ) {
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        initKeyboard();
        //this.botState = BOT_STATE;

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
            //message_text = update.getMessage().getForwardFrom().getId();
            long chat_id = update.getMessage().getChatId();
            //stateAnalyzer(botStateCache.getUsersCurrentBotState(getUSER_ID()));
            //sendMessage(chat_id,"asdasd");
           //sendMessage(chat_id, String.valueOf(update.getMessage().getForwardFrom().getId()));
             commandMessage(chat_id,update);

        }
    }
    public void stateAnalyzer(BotState botState,long chatId, Update update){
        switch (botState){
            case ON_MAIN_MENU:
                sendMessage(chatId,"Приветствую, бот создан, чтобы конролировать рабочие процессы. Для вывода помощи по командам нажмите Помощь");
                break;
            case ON_HELP_MENU:///////////////////////////////////
                sendMessage(chatId,"Этот бот создан, чтобы создавать команды и давать им задания");
                sendMessage(chatId,"Напишите /inputuser для добавления людей в группу");
                sendMessage(chatId,"Напишите /deleteuserfromgroup для удаления пользователя из группы");
                sendMessage(chatId,"Напишите /choiceuserrole для выбора роли пользователя");
                sendMessage(chatId,"Напишите /changegroupcolor для изменения цвета группы");
                sendMessage(chatId,"Напишите /creategroup для создания группы в команде");
                sendMessage(chatId,"Напишите /deletegroup для создания группы в команде");

                //и так далее
            break;

            case CHOOSE_GROUP_ON_UPDATE:
                //получения списка команд, где пользователь является админом
                //проверка на правильность ввода команды
                //Если правильно, то
                groupIdContainer =0;// ввести какое то значение из бд

                sendMessage(chatId,"Что вы хотите обновить в команде?");
                sendMessage(chatId,"Напишите /inputuser для добавления людей в группу");
                sendMessage(chatId,"Напишите /deleteuserfromgroup для удаления пользователя из группы");
                sendMessage(chatId,"Напишите /choiceuserrole для выбора роли пользователя");
                sendMessage(chatId,"Напишите /changegroupcolor для изменения цвета группы");
                sendMessage(chatId,"Напишите /creategroup для создания группы в команде");

                break;

            case INPUT_GROUP_USERS:
                //проверка наличия юзера с таким id в команде
                int someId = 0; // сюда данные из бд
                //addUserToGroup(groupIdContainer, someId,UserRole.SLAVE)
                sendMessage(chatId,"Пользователь - "+ someId+" добавлен в команду - ");
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_MAIN_MENU);
                break;
            case ON_CHOICE_USER_ROLE:

                otherId = Integer.parseInt(update.getMessage().getText());

                sendMessage(chatId,"Введите 0 чтобы установить пользователю роль студента, либо 1, чтобы установить роль тимлида ");
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.CHOICE_USER_ROLE);

                break;
            case CHOICE_USER_ROLE:
                if(update.getMessage().getText().equals("1")){
                    //choiceRoleUserToGroup(someId,UserRole.ADMIN)
                    sendMessage(chatId,"Для пользователя с if "+ otherId +" установлена роль"+ UserRole.ADMIN);
                }
                else{
                    //choiceRoleUserToGroup(someId,UserRole.SLAVE)
                    sendMessage(chatId,"Для пользователя с if "+ otherId +" установлена роль"+ UserRole.SLAVE);
                }

                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_MAIN_MENU);
                break;

            case CREATE_GROUP:
                sendMessage(chatId,"Группа с названием(цветом)  "+ update.getMessage().getText()+ " создана");
                //createGroup(update.getMessage().getText());
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_MAIN_MENU);

                break;

            case DELETE_GROUP:
                //получение id группы по имени из бд
                //deleteGroup( id);
                sendMessage(chatId,"группа с названием "+update.getMessage().getText() +" удалена");
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_MAIN_MENU);

                break;

            case DELETE_USER_FROM_GROUP:
                otherId = Integer.parseInt(update.getMessage().getText());
                //проверка id пользователя на наличие в группе
                //removeUserFromGroup(otherId) -- полный шлак
                sendMessage(chatId,"Пользователь с id  - "+Integer.parseInt(update.getMessage().getText())+" удален");


                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_MAIN_MENU);

                break;


        }
    }

    public void commandMessage(long chatId, Update update) {


        switch (update.getMessage().getText()) {
            case("/start"):case("Старт"):
               // initKeyboard();
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_MAIN_MENU);
                stateAnalyzer(botStateCache.getUsersCurrentBotState(getUSER_ID()),chatId,update);
                break;
            case("/help"): case("Помощь"):
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_HELP_MENU);
                stateAnalyzer(botStateCache.getUsersCurrentBotState(getUSER_ID()),chatId,update);
                break;
            case("/creategroup"):
                sendMessage(chatId,"Введите название(цвет) группы ");
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.CREATE_GROUP);

                break;
            case("/deletegroup"):
                sendMessage(chatId,"Введите название(цвет) группы ");
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.DELETE_GROUP);

                break;
            case("/inputuser"):

                sendMessage(chatId,"Напишите id пользователя котороого хотите добавить в команду");
                //вывод пользователей команды
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.INPUT_GROUP_USERS);
                break;
            case("/choiceuserrole"):

                sendMessage(chatId,"Напишите id пользователя которому хотите поменять роль");
                //вывод людей и их ролей
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_CHOICE_USER_ROLE);
                break;
            case("/deleteuserfromgroup"):
                sendMessage(chatId,"Напишите id пользователя которому хотите удалить из группы");
                //вывод людей и их ролей
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.DELETE_USER_FROM_GROUP);
                break;
            case("/showcommand"): case("Показать команду"):
                //запрос команды из бд
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.SHOW_COMMAND);
                //setBotState(BotState.INPUT_COMMAND_NAME);
                Command command = commands.get(commandIdContainer);//заглушка, убрать потом
                sendMessage(chatId,"Состав комманды "+ command);
                break;

            case("токен"):
                sendMessage(chatId,String.valueOf(botStateCache.getUsersCurrentBotState(getUSER_ID())));

                break;
            default:
                stateAnalyzer(botStateCache.getUsersCurrentBotState(getUSER_ID()),chatId,update);
        }


    }


    void initKeyboard()
    {
        //Создаем объект будущей клавиатуры и выставляем нужные настройки
         replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);

        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        //Создаем один ряд кнопок и добавляем его в список
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRows.add(keyboardRow);
        //Добавляем
        keyboardRow.add(new KeyboardButton("Старт"));
        keyboardRow.add(new KeyboardButton("Помощь"));

        //добавляем лист с одним рядом кнопок в главный объект
        replyKeyboardMarkup.setKeyboard(keyboardRows);

    }
    public void sendMessage(long chatId, String textMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textMessage);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
