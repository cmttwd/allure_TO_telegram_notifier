package notifier.api.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseOverviewDto {

    private int id;
    private int projectId;
    private String name;
    private String fullName;
    private boolean deleted;
    private boolean editable;
    private boolean automated;
    private boolean external;
    private String style;

    private IdAndNameDto layer;
    private List<IdAndNameDto> tags;
    private IdAndNameDto status;

    private List<CustomFieldValueDto> customFields;
    private List<MemberDto> members;
    private List<TestCaseParameterDto> parameters;


    private long createdDate;
    private long lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TestCaseParameterDto {

        private String name;
    }

}
