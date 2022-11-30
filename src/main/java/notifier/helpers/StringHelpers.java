package notifier.helpers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringHelpers {

    public static String getPercent(long total, long part) {
        return String.format("%.2f", part * 100f / total) + "%";
    }

    public static int parseId(String text) {
        try {
            int id = Integer.parseInt(text);
            if (id < 1 || id > 200_000) return -1;
            return id;

        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static List<String> splitStringByCharsCount(String text, int count) {
        String[] strings = text.split("\n");

        List<String> list = new ArrayList<>();

        Arrays.stream(strings).forEach(s -> {
            if (list.isEmpty()) {
                list.add(s);
            } else if (list.get(list.size() - 1).length() + s.length() < count) {
                list.set(list.size() - 1, list.get(list.size() - 1) + "\n" +s);
            } else list.add(s);
        });

        return list;
    }

}
