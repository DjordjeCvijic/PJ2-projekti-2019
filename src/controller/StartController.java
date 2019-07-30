package controller;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import main.Main;
import model.Simulator;
import model.Airspace;
import model.Radar;

import java.io.File;

public class StartController {

    @FXML private Button startBtn;

    public void startBtnClick(ActionEvent actionEvent) {


        Airspace a=new Airspace();
        Simulator s=new Simulator(a);
        s.start();
        Radar r=new Radar(a);
        r.start();
    try{
        Parent root = FXMLLoader.load(getClass().getResource(".."+ File.separator+"view"+File.separator+"mainApplication.fxml"));
        Main.primaryStage1.setTitle("Air space");
        Main.primaryStage1.setScene(new Scene(root));
        Main.primaryStage1.show();
    }catch (Exception e){
    e.printStackTrace();
    }






    }
}
