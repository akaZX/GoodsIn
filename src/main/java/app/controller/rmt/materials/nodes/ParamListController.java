package app.controller.rmt.materials.nodes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

public class ParamListController extends StackPane {

    @FXML
    public JFXButton removeBtn;

    @FXML
    JFXButton addBtn;

    @FXML
    JFXListView<Object> listView;

    @FXML
    Label selectedLabel;

    @FXML
    Label title;

    @FXML
    JFXTextField newEntry;


    public ParamListController(String title) {

        this();
        this.title.setText(title);
    }


    public ParamListController() {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/materialProfiles/ParamList.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {

            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        listListeners();

    }


    private void listListeners() {

        listView.setOnKeyPressed(event -> {
            if (listView.getSelectionModel().getSelectedItem() != null) {

                if (event.getCode() == KeyCode.ENTER) {
                    removeBtn.setDisable(false);
                    selectedLabel.setText(listView.getSelectionModel().getSelectedItem().toString());
                }
            }
            else {
                removeBtn.setDisable(true);

            }
        });

        listView.setOnMouseClicked(event -> {
            if (listView.getSelectionModel().getSelectedItem() != null) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    removeBtn.setDisable(false);

                    selectedLabel.setText(listView.getSelectionModel().getSelectedItem().toString());
                }
            }
            else {
                removeBtn.setDisable(true);

            }
        });
    }


    public Object getSelectedItem() {

        return listView.getSelectionModel().getSelectedItem();
    }


    public <T> void reloadList(List<T> list) {

        listView.getItems().removeAll();
        listView.getItems().addAll(list);
    }


    public void setTitle(String title) {

        this.title.setText(title);
    }


    public String getText() {

        return newEntry.getText();
    }


    public void clearText() {

        newEntry.clear();
    }


    public void clearList() {

        listView.getItems().clear();
    }


    public void clearSelectedLabel() {

        selectedLabel.setText("");
    }


}
