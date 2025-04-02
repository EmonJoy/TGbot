import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.io.IOException;

/*
*
* More  Feature will be added here......
*
* 
*
* */
public class MyBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "coder22bot";
    }

    @Override
    public String getBotToken() {
        return "6817651266:AAHDzZjfl3JyMhubKWcg-_hmTvqiCPw1JSY";
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText().trim();
            long chatId = update.getMessage().getChatId();

            String weatherInfo;
            try {
                weatherInfo = WeatherDetails.getWeatherInfo(text);
            } catch (IOException | InterruptedException e) {
                weatherInfo = "⚠️ Error";
            }

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
           // message.setText(weatherInfo);
            if (text.equalsIgnoreCase("/About")) {
                message.setText("\uD83C\uDF2A\uFE0F WeatherBot by EmonJoy! \uD83D\uDE80" +
                        " Type any city, get real-time weather—temp, humidity, " +
                        "vibes! Stay fresh, stay updated! \uD83D\uDD25☁\uFE0F");
            } else {
                message.setText(weatherInfo);
            }


            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }


        }
    }

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyBot());
            System.out.println("Bot is running...");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
