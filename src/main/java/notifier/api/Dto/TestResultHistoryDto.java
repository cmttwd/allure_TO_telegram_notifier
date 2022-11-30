package notifier.api.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestResultHistoryDto {

    private int id;
    private IdAndNameDto launch;

    private long start;
    private long stop;

    private long duration;
    private String status;
    private String message;
    private String trace;

    private long createdDate;
    private long lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;

    private List<TestResultParameter> parameters;
    private List<EnvVarValueDto> environment;


    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TestResultParameter {

        private String name;
        private String value;
        private boolean hidden;
        private boolean excluded;

    }

}
