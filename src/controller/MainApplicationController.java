package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

import java.util.ResourceBundle;

import model.Airspace;
import model.JavaDirectoryChangeListener;
import model.Radar;
import model.Simulator;


public class MainApplicationController extends Thread implements Initializable {

    @FXML
    private AnchorPane aPanel;

    @FXML
    private Label noFlightZoneLabel;
    @FXML
    private Label infoLabel = new Label();
    private GridPane table;

    private File file;
    private int x;
    private int y;
    private String[][] map;
    private long timeStamp;
    private static String infoText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        file = Radar.f;
        //file=new File("src" + File.separator + "resources" + File.separator + "map.txt");
        JavaDirectoryChangeListener listener = new JavaDirectoryChangeListener();
        listener.start();
        start();


    }

    public static void setInfoText(String s) {
        infoText = s;
    }


    public void run() {


        try {

            //sleep(500);

            BufferedReader in = new BufferedReader(new FileReader(file));
            String[] tmp = in.readLine().split("#");
            x = Integer.parseInt(tmp[0]);
            y = Integer.parseInt(tmp[1]);
            //System.out.println("u kontoleru velicina table"+x+" "+y);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    aPanel.getChildren().add(setTable("   ", x, y));
                }
            });


            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        timeStamp = file.lastModified();
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
                if (isFileUpdated(file)) {


                    synchronized (file) {

                        BufferedReader in = new BufferedReader(new FileReader(file));
                        in.readLine();
                        String tmp[];
                        map = new String[x][y];//punjenjje pame provjeriti
                        for (int i = 0; i < x; i++) {
                            for (int j = 0; j < y; j++) {
                                tmp = in.readLine().split("#");
                                map[i][j] = tmp[1];//ispisati mapu

                            }
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                print();
                            }
                        });


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public void print() {
//ovo provjeriti
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {


                TextField text1 = new TextField(map[i][j]);
                Node node = getNode(table, j, i);
                table.getChildren().remove(node);
                text1.setPrefWidth(29);
                text1.setPrefHeight(29);
                if (Simulator.isThisEnemy(Airspace.getIdInThisPositionStatic(i, j))) {
                    text1.setStyle("-fx-text-inner-color: red;");

                }
                text1.setFont(new Font(10));
                table.add(text1, j, i);//prvo colona pa red
                //GridPane.setValignment(text, VPos.CENTER);
                //GridPane.setHalignment(text, HPos.CENTER);
            }
        }


    }


    public GridPane setTable(String str, int x, int y) {
        table = new GridPane();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {


                Text text = new Text(str);
                TextField text1 = new TextField(str);
                text1.setPrefWidth(29);
                text1.setPrefHeight(29);

                table.add(text1, j, i);
                GridPane.setValignment(text, VPos.CENTER);
                GridPane.setHalignment(text, HPos.CENTER);
            }
        }
        //table.setAlignment(Pos.CENTER);

        //table.setGridLinesVisible(true);
        //table.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        //  table.setMaxSize(4,4);
        table.setLayoutX(20);
        table.setLayoutY(20);
        //table.setPrefSize(505, 505);
        // table.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        return table;

    }

    public void noFlightZoneActivateBtnClick(ActionEvent actionEvent) {


        System.out.println("STOP");
        noFlightZoneLabel.setText("No-Flight Zone");
        Simulator.noFlightZoneActivate();
    }

    public void noFlightZoneDeactivateBtnClick(ActionEvent actionEvent) {
        System.out.println("STart");
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }


    public static Node getNode(GridPane table, int columnNum, int rowNum) {//za brisanje
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
