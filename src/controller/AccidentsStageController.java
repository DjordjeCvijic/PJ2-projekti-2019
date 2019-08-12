package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Crash;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AccidentsStageController implements Initializable{
    @FXML
    private TableView<AircraftsInCrush> tableView = new TableView<>();
    @FXML
    private TableColumn<AircraftsInCrush,String > firstAircraftColumn=new TableColumn<>();
    @FXML
    private TableColumn<AircraftsInCrush,String > secondAircraftColumn=new TableColumn<>();
    @FXML
    private TableColumn<AircraftsInCrush,String > xColumn=new TableColumn<>();
    @FXML
    private TableColumn<AircraftsInCrush,String > yColumn=new TableColumn<>();
    @FXML
    private TableColumn<AircraftsInCrush,Date > timeColumn=new TableColumn<>();

    public static ObservableList<AircraftsInCrush> aircraftsInCrushes = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources){
        File f=new File("src" + File.separator + "alert");
        String[] files=f.list();
        try {
            for(int i=0;i<files.length;i++) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f + File.separator + files[i]));

                Crash crush = (Crash) ois.readObject();
                String []info=crush.getDescription().split(" ");
                String []info1=crush.getPositionOfCrash().split(" ");

                aircraftsInCrushes.add(new AircraftsInCrush(info[0],info[2],info1[0],info1[1],crush.getTime()));
                ois.close();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        firstAircraftColumn.setCellValueFactory(new PropertyValueFactory<>("firstAircraft"));
        secondAircraftColumn.setCellValueFactory(new PropertyValueFactory<>("secondAircaft"));
        xColumn.setCellValueFactory(new PropertyValueFactory<>("xPosition"));
        yColumn.setCellValueFactory(new PropertyValueFactory<>("yPosition"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfCrush"));
        tableView.setItems(aircraftsInCrushes);


    }

    public class AircraftsInCrush {
        private String firstAircraft;
        private String secondAircaft;
        private String xPosition;
        private String yPosition;
        private Date timeOfCrush;

        private AircraftsInCrush(String firstAircraft, String secondAircaft, String xPosition, String yPosition, String time) {
            this.firstAircraft = firstAircraft;
            this.secondAircaft = secondAircaft;
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            timeOfCrush= new Date(Long.parseLong(time));


        }

        public String getFirstAircraft() {
            return firstAircraft;
        }


        public String getSecondAircaft() {
            return secondAircaft;
        }


        public String getXPosition() {
            return xPosition;
        }

        public String getYPosition() {
            return yPosition;
        }

        public Date getTimeOfCrush() {
            return timeOfCrush;
        }

        //public String toString(){
        //return id+" "+mark;
    }
}

