package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import applications.*;

import java.io.*;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.ResourceBundle;
import java.util.logging.Level;

import model.*;
import model.aircrafts.Aircraft;


public class MainApplicationController extends Thread implements Initializable {


    @FXML
    private Label noFlightZoneLabel;
    @FXML
    private Label infoLabel = new Label();
    private GridPane table;
    @FXML
    private ScrollPane scrollPane = new ScrollPane();
    private File fileMap;
    private int x;
    private int y;
    private String[][] map;
    private long timeStamp;
    private static String infoText;
    private static File fileAlert;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fileMap = Radar.fileMap;
        fileAlert = Radar.fileAlert;
        deleteFolders(fileAlert);
        deleteFolders(new File("src" + File.separator + "events"));
        try {
            sleep(500);

            BufferedReader in = new BufferedReader(new FileReader(fileMap));
            String[] tmp = in.readLine().split("#");
            x = Integer.parseInt(tmp[0]);
            y = Integer.parseInt(tmp[1]);


            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setTable("   ", x, y);
                }
            });

            in.close();

        } catch (Exception e) {
            LoggerService logger = LoggerService.getInstance();
            logger.log(Level.WARNING, e);
        }
        JavaDirectoryChangeListener listener = new JavaDirectoryChangeListener(FileSystems.getDefault().getPath("src" + File.separator + "events"), "events");
        listener.start();
        JavaDirectoryChangeListener listener1 = new JavaDirectoryChangeListener(FileSystems.getDefault().getPath("src" + File.separator + "alert"), "alert");
        listener1.start();
        start();
    }

    private void deleteFolders(File file) {
        String[] entries = file.list();
        for (String s : entries) {
            File currentFile = new File(file.getPath(), s);
            currentFile.delete();
        }
    }

    public static void newCrash() {
        synchronized (fileAlert) {
            try {
                String[] files = fileAlert.list();
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileAlert + File.separator + files[files.length - 1]));
                Crash crash = (Crash) ois.readObject();
                warning(crash.toString());

            } catch (Exception e) {
                LoggerService logger = LoggerService.getInstance();
                logger.log(Level.WARNING, e);
            }
        }
    }

    public static void warning(String s) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Accident information");
                alert.setHeaderText(null);
                alert.setContentText(s);
                alert.show();
            }
        });


    }

    public static void setInfoText(String s) {
        infoText = s;
    }

    public void run() {


        timeStamp = fileMap.lastModified();
        while (true) {
            if (!Airspace.isIsEnemyInSky()) {
                infoText = " ";
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    infoLabel.setText(infoText);
                }
            });

            try {
                if (isFileUpdated(fileMap)) {
                    synchronized (fileMap) {

                        BufferedReader in = new BufferedReader(new FileReader(fileMap));
                        in.readLine();
                        String tmp[];
                        map = new String[x][y];//punjenjje mape provjeriti
                        for (int i = 0; i < x; i++) {
                            for (int j = 0; j < y; j++) {
                                tmp = in.readLine().split("#");
                                map[i][j] = tmp[1];

                            }
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                print();
                            }
                        });
                        timeStamp = fileMap.lastModified();

                    }
                }
            } catch (Exception e) {
                LoggerService logger = LoggerService.getInstance();
                logger.log(Level.WARNING, e);
            }

        }


    }

    private void print() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {


                TextField text1 = new TextField(map[i][j]);
                Node node = getNode(table, j, i);
                table.getChildren().remove(node);
                text1.setPrefWidth(33);
                text1.setPrefHeight(31);
                if (Simulator.isThisEnemy(Airspace.getIdInThisPositionStatic(i, j))) {
                    text1.setStyle("-fx-text-inner-color: red;");
                }
                Aircraft a = (Aircraft) Simulator.aircrafts.get(Airspace.getIdInThisPositionStatic(i, j));
                if (a != null && a.isInAttack()) {
                    text1.setStyle("-fx-text-inner-color: blue;");
                }
                text1.setFont(new Font(9));
                table.add(text1, j, i);//prvo colona pa red

            }
        }


    }


    private void setTable(String str, int x, int y) {
        table = new GridPane();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Text text = new Text(str);
                TextField text1 = new TextField(str);
                text1.setPrefWidth(33);
                text1.setPrefHeight(31);

                table.add(text1, j, i);
                GridPane.setValignment(text, VPos.CENTER);
                GridPane.setHalignment(text, HPos.CENTER);
            }
        }

        table.setLayoutX(20);
        table.setLayoutY(20);
        scrollPane.setContent(table);


    }

    public void noFlightZoneActivateBtnClick(ActionEvent actionEvent) {

        noFlightZoneLabel.setText("No-Flight Zone");
        Simulator.noFlightZoneActivate();
    }

    public void noFlightZoneDeactivateBtnClick(ActionEvent actionEvent) {
        noFlightZoneLabel.setText("");
        Simulator.noFlightZoneDeactivate();
    }

    public void accidentsDisplayBtnClick(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(".." + File.separator + "view" + File.separator + "accidentsStage.fxml"));
            stage.setTitle("Accidents in Air-space");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            LoggerService logger = LoggerService.getInstance();
            logger.log(Level.WARNING, e);
        }
    }

    public void enemyAircraftDisplayBtnClick(ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(".." + File.separator + "view" + File.separator + "enemyAircraftStage.fxml"));
            stage.setTitle("Enemy aircraft in Air-space");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            LoggerService logger = LoggerService.getInstance();
            logger.log(Level.WARNING, e);
        }
    }


    private static Node getNode(GridPane table, int columnNum, int rowNum) {//za brisanje
        for (Node node : table.getChildren()) {
            if (GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == columnNum && GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == rowNum) {
                return node;
            }
        }
        return null;
    }

    private boolean isFileUpdated(File file) {

        long time;
        time = file.lastModified();

        if (timeStamp != time) {
            timeStamp = time;
            return true;
        }


        return false;
    }
}
