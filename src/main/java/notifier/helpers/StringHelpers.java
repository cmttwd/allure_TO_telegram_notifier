package notifier.helpers;

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

}
