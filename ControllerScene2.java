package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerScene2 implements Initializable {
    @FXML
    public Button back;

    @FXML
    public TextArea word;

    @FXML
    public TextArea meaning;

    @FXML
    public Button save;
    @Override
    public void initialize(URL Location, ResourceBundle resource){
        back.setOnMouseClicked(event -> {
            Controller.primaryStage.close();
        });
        word.setText(Controller.word);
        meaning.setText(Controller.mean);

        save.setOnMouseClicked(event -> {
            Controller.word = word.getText();
            Controller.mean = meaning.getText();
            Controller.dictionary.put(Controller.word, Controller.mean);
            Controller.primaryStage.close();
        });
    }
}
