package notifier;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Parameters {
    private static Parameters PARAMETERS;

    private String telegramToken;
    private String telegramChatId;

    private String allureUri;
    private String allureToken;
    private String allureBuildId;

    public static Parameters getParameters() {
        if (PARAMETERS == null) PARAMETERS = initAndGetParameters();
        return PARAMETERS;
    }


    private static Parameters initAndGetParameters(){
        Parameters param = new Parameters();

        param.telegramToken = getProperty("telegramToken", true);
        param.telegramChatId = getProperty("telegramChatId", true);

        param.allureUri = getProperty("allureUri", true);
        param.allureToken = getProperty("allureToken", true);
        param.allureBuildId = getProperty("allureBuildId", true);

        return param;
    }


    private static String getProperty(String name, boolean required) {
        Optional<String> prop = Optional.ofNullable(System.getProperty(name));

        if (required)
            return prop.orElseThrow(() -> new RuntimeException("Required parameter missing '" + name + "'"));

        return prop.orElse(null);
    }
}
