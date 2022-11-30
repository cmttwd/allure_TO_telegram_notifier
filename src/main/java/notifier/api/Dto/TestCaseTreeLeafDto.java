package notifier.api.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseTreeLeafDto {

    private int id;
    private String name;

    private boolean automated;
    private boolean external;

    private String statusName;
    private String statusColor;
    private String layerName;

    private long createdDate;

}
