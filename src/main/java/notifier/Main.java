package notifier;

import lombok.extern.log4j.Log4j2;
import notifier.api.TelegramApi;
import notifier.chart.ChartBuilder;
import notifier.data.Data;
import notifier.message.MessageBuilder;
import notifier.model.LaunchInfo;

import java.util.Date;

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

            String caption = MessageBuilder.getCaption(launchInfo);
            String defectsMessage = MessageBuilder.getDefectsMessage(launchInfo);
            String epicsStatuses = MessageBuilder.getEpicsStatisticMessage(launchInfo);

            byte[] chartImage = ChartBuilder.getChartImage(launchInfo);

            long duration = new Date().getTime() - start.getTime();
            if (duration > 120_000) caption = caption + "\n\n Превышено время формирования отчета: " + duration;

            String[] chatIds = getParameters().getTelegramChatId().replaceAll(" ", "").split(",");

            for (String id: chatIds){
                log.info("SEND MESSAGE to Telegram chat: {}", id);

                TelegramApi.sendPhotoWithText(chartImage, caption, id);
                TelegramApi.sendText("Дефекты: \n" + defectsMessage + "\n**********************" + epicsStatuses, id);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            System.exit(1);
        }

        log.info("Finish...");
    }
}
