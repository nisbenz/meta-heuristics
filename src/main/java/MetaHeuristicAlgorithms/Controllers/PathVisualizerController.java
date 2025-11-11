package MetaHeuristicAlgorithms.Controllers;

import MetaHeuristicAlgorithms.Models.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class PathVisualizerController {
    @FXML private Canvas mapCanvas;
    @FXML private ComboBox<String> algorithmComboBox;
    @FXML private Button runButton;
    @FXML private Label distanceLabel;
    @FXML private TextArea routeTextArea;
    @FXML private ProgressBar progressBar;

    private Positions positions;
    private MetaheuristicSearches currentSearch;
    private double minX, maxX, minY, maxY;
    private static final int PADDING = 50;

    @FXML
    public void initialize() {
        positions = new Positions();

        // Populate algorithm dropdown
        algorithmComboBox.getItems().addAll(
                "Random Search",
                "Hill Climbing",
                "Local Search (2-opt)",
                "Simulated Annealing"
        );
        algorithmComboBox.setValue("Simulated Annealing");

        // Calculate bounds for map
        calculateBounds();

        // Draw initial map with cities only
        drawMap(null);

        // Set up button action
        runButton.setOnAction(e -> runAlgorithm());
    }

    private void calculateBounds() {
        ArrayList<City> cities = positions.getPositions();
        minX = cities.stream().mapToDouble(City::x_km).min().orElse(0);
        maxX = cities.stream().mapToDouble(City::x_km).max().orElse(1000);
        minY = cities.stream().mapToDouble(City::y_km).min().orElse(0);
        maxY = cities.stream().mapToDouble(City::y_km).max().orElse(5000);
    }

    private void runAlgorithm() {
        runButton.setDisable(true);
        progressBar.setVisible(true);
        routeTextArea.clear();

        // Run algorithm in background thread
        new Thread(() -> {
            String algorithm = algorithmComboBox.getValue();

            switch (algorithm) {
                case "Random Search":
                    currentSearch = new RandomParkour();
                    ((RandomParkour) currentSearch).RandomSolution();
                    break;
                case "Hill Climbing":
                    currentSearch = new HillClimbParkour();
                    ((HillClimbParkour) currentSearch).HillClimbSolution();
                    break;
                case "Local Search (2-opt)":
                    currentSearch = new LocalParkour();
                    ((LocalParkour) currentSearch).localSolution();
                    break;
                case "Simulated Annealing":
                    currentSearch = new SimulatedAnnealingParkour();
                    ((SimulatedAnnealingParkour) currentSearch).SimulatedAnnealingSolution();
                    break;
            }

            // Update UI on JavaFX thread
            javafx.application.Platform.runLater(() -> {
                drawMap(currentSearch.parkour);
                updateRouteInfo();
                progressBar.setVisible(false);
                runButton.setDisable(false);
            });
        }).start();
    }

    private void drawMap(Parkour parkour) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        double width = mapCanvas.getWidth();
        double height = mapCanvas.getHeight();

        // Clear canvas
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        // Draw border
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(2);
        gc.strokeRect(10, 10, width - 20, height - 20);

        // Draw title
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 16));
        gc.fillText("Algerian Cities TSP Visualization", 20, 30);

        ArrayList<City> cities = positions.getPositions();

        // Draw route if available
        if (parkour != null && parkour.getParkour().size() > 1) {
            gc.setStroke(Color.BLUE);
            gc.setLineWidth(2);

            ArrayList<City> route = parkour.getParkour();
            for (int i = 0; i < route.size() - 1; i++) {
                City c1 = route.get(i);
                City c2 = route.get(i + 1);

                double x1 = scaleX(c1.x_km(), width);
                double y1 = scaleY(c1.y_km(), height);
                double x2 = scaleX(c2.x_km(), width);
                double y2 = scaleY(c2.y_km(), height);

                gc.strokeLine(x1, y1, x2, y2);

                // Draw arrow
                drawArrow(gc, x1, y1, x2, y2);
            }
        }

        // Draw cities
        for (City city : cities) {
            double x = scaleX(city.x_km(), width);
            double y = scaleY(city.y_km(), height);

            // Draw city point
            gc.setFill(Color.RED);
            gc.fillOval(x - 5, y - 5, 10, 10);

            // Draw city name
            gc.setFill(Color.BLACK);
            gc.setFont(new Font("Arial", 10));
            gc.fillText(city.name(), x + 8, y + 5);
        }

        // Highlight start city
        City start = cities.get(0);
        double startX = scaleX(start.x_km(), width);
        double startY = scaleY(start.y_km(), height);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.strokeOval(startX - 8, startY - 8, 16, 16);
    }

    private void drawArrow(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        double len = Math.sqrt(dx * dx + dy * dy);

        // Draw arrow at midpoint
        double midX = (x1 + x2) / 2;
        double midY = (y1 + y2) / 2;

        double arrowLength = 8;
        double arrowWidth = 5;

        double x3 = midX - arrowLength * Math.cos(angle - Math.PI / 6);
        double y3 = midY - arrowLength * Math.sin(angle - Math.PI / 6);
        double x4 = midX - arrowLength * Math.cos(angle + Math.PI / 6);
        double y4 = midY - arrowLength * Math.sin(angle + Math.PI / 6);

        gc.setFill(Color.BLUE);
        gc.fillPolygon(new double[]{midX, x3, x4}, new double[]{midY, y3, y4}, 3);
    }

    private double scaleX(double x, double width) {
        return PADDING + (x - minX) / (maxX - minX) * (width - 2 * PADDING);
    }

    private double scaleY(double y, double height) {
        return height - PADDING - (y - minY) / (maxY - minY) * (height - 2 * PADDING);
    }

    private void updateRouteInfo() {
        if (currentSearch != null && currentSearch.parkour != null) {
            double distance = Evaluation.TotalDistance(currentSearch.parkour);
            distanceLabel.setText(String.format("Total Distance: %.2f km", distance));

            StringBuilder routeText = new StringBuilder();
            routeText.append("Route Order:\n");
            routeText.append("=" .repeat(40)).append("\n");

            ArrayList<City> route = currentSearch.parkour.getParkour();
            for (int i = 0; i < route.size(); i++) {
                City city = route.get(i);
                routeText.append(String.format("%2d. %s\n", i + 1, city.name()));

                if (i < route.size() - 1) {
                    double dist = Evaluation.Distance(city, route.get(i + 1));
                    routeText.append(String.format("    ↓ %.2f km\n", dist));
                }
            }

            routeTextArea.setText(routeText.toString());
        }
    }
}