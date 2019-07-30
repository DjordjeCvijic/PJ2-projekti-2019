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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import model.Radar;



public class MainApplicationController extends Thread implements Initializable  {

    @FXML
    private AnchorPane aPanel;
    @FXML
    private Label infoLabel;
    private GridPane table;

    private File file;
    private int x;
    private int y;
    private String[][]map;
    private long timeStamp;


        @Override
    public void initialize(URL location, ResourceBundle resources) {


            file=Radar.f;
           // start();


    }


    public void run(){



        try{

            sleep(1000);

            BufferedReader in=new BufferedReader(new FileReader(file));
            String []tmp=in.readLine().split("#");
            x=Integer.parseInt(tmp[0]);
            y=Integer.parseInt(tmp[1]);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    aPanel.getChildren().add(setTable("   ",x,y));
                }
            });




            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        this.timeStamp=file.lastModified();
            while(true){
                try{
                    if(isFileUpdated(file)) {

                        synchronized (file) {
                            BufferedReader in = new BufferedReader(new FileReader(file));
                            in.readLine();
                            String tmp[];
                            map = new String[x][y];
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


                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }


    }
    public void print() {

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

                Text text = new Text(map[i][j]);
                Node node = getNode(table, i, j);
                table.getChildren().remove(node);
                table.add(text, i, j);
                GridPane.setValignment(text, VPos.CENTER);
                GridPane.setHalignment(text, HPos.CENTER);
            }
        }
    }




    public  GridPane setTable(String str,int rows,int columns) {
         table = new GridPane();

        for(int j=0;j<columns;j++) {
            for (int i = 0; i < rows; i++) {


                Text text = new Text(str);
                table.add(text, j, i);
                GridPane.setValignment(text, VPos.CENTER);
                GridPane.setHalignment(text, HPos.CENTER);
            }
        }
        table.setAlignment(Pos.CENTER);



        table.setGridLinesVisible(true);
        //table.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        //  table.setMaxSize(4,4);
        table.setLayoutX(20);
        table.setLayoutY(20);
        return table;

    }

    public void noFlightZoneActivateBtnClick(ActionEvent actionEvent) {

        System.out.println("STOP");
        infoLabel.setText("strana letjelica");
    }

    public void noFlightZoneDeactivateBtnClick(ActionEvent actionEvent) {
        System.out.println("STart");
    }

    public void accidentsDisplayBtnClick(ActionEvent actionEvent) {
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource(".." + File.separator + "view" + File.separator + "accidentsStage.fxml"));
                stage.setTitle("Accidents in Air-space");
                stage.setScene(new Scene(root));
                stage.show();
            }catch (Exception e){
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static Node getNode(GridPane table, int columnNum, int rowNum){//za brisanje
        for(Node node : table.getChildren()){
            if (GridPane.getColumnIndex(node) != null &&  GridPane.getColumnIndex(node) == columnNum && GridPane.getRowIndex(node)!= null && GridPane.getRowIndex(node) == rowNum) {
                return node;
            }
        }
        return null;
    }

    private boolean isFileUpdated(File file) {

        long time;
        time=file.lastModified();
        if (this.timeStamp != time) {
            this.timeStamp = time;
            return true;
        }


        return false;
    }
}
