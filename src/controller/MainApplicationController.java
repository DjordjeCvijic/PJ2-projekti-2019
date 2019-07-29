package controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;



public class MainApplicationController implements Initializable {

    @FXML
    private AnchorPane aPanel;

    public static GridPane table(String str,int rows,int columns) {
        GridPane table = new GridPane();

        for(int j=0;j<columns;j++) {
            for (int i = 0; i < rows; i++) {


                Text text = new Text(str);
                table.add(text, j, i);
                GridPane.setValignment(text, VPos.CENTER);
                GridPane.setHalignment(text, HPos.CENTER);


            }
        }
        table.setAlignment(Pos.CENTER);


        Text text=new Text("YUI");
        Node node = getNode(table, 3, 3);
        table.getChildren().remove(node);
        table.add(text,3,3);
        GridPane.setValignment(text, VPos.CENTER);
        GridPane.setHalignment(text, HPos.CENTER);




        table.setGridLinesVisible(true);
        //table.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        //  table.setMaxSize(4,4);
        table.setLayoutX(20);
        table.setLayoutY(20);
        return table;

    }

        @Override
    public void initialize(URL location, ResourceBundle resources) {


        aPanel.getChildren().add(table("ASD",10,10));
    }

    public static Node getNode(GridPane table, int columnNum, int rowNum){
        for(Node node : table.getChildren()){
            if (GridPane.getColumnIndex(node) != null &&  GridPane.getColumnIndex(node) == columnNum && GridPane.getRowIndex(node)!= null && GridPane.getRowIndex(node) == rowNum) {
                return node;
            }
        }
        return null;
    }
}
