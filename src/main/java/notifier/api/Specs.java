package notifier.api;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static notifier.Parameters.getParameters;

public class Specs {

    public static final RequestSpecification telegramRequest = new RequestSpecBuilder()
            .setBaseUri("https://api.telegram.org/")
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.MULTIPART)
            .build();

    public static final RequestSpecification allureRequest = new RequestSpecBuilder()
            .setBaseUri(getParameters().getAllureUri())
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .addHeader("Authorization", getParameters().getAllureToken())
            .build();


}
