package nasa.apod;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Bot extends TelegramLongPollingBot {

    String name;
    String token;
    String URL = "https://api.nasa.gov/planetary/apod?api_key=YOUR_NASA_APOD_API"; // TODO
    String url;

    public Bot(String name, String token) throws TelegramApiException {

        this.name = name;
        this.token = token;

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            long chatId = update.getMessage().getChatId();

            String[] action = update.getMessage().getText().split(" ");

            // Add your bot replies on a different messages
            switch (action[0]) {

                case "/start":
                case "/image":
                    url = Program.getUrl(URL);
                    sendMessage(url, chatId);
                    break;

                case "/help":
                    sendMessage("Привет, я бот, который будет отправлять тебе ежедневную картинку с сайта NASA. " +
                            "Чтобы попробовать, напиши: /image. " +
                            "Также можешь использовать /date а дальше указать дату в формате ГГГГ-ММ-ДД, " +
                            "чтобы посмотреть картинку за определённый день", chatId);
                    break;

                case "/date":
                    url = Program.getUrl(URL + "&date=" + action[1]);
                    sendMessage(url, chatId);
                    break;

                default:
                    sendMessage("Я не понимаю тебя. Попробуй команду /help, чтобы узнать о возможностях бота", chatId);
            }
        }
    }

    void sendMessage(String msg, long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(msg);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("bruh"); // bruh?
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
