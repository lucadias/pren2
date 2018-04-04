package desktopapp;

import desktopapp.clientsocket.ClientSocket;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
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

public class ClientFrame extends Application {

    public void initialize() {
        this.launch();
    }

    ObservableList<String> items;
    ListView<String> list;
    GridPane grid;
    ClientSocket socket;
    Thread thread;

    @Override
    public void start(Stage primaryStage) {
        this.settings(primaryStage);
        System.out.println("asdf");
        this.addItemsToList("luca");

        try {

            socket = new ClientSocket();
            thread = new Thread(socket);
            thread.start();

        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void settings(Stage primaryStage) {
        //Frame Settings
        primaryStage.setTitle("JavaFX Welcome");
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Image image = new Image("File:image.png");
        grid.add(new ImageView(image), 0, 0, 1, 1);

        list = new ListView<String>();
        items = FXCollections.observableArrayList(
                "Single", "Double", "Suite", "Family App");
        list.setItems(items);
        items.add("test");

        grid.add(list, 1, 0, 1, 1);

        Text scenetitle2 = new Text("Position X: 00 | Position Y: 00");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
        grid.add(scenetitle2, 0, 1, 1, 1);

        Button btn = new Button("Start");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Button pressed");
                socket.write("message");
            }
        });

        grid.add(hbBtn, 1, 1, 1, 1);
        grid.setFillWidth(btn, true);
        grid.setFillHeight(btn, true);

        Scene scene = new Scene(grid, 720, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println(items);

        items.add("boay");

    }

    public void addItemsToList(String Log) {

        this.items.add(Log);

    }
}
