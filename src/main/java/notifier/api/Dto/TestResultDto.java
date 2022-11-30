package notifier.api.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestResultDto {

    private int id;
    private int projectId;
    private int launchId;
    private int testCaseId;

    private String name;
    private String fullName;

    private long start;
    private long stop;
    private long duration;
    private String status;
    private IdAndNameDto layer;
    private IdAndNameDto category;
    private String message;
    private String trace;
    private boolean external;
    private boolean manual;
    private String assignee;
    private String testedBy;
    private String hostId;
    private String threadId;
    private boolean flaky;
    private boolean muted;
    private boolean known;
    private boolean hidden;
    private IdAndNameDto retriedBy;
    private long createdDate;
    private long lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
    private List<IdAndNameDto> tags;
    private List<TestResultParameter> parameters;


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
