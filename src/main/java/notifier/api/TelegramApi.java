package notifier.api;

import java.io.ByteArrayInputStream;

import static io.restassured.RestAssured.given;
import static notifier.Parameters.getParameters;

public class TelegramApi {

    public static void sendPhotoWithText(byte[] photo, String text){
        given().spec(Specs.telegramRequest)
                .multiPart("photo", "image.png", new ByteArrayInputStream(photo), "image/png")
                .multiPart("chat_id", getParameters().getTelegramChatId())
                .multiPart("caption", text)
                .post("/bot{token}/sendPhoto", getParameters().getTelegramToken())
                .then()
                .statusCode(200);
    }

    public static void sendText(String text){
        given().spec(Specs.telegramRequest)
                .multiPart("chat_id", getParameters().getTelegramChatId())
                .multiPart("text", text)
                .post("/bot{token}/sendMessage", getParameters().getTelegramToken())
                .then()
                .statusCode(200);
    }

}
