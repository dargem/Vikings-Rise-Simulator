package test.gui;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class PlotlyHistogram {
    private static JFrame frame;
    private static JFXPanel jfxPanel;

    public static void showHistogram(List<Integer> values) {
        SwingUtilities.invokeLater(() -> {
            if (frame == null) {
                frame = new JFrame("Troop Histogram");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(800, 600);
                jfxPanel = new JFXPanel();
                frame.add(jfxPanel);

                // Prevent JavaFX runtime from exiting on window close
                Platform.setImplicitExit(false);
            }

            frame.setVisible(true);

            Platform.runLater(() -> {
                WebView webView = new WebView();
                Scene scene = new Scene(webView);

                String jsArray = values.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));

                String html = """
                    <html>
                    <head>
                        <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
                    </head>
                    <body>
                        <div id="chart" style="width:100%%;height:100%%;"></div>
                        <script>
                            var data = [{
                                x: [%s],
                                type: 'histogram',
                                marker: {color: 'rgba(100, 149, 237, 0.7)'}
                            }];
                            var layout = {
                                title: 'Troop Survival Distribution',
                                xaxis: { title: 'Troops Alive After Fight' },
                                yaxis: { title: 'Frequency' },
                                bargap: 0.05
                            };
                            Plotly.newPlot('chart', data, layout);
                        </script>
                    </body>
                    </html>
                    """.formatted(jsArray);

                webView.getEngine().loadContent(html);
                jfxPanel.setScene(scene);
            });
        });
    }
}
