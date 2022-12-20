package notifier.message;

import lombok.extern.log4j.Log4j2;
import notifier.helpers.StringHelpers;
import notifier.model.LaunchInfo;
import notifier.model.Status;
import notifier.model.StatusData;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.stream.Collectors;

import static notifier.Parameters.getParameters;

@Log4j2
public class MessageBuilder {

    public static String getCaption(LaunchInfo launchInfo){
        StringBuilder sb = new StringBuilder();

        sb.append("<code>Launch ID: </code>").append(launchInfo.getLaunchId()).append("\n");
        sb.append("<code>Время запуска: </code>").append(launchInfo.getStartTime()).append("\n");
        sb.append("<code>Общая продолжительность на 1 поток: </code>").append(launchInfo.getDuration()).append("\n\n");

        String launchTags = launchInfo.getTags().stream().map(s -> "[ " + s + " ]").collect(Collectors.joining(" "));
        sb.append("<code>Теги: </code>").append(launchTags).append("\n\n");

        // Environment
        sb.append(launchInfo.getEnv().entrySet().stream()
                .map(stringStringEntry -> stringStringEntry.getKey() + " = <code>" + stringStringEntry.getValue() + "</code>")
                .collect(Collectors.joining("\n"))).append("\n\n");

        URI uri = UriBuilder.fromUri(URI.create(getParameters().getAllureUri())).replacePath("/launch/" + launchInfo.getLaunchId()).build();
        sb.append(uri);

        return sb.toString();
    }

    private static String getStatisticByEpic(StatusData statusData){

        if (statusData.getStatuses().isEmpty()) return "";

        Long pass = statusData.getStatuses().get(Status.PASSED);
        if(pass == null) pass = 0L;

        long total = statusData.getStatuses().values().stream().mapToLong(v -> v).sum();

        String percent = StringHelpers.getPercent(total, pass);

        String statuses = statusData.getStatuses().entrySet().stream()
                .map(statusLongEntry -> statusLongEntry.getKey().name().charAt(0) + "-" + statusLongEntry.getValue())
                .collect(Collectors.joining("; "));

        return String.format("\n<code>%s => </code>%s<code> %s</code>", statusData.getTitle(), percent, statuses);
    }


    public static String getDefectsMessage(LaunchInfo launchInfo) {
       return launchInfo.getDefects().stream().limit(7)
                .map(stringIntPair -> "<code>" + stringIntPair.string + "</code> - " + stringIntPair.integer)
                .collect(Collectors.joining("\n"));
    }

    public static String getEpicsStatisticMessage(LaunchInfo launchInfo) {
        StringBuilder sb = new StringBuilder();

        launchInfo.getEpicsStatus().forEach(statusData -> {
            sb.append(getStatisticByEpic(statusData));
        });

        return sb.toString();
    }
}
