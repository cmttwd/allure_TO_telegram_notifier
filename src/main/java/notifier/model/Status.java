package notifier.model;

import lombok.Getter;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

@Getter
public enum Status {

    PASSED("passed", new Color(148, 202, 102)),
    FAILED("failed", new Color(255, 87, 68)),
    BROKEN("broken", new Color(255, 206, 87)),
    SKIPPED("skipped", new Color(170, 170, 170)),
    UNKNOWN("unknown", new Color(216, 97, 190)),
    IN_PROGRESS(null, new Color(100, 150, 255));

    String allureValue;
    String legend;
    Color color;

    Status(String allureValue, Color color) {
        this.allureValue = allureValue;
        this.legend = this.name();
        this.color = color;
    }

    public static Status getStatus(String value) {
        return Arrays.stream(Status.values())
                .filter(status -> Objects.equals(value, status.allureValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ошибка при сопоставлении статусов. Статус '" + value + "' не найден."));
    }
}
