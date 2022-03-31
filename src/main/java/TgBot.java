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

        if (update.hasMessage() && update.getMessage().hasText()) {
            setUSER_ID(update.getMessage().getFrom().getId());
            long chat_id = update.getMessage().getChatId();
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


            break;

            case CHOOSE_GROUP_ON_UPDATE:

                groupIdContainer =Integer.parseInt(update.getMessage().getText());

                sendMessage(chatId,"Что вы хотите обновить в команде?");
                sendMessage(chatId,"Напишите /inputuser для добавления людей в группу");
                sendMessage(chatId,"Напишите /deleteuserfromgroup для удаления пользователя из группы");
                sendMessage(chatId,"Напишите /choiceuserrole для выбора роли пользователя");
                sendMessage(chatId,"Напишите /changegroupcolor для изменения цвета группы");
                sendMessage(chatId,"Напишите /creategroup для создания группы в команде");
                sendMessage(chatId,"Напишите /updategroupcolor для обновления группы");
                sendMessage(chatId,"Напишите /deletegroup для удаления группы");


                break;
            case UPDATE_COLOR:
                //GroupEntity groupEntity = getGroupById(groupIdContainer);
                // groupEntity.setColor(update.getMessage().getText());
                // updateGroup(groupEntity);
                sendMessage(chatId,"Цвет группы успешно обновлен");
                break;
            case INPUT_GROUP_USERS:
                int someId = Integer.parseInt(update.getMessage().getText());
                //addUserToGroup(groupIdContainer, someId,UserRole.SLAVE)
                sendMessage(chatId,"Пользователь - "+ someId+" добавлен в команду - " + "getGroupById(groupIdContainer)"); //убрать кавычки у метода
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_MAIN_MENU);
                break;
            case ON_CHOICE_USER_ROLE:

                otherId = Integer.parseInt(update.getMessage().getText());
                sendMessage(chatId,"Введите 0 чтобы установить пользователю роль студента, либо 1, чтобы установить роль тимлида ");
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.CHOICE_USER_ROLE);

                break;
            case CHOICE_USER_ROLE:
                if(update.getMessage().getText().equals("1")){
                    //choiceRoleUserInGroup(groupIdContainer,someId,"не знаю какая роль прописана в бд для админа, вставьте сюда значение")
                    sendMessage(chatId,"Для пользователя с if "+ otherId +" установлена роль"+ UserRole.ADMIN);
                }
                else{
                    //choiceRoleUserInGroup(groupIdContainer,someId,"не знаю какая роль прописана в бд для студента, вставьте сюда значение")
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

                otherId = Integer.parseInt(update.getMessage().getText());
                //deleteGroup( id);
                sendMessage(chatId,"группа с id "+update.getMessage().getText() +" удалена");
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_MAIN_MENU);

                break;

            case DELETE_USER_FROM_GROUP:
                otherId = Integer.parseInt(update.getMessage().getText());
                //removeUserFromGroup(otherId)
                sendMessage(chatId,"Пользователь с id  - "+Integer.parseInt(update.getMessage().getText())+" удален");

                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_MAIN_MENU);

                break;


        }
    }

    public void commandMessage(long chatId, Update update) {


        switch (update.getMessage().getText()) {
            case("/start"):case("Старт"):

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
                sendMessage(chatId,"Введите id группы ");
                //List<GroupEntity> groupEntity= getGroupsByLector(getUSER_ID());
                //sendMessage(chatId,groupEntity);
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.DELETE_GROUP);

                break;
            case("/inputuser"):

                sendMessage(chatId,"Напишите id пользователя котороого хотите добавить в команду");
                //List<UserEntity> list=getAllUsersInGroup(groupIdContainer);
                //sendMessage(list); - тоит использовать стрим и вывести все это нормально, но пока пусть будет так
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.INPUT_GROUP_USERS);
                break;
            case("/choiceuserrole"):

                sendMessage(chatId,"Напишите id пользователя которому хотите поменять роль");
                //List<UserEntity> list=getAllUsersInGroup(groupIdContainer); надеюсь, что в этом ентити есть роль
                //sendMessage(list);
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_CHOICE_USER_ROLE);
                break;
            case("/deleteuserfromgroup"):
                sendMessage(chatId,"Напишите id пользователя которому хотите удалить из группы");
                //List<UserEntity> list=getAllUsersInGroup(groupIdContainer);
                //sendMessage(list);
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.DELETE_USER_FROM_GROUP);
                break;
            case("/showcommand"): case("Показать команду"):
                //List<UserEntity> list = getAllUsers();
                //sendMessage(chatId,list);
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.SHOW_COMMAND);

                break;
            case("/updategroup"):
                //List<GroupEntity> groupEntity= getGroupsByLector(getUSER_ID());
                //sendMessage(chatId,groupEntity);
                sendMessage(chatId,"Напишите id группы, которую хотите изменить");

                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.CHOOSE_GROUP_ON_UPDATE);
                break;
            case("/updategroupcolor"):
                //List<GroupEntity> groupEntity= getGroupsByLector(getUSER_ID());
                //sendMessage(chatId,groupEntity);
                sendMessage(chatId,"Напишите новый цвет для группы");

                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.UPDATE_COLOR);
                break;
            case("/trackingon"):
                sendMessage(chatId,"Вы затрекались");
                //trackingOn(getUSER_ID());
                botStateCache.setUsersCurrentBotState(getUSER_ID(),BotState.ON_MAIN_MENU);
                break;
            case("токен"):
                sendMessage(chatId,String.valueOf(botStateCache.getUsersCurrentBotState(getUSER_ID())));

                break;
            default:
                //???
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
