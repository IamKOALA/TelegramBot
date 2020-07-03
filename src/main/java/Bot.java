import com.google.inject.internal.cglib.core.$AbstractClassGenerator;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot extends TelegramLongPollingBot {
    HashMap <Long, Boolean> auth = new HashMap<>();

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
        Long curChatId = message.getChatId();
        sendMessage.setChatId(curChatId.toString());

        if (message != null && message.hasText()) {
            String msgText = message.getText();

            if (msgText.equals("/start")) {
                try {
                    auth.put(curChatId, false);
                    execute(sendMessage.setText("Oh, hello there \nWrite /help for more info :0"));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            else if (msgText.equals("/help")) {
                try {
                    execute(sendMessage.setText("Here you can configure your notifications at Jira. Write /auth to start your authorization."));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            else if (msgText.equals("/auth")) {
                try {
                    execute(sendMessage.setText("Firstly, we need to know your personal Jira domain. " +
                            "Usually it looks like <SOME_NAME>.atlassian.net " +
                            "so just write it to me in the same format."));
                    auth.put(curChatId, true);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            //Means that user have chosen /auth on prev step so he should write his domain
            else if (auth.get(curChatId)) {
                try {
                    Pattern pattern = Pattern.compile("^.+[.]atlassian[.]net$");
                    Matcher matcher = pattern.matcher(message.getText());
                    if (matcher.matches()) {
                        execute(sendMessage.setText("Good!"));
                        auth.put(curChatId, false);
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            else {
                try {
                    execute(sendMessage.setText("I'm actually don't understand, dude :-/)"));
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
