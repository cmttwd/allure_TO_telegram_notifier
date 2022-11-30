package notifier.api.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaunchDetailsDto {

    private int id;
    private String name;
    private boolean closed;
    private int projectId;
    private List<IdAndNameDto> tags;
    private String createdBy;
    private String lastModifiedBy;
    private long createdDate;
    private long lastModifiedDate;


}
