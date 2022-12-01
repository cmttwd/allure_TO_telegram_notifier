package notifier.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class LaunchInfo {

    private String launchName;
    private String launchId;

    private Map<String, String> env;
    private List<String> tags;

    private String startTime;
    private String duration;

    private Map<Status, Long> statuses;
    private List<StringIntPair> defects;
    private List<StatusData> epicsStatus;


    @Getter
    public static class StringIntPair {
        public String string;
        public Integer integer;

        public StringIntPair(String string, Integer integer) {
            this.string = string;
            this.integer = integer;
        }
    }
}
