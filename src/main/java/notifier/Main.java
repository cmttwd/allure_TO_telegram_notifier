package notifier;

import lombok.extern.log4j.Log4j2;
import notifier.api.TelegramApi;
import notifier.chart.ChartBuilder;
import notifier.data.Data;
import notifier.helpers.StringHelpers;
import notifier.message.MessageBuilder;
import notifier.model.LaunchInfo;

import java.util.Date;
import java.util.List;

import static notifier.Parameters.getParameters;


@Log4j2
public class Main {

    public static void main(String[] args) {
        startApplication();
    }

    private static void startApplication() {
        log.info("Start...");
        Date start = new Date();

        try {
            LaunchInfo launchInfo = Data.getLaunchInfo(getParameters().getAllureBuildId());

            String message = MessageBuilder.getMessage(launchInfo);

            byte[] chartImage = ChartBuilder.getChartImage(launchInfo);

            long duration = new Date().getTime() - start.getTime();
            if (duration > 120_000) message = message + "\n\n Превышено время формирования отчета: " + duration;


            List<String> messageList = StringHelpers.splitStringByCharsCount(message, 1000);

            for (int i = 0; i < messageList.size(); i++) {
                if (i == 0) TelegramApi.sendPhotoWithText(chartImage, messageList.get(i));
                else TelegramApi.sendText(messageList.get(i));
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            System.exit(1);
        }

        log.info("Finish...");
    }
}
