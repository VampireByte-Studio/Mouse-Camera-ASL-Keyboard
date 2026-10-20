package org.mouse.mousecameraapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class MainController {
    @FXML
    private ToggleButton camera, Landmarks;
    @FXML
    private Button launch, signsdwn, configdwn, train, test;
    private final Path signsFile = Path.of("signs.jsonl");
    private Process pyProcess;
    @FXML
    private void togglecamera(){

    }
    @FXML
    private void togglelandmarks(){

    }
    @FXML
    private void dwnsigns(){
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
    private void dwnconfig(){

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
