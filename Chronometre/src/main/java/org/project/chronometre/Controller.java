package org.project.chronometre;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class Controller implements Initializable {

    @FXML private Label timeLabel;
    @FXML private Spinner<Integer> hourSpinner;
    @FXML private Spinner<Integer> minuteSpinner;
    @FXML private Button startButton;
    @FXML private Button resetButton;

    private Timer timer;
    private int second = 0;
    private int minute = 0;
    private int hour = 0;
    private boolean isRunning = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));


        updateTimeDisplay();
    }

    @FXML
    void handleStartButton(ActionEvent event) {
        if (!isRunning) {
            startTimer();
            startButton.setText("Duraklat");
        } else {
            stopTimer();
            startButton.setText("Başlat");
        }
        isRunning = !isRunning;
    }

    @FXML
    void handleResetButton(ActionEvent event) {
        stopTimer();
        second = 0;
        minute = 0;
        hour = 0;
        updateTimeDisplay();
        startButton.setText("Başlat");
        isRunning = false;
    }

    private void startTimer() {
        timer = new Timer();
        int targetHour = hourSpinner.getValue();
        int targetMinute = minuteSpinner.getValue();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                second++;
                if (second >= 60) {
                    minute++;
                    second = 0;
                }
                if (minute >= 60) {
                    hour++;
                    minute = 0;
                }

                updateTimeDisplay();

                // Hedef zamana ulaşıldığında
                if (hour == targetHour && minute == targetMinute) {
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.INFORMATION, "Süre doldu!").show();
                        stopTimer();
                        startButton.setText("Başlat");
                        isRunning = false;
                    });
                }
            }
        }, 0, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void updateTimeDisplay() {
        Platform.runLater(() ->
                timeLabel.setText(String.format("%02d:%02d:%02d", hour, minute, second))
        );
    }
}