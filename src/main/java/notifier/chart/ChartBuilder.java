package notifier.chart;

import notifier.model.LaunchInfo;
import notifier.model.Status;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.knowm.xchart.PieSeries.PieSeriesRenderStyle.Donut;
import static org.knowm.xchart.style.Styler.LegendLayout.Vertical;
import static org.knowm.xchart.style.Styler.LegendPosition.OutsideE;

public class ChartBuilder {

    private static final Color BACKGROUND_COLOR = new Color(24, 37, 51);
    private static final Color FONT_COLOR = Color.WHITE;

    public static byte[] getChartImage(LaunchInfo launchInfo) {
        BufferedImage chartImage = BitmapEncoder.getBufferedImage(createChart(launchInfo));

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(chartImage, "png", os);
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Unable to create image with chart", e);
        }
    }

    private static PieChart createChart(LaunchInfo launchInfo) {
        PieChart chart = new PieChartBuilder()
                .title(launchInfo.getLaunchName())
                .width(500)
                .height(250)
                .build();

        chart.getStyler()
                //Легенда
                .setLegendVisible(true)
                .setLegendPosition(OutsideE)
                .setLegendPadding(8)
                .setLegendLayout(Vertical)
                .setLegendBorderColor(BACKGROUND_COLOR)
                .setLegendBackgroundColor(BACKGROUND_COLOR);

        // Диграмма
        chart.getStyler()
                .setDefaultSeriesRenderStyle(Donut)
                .setCircular(true)
                .setSumVisible(true)
                .setSumFontSize(30f)
                .setDonutThickness(.6)
                .setPlotBorderColor(BACKGROUND_COLOR)
                .setPlotBackgroundColor(BACKGROUND_COLOR)

                // Общее
                .setChartPadding(0)
                .setPlotContentSize(0.9)
                .setChartBackgroundColor(BACKGROUND_COLOR)
                .setChartFontColor(FONT_COLOR)
                .setDecimalPattern("#");


        List<Color> colors = addSeriesTo(chart, launchInfo.getStatuses());

        chart.getStyler().setSeriesColors(colors.toArray(new Color[0]));

        return chart;
    }


    private static List<Color> addSeriesTo(PieChart chart, Map<Status, Long> data) {

        List<Color> colors = new ArrayList<>();

        data.forEach((status, integer) -> {
            if (integer != null && integer != 0) {
                chart.addSeries(String.format("%d - %s", integer,
                        status.getLegend()), integer);
                colors.add(status.getColor());
            }
        });

        return colors;
    }

}
