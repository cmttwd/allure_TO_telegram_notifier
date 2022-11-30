package notifier.api;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;

import static io.restassured.RestAssured.given;
import static notifier.Parameters.getParameters;

public class TelegramApi {

    public static void sendPhotoWithTextApi(byte[] photo, String text) {
        given().spec(Specs.telegramRequest)
                .multiPart("photo", "image.png", new ByteArrayInputStream(photo), "image/png")
                .multiPart("chat_id", getParameters().getTelegramChatId())
                .multiPart("caption", text)
                .params("disable_notification", true)
                .post("/bot{token}/sendPhoto", getParameters().getTelegramToken())
                .then()
                .statusCode(200);
    }

    public static void sendTextApi(String text) {
        given().spec(Specs.telegramRequest)
                .multiPart("chat_id", getParameters().getTelegramChatId())
                .multiPart("text", text)
                .post("/bot{token}/sendMessage", getParameters().getTelegramToken())
                .then()
                .statusCode(200);
    }

    public static void sendText(String text) throws TelegramApiException {
        getSender().execute(SendMessage.builder()
                .chatId(getParameters().getTelegramChatId())
                .text(text)
                .parseMode("HTML")
                .build());
    }

    public static void sendPhotoWithText(byte[] photo, String text) throws TelegramApiException {
        getSender().execute(SendPhoto.builder()
                .chatId(getParameters().getTelegramChatId())
                .photo(new InputFile(new ByteArrayInputStream(photo), "image.png"))
                .parseMode("HTML")
                .caption(text)
                .build());
    }

    private static AbsSender getSender() {
        return new DefaultAbsSender(new DefaultBotOptions()) {
            @Override
            public String getBotToken() {
                return getParameters().getTelegramToken();
            }
        };
    }

}
