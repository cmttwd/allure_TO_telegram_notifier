package notifier.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class StatusData {

    private Map<Status, Long> statuses;
    private String title;

}
