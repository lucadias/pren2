package desktopapp;

import desktopapp.clientsocket.PiServerClient;
import desktopapp.clientsocket.ThreadedClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import piserver.PiServer;

public class ClientFrame extends Application {

    public void initialize() {
        this.launch();
    }

    private ObservableList<String> items;
    private ListView<String> list;
    private GridPane grid;
    Thread thread;
    public static String pushtolist;
    public static ThreadedClient instance = ThreadedClient.getInstance();
    
    public static LabelValue lv = LabelValue.getInstance().getInstance();
    public static ObservableValue<String> labelvalue;
    
    @Override
    public void start(Stage primaryStage) throws InterruptedException, IOException {
        this.settings(primaryStage);
        
        pushtolist = null;
        
        
        lv = LabelValue.getInstance();
        
        instance = ThreadedClient.getInstance();
        thread = new Thread(instance);
        thread.start();
    }

    public void settings(Stage primaryStage) {
        //Frame Settings
        primaryStage.setTitle("JavaFX Welcome");
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Image image = new Image("File:image.jpg");
        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(900);
        imageview.setFitWidth(1200);
        grid.add(imageview, 0, 0, 1, 1);

        list = new ListView<String>();
        items = FXCollections.observableArrayList(
                "DebugLog initialized");
        list.setItems(items);

        grid.add(list, 1, 0, 1, 1);

        Text scenetitle2 = new Text(lv.getValue());
       //scenetitle2.textProperty().bind(lv);
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
        grid.add(scenetitle2, 0, 1, 1, 1);

        Button btn = new Button("Start");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Sending Start Signal to Raspi");
                addItemsToList("Sending Start Signal to Raspi");
                instance.startButtonPressed();

            }
        });

        grid.add(hbBtn, 1, 1, 1, 1);
        grid.setFillWidth(btn, true);
        grid.setFillHeight(btn, true);

        Scene scene = new Scene(grid, 1500, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println(items);

    }

    public void addItemsToList(String Log) {

        this.items.add(Log);
//        list.getItems().set( list.getSelectionModel().getSelectedIndex(), Log );

    }
}
