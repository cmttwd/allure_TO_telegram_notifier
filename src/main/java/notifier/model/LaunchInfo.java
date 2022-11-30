package notifier.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class LaunchInfo {

    private String launchName;
    private String env;
    private String duration;
    private Map<Status, Long> statuses;
    private String launchId;
    private List<StatusData> epicsStatus;

}
