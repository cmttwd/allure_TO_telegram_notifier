package notifier.api.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestResultRowDto {

    private int id;
    private int testCaseId;
    private String name;
    private String status;

    private long duration;
    private String assignee;


}
