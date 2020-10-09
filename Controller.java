package sample;

import com.gtranslate.Language;
import com.gtranslate.Translator;
import javafx.event.Event;
import java.io.FileWriter;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.io.File;
import java.io.*;
import com.sun.speech.freetts.*;

public class Controller implements Initializable {

    @FXML
    public Button searchButton;

    @FXML
    public TextField searchWord;

    @FXML
    public TextArea Meaning;

    @FXML
    public ListView<String> listWords;

    public  static Map<String, String> dictionary = new HashMap<String, String>();

    @FXML
    public ListView<String> FilterWords;

    @FXML
    public Button edit;

    @FXML
    public Button delete;

    @FXML
    public Button add;

    @FXML
    public Button speak;
    public static String word;
    public static String mean;
    public static Stage primaryStage = new Stage();
    public void Edit() {
            word = searchWord.getText();
            mean = Meaning.getText();
            try {

                //Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
                primaryStage.setTitle("Hello World");
                primaryStage.setScene(new Scene(root, 600, 500));
                primaryStage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchWord.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                search();
                listWords.setOnMouseClicked(event1 ->  {
                    showMeaning();
                    searchWord.setText(listWords.getSelectionModel().getSelectedItem());
                });
            }
        });
        searchButton.setOnMouseClicked(event -> search());
        listWords.setOnMouseClicked(event -> {
            showMeaning();
        });

        edit.setOnMouseClicked(event -> {
            Edit();
            listWords.getItems().clear();
            listWords.getItems().addAll(dictionary.keySet());
            searchWord.setText(word);
            Meaning.setText(mean);

        });

        add.setOnMouseClicked(event -> {
            try {

                //Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
                primaryStage.setTitle("Hello World");
                primaryStage.setScene(new Scene(root, 600, 500));
                primaryStage.show();
                listWords.getItems().clear();
                listWords.getItems().addAll(dictionary.keySet());
                searchWord.setText(word);
                Meaning.setText(mean);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.initializeWordList();

        delete.setOnMouseClicked(event -> {
            word = searchWord.getText();
            dictionary.remove(word);
            listWords.getItems().remove(word);
            searchWord.clear();
            Meaning.clear();
        });

        speak.setOnMouseClicked(event -> {
            text_voice();
        });
        
    }


    public void initializeWordList() {
        File fin = new File("C:\\Users\\Dell\\IdeaProjects\\Dictionary\\src\\sample\\luon.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(fin);


            while (sc.hasNext()) {
                String str = "";
                String line = "0";
                while (!line.equals("")){
                    line = sc.nextLine();
                    str += line + "\n";
                }
                int pos = str.indexOf('/');
                String work = str.substring(1,pos-1);
                String meaning = str.substring(pos);
                dictionary.put(work, meaning);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listWords.getItems().addAll(dictionary.keySet());
    }

    public void Filter(String s) {
        listWords.getItems().clear();
        Set<String> set = dictionary.keySet();
        for (String key : set) {
            if (key.indexOf(s) == 0)
                listWords.getItems().add(key);

        }
    }

    public void search() {
        String searchedWord = searchWord.getText();
        this.Filter(searchedWord);
        if (searchedWord != null && searchedWord.equals("") == false) {
            String wordMeaning = dictionary.get(searchedWord);
            Meaning.setText(wordMeaning);
        }
    }

    public void showMeaning() {
        String searchedWord = listWords.getSelectionModel().getSelectedItem();
        searchWord.setText(searchedWord);
        if (searchedWord != null && searchedWord.equals("") == false) {
            String wordMeaning = dictionary.get(searchedWord);
            Meaning.setText(wordMeaning);
        }
    }

    private static final String VOICENAME = "kevin16";

    public void text_voice() {
        Voice voice;
        VoiceManager vm = VoiceManager.getInstance();
        voice = vm.getVoice(VOICENAME);

        voice.allocate();
        try {
            voice.speak(searchWord.getText());
        } catch (Exception e) {

        }
    }


}
