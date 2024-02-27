import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.*;
import java.awt.*;

public class JavaFXChartInSwing extends JFrame {

    public JavaFXChartInSwing() {
        initUI();
    }

    private void initUI() {
        setTitle("JavaFX Chart in Swing");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JFXPanel
        final JFXPanel fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);

        // Initialize JavaFX on the JavaFX Application Thread
        Platform.runLater(() -> {
            initFX(fxPanel);
        });
    }

    private void initFX(JFXPanel fxPanel) {
        // Create the X and Y axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X-Axis Label");
        yAxis.setLabel("Y-Axis Label");

        // Create the line chart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("JavaFX Line Chart");

        // Create a data series
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data Series");

        // Add data points to the series
        series.getData().add(new XYChart.Data<>(1, 5));
        series.getData().add(new XYChart.Data<>(2, 10));
        series.getData().add(new XYChart.Data<>(3, 15));
        series.getData().add(new XYChart.Data<>(4, 20));

        // Add the series to the chart
        lineChart.getData().add(series);

        // Create the scene
        Scene scene = new Scene(lineChart);

        // Set the scene to the JFXPanel
        fxPanel.setScene(scene);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JavaFXChartInSwing example = new JavaFXChartInSwing();
            example.setLocationRelativeTo(null);
            example.setVisible(true);
        });
    }
}