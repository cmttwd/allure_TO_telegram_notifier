package notifier.api.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageTestCaseTreeLeafDto {

    private int totalPages;
    private int totalElements;
    private int size;
    private int number;
    private SortDto sort;
    private int numberOfElements;
    private boolean first;
    private boolean last;
    private boolean empty;
    private List<TestCaseTreeLeafDto> content;

}
