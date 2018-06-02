package desktopapp;

import desktopapp.clientsocket.ThreadedClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
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

    private static ObservableList<String> items;
    private static ListView<String> list;
    private GridPane grid;
    public static StringProperty test123 = new SimpleStringProperty("PositionX: 000 \nPositionZ: 000");
   Thread thread;
    public static String pushtolist;
    public static ThreadedClient instance = ThreadedClient.getInstance();

    //   public static LabelValue lv = LabelValue.getInstance().getInstance();
    //   public static ObservableValue<String> labelvalue;
    @Override
    public void start(Stage primaryStage) throws InterruptedException, IOException {
        this.settings(primaryStage);

        pushtolist = null;

        //     lv = LabelValue.getInstance();
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

       
        list = new ListView<String>();
        items = FXCollections.observableArrayList(
                "DebugLog initialized");
        list.setItems(items);

    //    grid.add(list, 1, 0, 1, 1);

        Text scenetitle2 = new Text(test123.getValue());
        test123.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                scenetitle2.setText(test123.getValue());
            }
        });

        //scenetitle2.textProperty().bind(lv);
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 80));
        grid.add(scenetitle2, 0, 1, 1, 1);

        Button btn = new Button("Start");
        btn.setStyle("-fx-font-size: 80pt;");


        HBox hbBtn = new HBox(1000);
        hbBtn.setAlignment(Pos.CENTER_RIGHT);
        hbBtn.getChildren().add(btn);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Sending Start Signal to Raspi");
                addItemsToList("Sending Start Signal to Raspi");
                instance.startButtonPressed();

            }
        });
      /*  Button btnstop = new Button("Last erkannt");
        HBox hbBtn1 = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(btnstop);
        btnstop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Sending Stop Signal to Raspi");
                addItemsToList("Sending Stop Signal to Raspi");
         //       instance.stopButtonPressed();
            }
        });
*/
        grid.add(hbBtn, 1, 1, 1, 1);

      //  grid.add(hbBtn1, 1, 1, 1, 1);
        grid.setFillWidth(btn, true);
        grid.setFillHeight(btn, true);
      
        Scene scene = new Scene(grid, 900, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println(items);

    }

    public static void addItemsToList(String Log) {

        items.add(Log);

//        list.getItems().set( list.getSelectionModel().getSelectedIndex(), Log );
    }
}
