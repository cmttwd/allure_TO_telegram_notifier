package notifier.api.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageTestResultDto {

    private int totalPages;
    private int totalElements;
    private boolean first;
    private SortDto sort;
    private int numberOfElements;
    private int size;
    private int number;
    private boolean empty;
    private List<TestResultDto> content;

}
