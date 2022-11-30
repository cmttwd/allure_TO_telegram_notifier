package notifier.api.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestPlanAssignDto {


    private String username;
    private TestPlanTreeSelectionDto selection;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TestPlanTreeSelectionDto {
       private boolean inverted = false;
       private boolean manual = true;

       private int testPlanId;

       private List<Integer> groupsInclude;
       private List<Integer> groupsExclude;

       private List<Integer> leafsInclude;
       private List<Integer> leafsExclude;

    }

    public TestPlanAssignDto(String username, List<Integer> leafsInclude) {
        this.username = username;
        this.selection = new TestPlanTreeSelectionDto();
        this.selection.setLeafsInclude(leafsInclude);
    }
}
