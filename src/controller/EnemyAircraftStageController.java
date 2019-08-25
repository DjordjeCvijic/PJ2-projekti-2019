package controller;


import applications.LoggerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



public class EnemyAircraftStageController implements Initializable{
    @FXML
    private TableView<EnemyMillitaryAircraft> tableView = new TableView<>();
    @FXML
    private TableColumn<EnemyMillitaryAircraft,String > idColumn=new TableColumn<>();
    @FXML
    private TableColumn<EnemyMillitaryAircraft,String > markColumn=new TableColumn<>();
    @FXML
    private TableColumn<EnemyMillitaryAircraft,String > xColumn=new TableColumn<>();
    @FXML
    private TableColumn<EnemyMillitaryAircraft,String > yColumn=new TableColumn<>();
    @FXML
    private TableColumn<EnemyMillitaryAircraft,Date > timeColumn=new TableColumn<>();



    public  ObservableList<EnemyMillitaryAircraft> enemis = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File f=new File("src" + File.separator + "events");
        String[] files=f.list();
        try {
            for(int i=0;i<files.length;i++) {
                BufferedReader out = new BufferedReader(new FileReader(f + File.separator + files[i]));
                String s = out.readLine();
                String[] info=s.split("#");
                int index=files[i].indexOf(".");
                String time=files[i].substring(0,index);
                enemis.add(new EnemyMillitaryAircraft(info[0],info[1],info[2],info[3],time));
                out.close();
            }

        } catch (Exception e){
            LoggerService logger=LoggerService.getInstance();
            logger.log(Level.WARNING,e);
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));
        xColumn.setCellValueFactory(new PropertyValueFactory<>("xPosition"));
        yColumn.setCellValueFactory(new PropertyValueFactory<>("yPosition"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfEntry"));
        tableView.setItems(enemis);
    }


    public class EnemyMillitaryAircraft{
        private String id;
        private String mark;
        private String xPosition;
        private String yPosition;
        private Date timeOfEntry;

        private EnemyMillitaryAircraft(String id,String mark,String xPosition,String yPosition,String time){
            this.id=id;
            this.mark=mark;
            this.xPosition=xPosition;
            this.yPosition=yPosition;
            timeOfEntry=new Date(Long.parseLong(time));


        }

        public String getId() {
            return id;
        }


        public String getMark() {
            return mark;
        }



        public String getXPosition() {
            return xPosition;
        }

        public String getYPosition() {
            return yPosition;
        }

        public Date getTimeOfEntry() {
            return timeOfEntry;
        }
        public String toString(){
            return id+" "+mark;
        }
    }
}
