package RectangleDetection;

import org.opencv.core.Core;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class ObjRecognition extends Application
{

    @Override
    public void start(Stage primaryStage)
    {
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ObjRecognition.fxml"));

            BorderPane root = loader.load();

            root.setStyle("-fx-background-color: whitesmoke;");

            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            primaryStage.setTitle("Object Recognition");
            primaryStage.setScene(scene);

            primaryStage.show();


            ObjRecognitionController controller = loader.getController();
            primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we)
                {
                    controller.setClosed();
                }
            }));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void initialize()
    {
        // load the native OpenCV library
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        System.load("C:/Users/luca_/Documents/NetBeansProjects/PrenTutorialOpenCV/lib/opencv_java331.dll");
        
        
        //System.load("/home/pi/opencv-3.3.1/build/lib/libopencv_java331.so");


        launch();
    }
}