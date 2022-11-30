package notifier.api;

import lombok.extern.log4j.Log4j2;
import notifier.api.Dto.*;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

@Log4j2
public class AllureApi {

    public static final String jobById = "/jobrun/{id}";

    public static final String testResult = "/testresult";
    public static final String testresultSearch = "/testresult/__search";

    public static final String launchStatistic = "/launch/{id}/statistic";
    public static final String launchEnv = "/launch/{id}/env";
    public static final String launchById = "/launch/{id}";

    public static final String cf = "/cf";
    public static final String cfSuggest = "/cfv/suggest";



    public static String getLaunchIdByJobId(String jobId) {
        log.info("[AllureApi] getLaunchIdByJobId - {}", jobId);

        return given().spec(Specs.allureRequest)
                .get(jobById, jobId)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath().get("launchId")
                .toString();
    }

    public static List<TestStatusDto> getLaunchStatistic(String id) {
        log.info("[AllureApi] getLaunchStatistic - {}", id);

        try {
            return Arrays.stream(given().spec(Specs.allureRequest)
                            .get(launchStatistic, id)
                            .then()
                            .statusCode(200)
                            .extract()
                            .as(TestStatusDto[].class))
                    .collect(Collectors.toList());
        } catch (AssertionError e) {
            throw new RuntimeException(String.format("[LaunchStatistic] Get data ERROR: %S\n%s", id, e.getMessage()));
        }
    }

    public static LaunchDetailsDto getLaunchById(String id) {
        log.info("[AllureApi] getLaunchById - {}", id);

        try {
            return given().spec(Specs.allureRequest)
                    .get(launchById, id)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(LaunchDetailsDto.class);
        } catch (AssertionError e) {
            throw new RuntimeException(String.format("[LaunchById] Get data ERROR: %s\n%s", id, e.getMessage()));
        }
    }



    public static PageTestResultDto getPageTestResultByLaunchId(String id, int page) {
        log.info("[AllureApi] getPageTestResultByLaunchId - {}, {}", id, page);

        Map<String, Object> params = new HashMap<>();
        params.put("launchId", id);
        params.put("size", 2000);
        params.put("page", page);

        try {
            return given().spec(Specs.allureRequest)
                    .queryParams(params)
                    .get(testResult)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(PageTestResultDto.class);
        } catch (AssertionError e) {
            throw new RuntimeException(String.format("[TestResultByLaunchId] Get data ERROR: %s\n%s", id, e.getMessage()));
        }
    }

    public static List<TestResultDto> getTestResultByLaunchId(String id){
        List<TestResultDto> result = new ArrayList<>();
        int page = 0;
        do {
            PageTestResultDto pageTestResult = getPageTestResultByLaunchId(id, page);
            result.addAll(pageTestResult.getContent());
            page++;
            if (page >= pageTestResult.getTotalPages()) break;
        } while (page < 10);

        return result;
    }


    public static PageTestResultRowDto pageTestResultSearchByCfAndLaunchId(Integer projectId, String customFieldName, String customFieldValue, Integer launchId, int page) {

        log.info("[AllureApi] pageTestResultSearchByCfAndLaunchId - {}, {}, {}, {}, {}", projectId, customFieldName, customFieldValue, launchId, page);

        Map<String, Object> params = new HashMap<>();
        params.put("rql", String.format("launch = %d and cf[\"%s\"] = \"%s\"", launchId, customFieldName, customFieldValue));
        params.put("projectId", projectId);
        params.put("size", 10000);
        params.put("page", page);

        try {
            return given().spec(Specs.allureRequest)
                    .queryParams(params)
                    .get(testresultSearch)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(PageTestResultRowDto.class);
        } catch (AssertionError e) {
            throw new RuntimeException(String.format("[CustomFieldsValues] Get data ERROR: %s", e.getMessage()));
        }
    }

    public static List<TestResultRowDto> testResultSearchByCfAndLaunchId(Integer projectId, String customFieldName, String customFieldValue, Integer launchId){
        List<TestResultRowDto> result = new ArrayList<>();

        int page = 0;
        do {
            PageTestResultRowDto pageTestResult = pageTestResultSearchByCfAndLaunchId(projectId, customFieldName, customFieldValue, launchId, page);
            result.addAll(pageTestResult.getContent());
            page++;
            if (page >= pageTestResult.getTotalPages()) break;
        } while (page < 10);

        return result;
    }


    public static List<EnvVarValueDto> getEnvByLaunch(String id) {
        log.info("[AllureApi] getEnvByLaunch - {}", id);

        try {
            return Arrays.stream(given().spec(Specs.allureRequest)
                            .get(launchEnv, id)
                            .then()
                            .statusCode(200)
                            .extract()
                            .as(EnvVarValueDto[].class))
                    .collect(Collectors.toList());
        } catch (AssertionError e) {
            throw new RuntimeException(String.format("[EnvByLaunch] Get data ERROR: %d\n%s", id, e.getMessage()));
        }
    }


    public static List<IdAndNameDto> getCustomFieldsByProjectId(Integer projectId) {
        log.info("[AllureApi] getCustomFieldsByProjectId - {}", projectId);

        Map<String, Object> params = new HashMap<>();
        params.put("projectId", projectId);

        try {
            return Arrays.stream(given().spec(Specs.allureRequest)
                    .queryParams(params)
                    .get(cf)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(IdAndNameDto[].class))
                    .collect(Collectors.toList());
        } catch (AssertionError e) {
            throw new RuntimeException(String.format("[CustomFieldsByLaunchId] Get data ERROR: %s\n%s", projectId, e.getMessage()));
        }
    }

    public static List<IdAndNameDto> getCustomFieldsValues(Integer projectId, Integer customFieldId) {
        log.info("[AllureApi] getCustomFieldsValues - {}, {}", projectId, customFieldId);

        Map<String, Object> params = new HashMap<>();
        params.put("customFieldId", customFieldId);
        params.put("projectId", projectId);
        params.put("size", 10000);

        try {
            return given().spec(Specs.allureRequest)
                            .queryParams(params)
                            .get(cfSuggest)
                            .then()
                            .statusCode(200)
                            .extract()
                            .jsonPath()
                            .getList("content", IdAndNameDto.class);
        } catch (AssertionError e) {
            throw new RuntimeException(String.format("[CustomFieldsValues] Get data ERROR: %s", e.getMessage()));
        }
    }
}
