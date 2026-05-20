package fes.aragon.unam.administracion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PepsiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PepsiApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),800,500);
        stage.setResizable(false);

        stage.setTitle("Pepsi");
        stage.setScene(scene);
        stage.show();
    }
}
