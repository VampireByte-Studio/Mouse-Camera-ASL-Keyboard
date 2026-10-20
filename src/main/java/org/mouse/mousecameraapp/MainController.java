package org.mouse.mousecameraapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mouse.mousecameraapp.functions.ConfigWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class MainController {
    @FXML
    private Label starting;
    @FXML
    private ToggleButton camera, Landmarks;
    @FXML
    private Button launch, signsdwn, configdwn, train, test;
    private final Path signsFile = Path.of("signs.jsonl");
    private Process pyProcess;
    private final ConfigWriter config = new ConfigWriter();
    @FXML
    private void togglecamera(){
        config.get().camera = camera.isSelected();
        config.save();
    }
    @FXML
    private void togglelandmarks(){
        config.get().landmarks = Landmarks.isSelected();
        config.save();

    }
    @FXML
    private void dwnsigns(){
        System.out.println("DWN SIGNS PRESSED");
        if (!Files.exists(signsFile)) {
            System.out.println("NO FILE FOUND");
            return;
        }
    FileChooser c = new FileChooser();
    c.setTitle("Downloading..");
    c.setInitialFileName("signs.jsonl");
    c.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON LINES", "*.jsonl"));
        Stage stage = (Stage) signsdwn.getScene().getWindow();
        File dest = c.showSaveDialog(stage);   // null if cancelled

        if (dest != null) {
            try {
                Files.copy(signsFile, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();  // or show an Alert
            }
        }

    }
    @FXML
    private void dwnconfig() {
        config.save();   // make sure config.json has the latest toggle states

        Path configFile = Path.of(System.getProperty("user.dir"), "config.json");

        FileChooser c = new FileChooser();
        c.setTitle("Save config");
        c.setInitialFileName("config.json");
        c.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

        File dest = c.showSaveDialog((Stage) configdwn.getScene().getWindow());   // null if cancelled
        if (dest != null) {
            try {
                Files.copy(configFile, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void train(){
        if(pyProcess != null && pyProcess.isAlive()) return;
        Path python = Path.of("venv", "Scripts", "python.exe").toAbsolutePath();
        Path script = Path.of("src", "main", "java", "org", "mouse", "mousecameraapp",
                "functions", "scripts", "Hand_Recorder.py").toAbsolutePath();

        Thread t = new Thread(() -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        python.toString(), script.toString(), signsFile.toAbsolutePath().toString()); // run from the script's folder
                pb.redirectErrorStream(true);                // merge errors into normal output
                pyProcess = pb.start();

                try (BufferedReader r = new BufferedReader(
                        new InputStreamReader(pyProcess.getInputStream()))) {
                    String line;
                    while ((line = r.readLine()) != null) {
                        final String out = line;
                        Platform.runLater(() -> System.out.println("PY: " + out)); // or update a Label
                    }
                }
                int code = pyProcess.waitFor();
                Platform.runLater(() -> System.out.println("Python exited with " + code));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);   // won't keep the app alive after the window closes
        t.start();
    }
    @FXML
    private void test(){
    if(pyProcess != null && pyProcess.isAlive()) return;
        Path python = Path.of("venv", "Scripts", "python.exe").toAbsolutePath();
        Path script = Path.of("src", "main", "java", "org", "mouse", "mousecameraapp",
                "functions", "scripts", "Hand_Tester.py").toAbsolutePath();

        Thread t = new Thread(() -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        python.toString(), script.toString(), signsFile.toAbsolutePath().toString()); // run from the script's folder
                pb.redirectErrorStream(true);                // merge errors into normal output
                pyProcess = pb.start();

                try (BufferedReader r = new BufferedReader(
                        new InputStreamReader(pyProcess.getInputStream()))) {
                    String line;
                    while ((line = r.readLine()) != null) {
                        final String out = line;
                        Platform.runLater(() -> System.out.println("PY: " + out)); // or update a Label
                    }
                }
                int code = pyProcess.waitFor();
                Platform.runLater(() -> System.out.println("Python exited with " + code));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);   // won't keep the app alive after the window closes
        t.start();

    }
    @FXML
    private void launch(){
        starting.setText("Starting...");
        if(pyProcess != null && pyProcess.isAlive()) return;
        Path python = Path.of("venv", "Scripts", "python.exe").toAbsolutePath();
        Path script = Path.of("src", "main", "java", "org", "mouse", "mousecameraapp",
                "functions", "scripts", "Hand_Mouse.py").toAbsolutePath();

        Thread t = new Thread(() -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        python.toString(), script.toString(), signsFile.toAbsolutePath().toString()); // run from the script's folder
                pb.redirectErrorStream(true);                // merge errors into normal output
                pyProcess = pb.start();

                try (BufferedReader r = new BufferedReader(
                        new InputStreamReader(pyProcess.getInputStream()))) {
                    String line;
                    while ((line = r.readLine()) != null) {
                        final String out = line;
                        Platform.runLater(() -> System.out.println("PY: " + out)); // or update a Label
                    }
                }
                int code = pyProcess.waitFor();
                Platform.runLater(() -> System.out.println("Python exited with " + code));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);   // won't keep the app alive after the window closes
        t.start();
    }



}
