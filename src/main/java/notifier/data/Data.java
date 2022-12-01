package notifier.data;

import lombok.extern.log4j.Log4j2;
import notifier.api.AllureApi;
import notifier.api.Dto.*;
import notifier.helpers.DateHelper;
import notifier.model.LaunchInfo;
import notifier.model.Status;
import notifier.model.StatusData;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
public class Data {

    public static LaunchInfo getLaunchInfo(String buildId) {
        log.info("Get Launch ID by buildId: {}", buildId);
        String launchId = AllureApi.getLaunchIdByJobId(buildId);
        log.info("Find LAUNCH: {}", launchId);


        LaunchDetailsDto launchDetails = AllureApi.getLaunchById(launchId);
        List<EnvVarValueDto> launchEnv = AllureApi.getEnvByLaunch(launchId);
        List<TestStatusDto> launchStatistic = AllureApi.getLaunchStatistic(launchId);
        List<TestResultDto> testResult = AllureApi.getTestResultByLaunchId(launchId);
        List<DefectCountRowDto> defects = AllureApi.getDefectsByLaunchId(launchId);
        log.info("All data by LAUNCH - {} received", launchId);


        LaunchInfo.LaunchInfoBuilder launchInfoBuilder = LaunchInfo.builder();

        // launchName
        launchInfoBuilder.launchName(launchDetails.getName());

        // launchId
        launchInfoBuilder.launchId(launchId);

        // startTime
        launchInfoBuilder.startTime(DateHelper.localDateTimeToString(DateHelper.getDateTimeFromMilliseconds(launchDetails.getCreatedDate())));

        // tags
        launchInfoBuilder.tags(launchDetails.getTags().stream().map(IdAndNameDto::getName).collect(Collectors.toList()));

        // env
        Map<String, String> envMap = launchEnv.stream().map(envVarValueDto -> Map.of(envVarValueDto.getVariable().getName(), envVarValueDto.getName()))
                .flatMap(it -> it.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.joining("; "))));

        launchInfoBuilder.env(envMap);

        // statuses
        launchInfoBuilder.statuses(launchStatistic.stream()
                .collect(Collectors.toMap(status -> Status.getStatus(status.getStatus()), TestStatusDto::getCount)));

        // duration
        long amountDuration = testResult.stream()
                .mapToLong(TestResultDto::getDuration)
                .sum();
        launchInfoBuilder.duration(DateHelper.millisecondsToHoursMinSec(amountDuration));

        // defects
        launchInfoBuilder.defects(defects.stream()
                .map(defectCountRowDto -> new LaunchInfo.StringIntPair(defectCountRowDto.getName(), defectCountRowDto.getCount()))
                .collect(Collectors.toList()));

        // epicsStatus
        launchInfoBuilder.epicsStatus(getStatusDataByEpic(launchDetails, testResult));

        return launchInfoBuilder.build();
    }


    private static List<StatusData> getStatusDataByEpic(LaunchDetailsDto launchDetailsDto, List<TestResultDto> testResult) {

        Integer launchId = launchDetailsDto.getId();
        Integer projectId = launchDetailsDto.getProjectId();

        Integer epicFieldId = AllureApi.getCustomFieldsByProjectId(projectId).stream()
                .filter(idAndNameDto -> idAndNameDto.getName().equals("Epic"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not Found EPIC custom field"))
                .getId();

        List<String> epics = AllureApi.getCustomFieldsValues(projectId, epicFieldId).stream()
                .map(IdAndNameDto::getName)
                .collect(Collectors.toList());

        return epics.stream().map(s -> {
                    Set<Integer> testCaseByEpic = AllureApi.testResultSearchByCfAndLaunchId(projectId, "Epic", s, launchId).stream()
                            .map(TestResultRowDto::getTestCaseId).collect(Collectors.toSet());

                    if (testCaseByEpic.size() == 0) return null;

                    Map<Status, Long> statuses = testResult.stream()
                            .filter(testResultDto -> testCaseByEpic.contains(testResultDto.getTestCaseId()))
                            .map(testResultDto -> Status.getStatus(testResultDto.getStatus()))
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

                    return StatusData.builder()
                            .title(s)
                            .statuses(statuses)
                            .build();

                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

    }


}
