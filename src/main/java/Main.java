import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Main {


    public static void main(String[] args)  {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TgBot("StepanAndersonovich_bot", "5232982059:AAEbtLq2PGXql-XD3hZSn2u_bhSRrj7_ZQw"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
    }
}
}
