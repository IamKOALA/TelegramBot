import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    try {
                        execute(sendMessage.setText("Oh, hello there \nWrite /help for more info :0"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/help":
                    try {
                        execute(sendMessage.setText("Here you can configure your notifications at Jira"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    try {
                        execute(sendMessage.setText("I'm actually don't understand, dude :-/"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
            }
        }

    }

    public String getBotUsername() {
        return "GeneralKenobi";
    }

    public String getBotToken() {
        return "1027375170:AAFin-m8T9eXlH9kuRGPUs6agbVdzsRcZPE";
    }
}
