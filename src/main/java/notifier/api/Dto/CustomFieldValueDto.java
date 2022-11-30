package notifier.api.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomFieldValueDto {

    private int id;
    private String name;
    private IdAndNameDto customField;

}
