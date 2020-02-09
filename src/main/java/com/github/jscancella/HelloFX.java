package com.github.jscancella;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloFX extends Application {
  @Override
  public void start(Stage stage) {
      final VBox rootPane = new VBox();
      
      createToolbar(rootPane);
      
      final SplitPane mainPane = createMainPane(rootPane);
      
      //split pane of data and tag files
      createDataTreeSide(mainPane);
      
      createMetaDataSide(mainPane);
      
      final Scene scene = new Scene(rootPane, 800, 480);
      stage.setTitle("Heirloom"); //TODO add in version?
      stage.setScene(scene);
      stage.show();
  }
  
  private void createToolbar(final VBox rootPane) {
    final ToolBar toolBar = new ToolBar();
    final Button createNewBagButton = new Button("Create New Bag");
    toolBar.getItems().add(createNewBagButton);
    
    final Button openBagButton = new Button("Open Bag");
    toolBar.getItems().add(openBagButton);
    
    final Button validateBagButton = new Button("Validate Bag");
    toolBar.getItems().add(validateBagButton);
    
    final Button checkAgainstProfileButton = new Button("Check Against Profile");
    toolBar.getItems().add(checkAgainstProfileButton);
    
    final Button updateBagButton = new Button("Update Bag");
    toolBar.getItems().add(updateBagButton);
    
    final Separator verticalSeparator = new Separator(Orientation.VERTICAL);
    toolBar.getItems().add(verticalSeparator);
    
    final CheckBox isBagValidCheckbox = new CheckBox("Valid?");
    isBagValidCheckbox.setMouseTransparent(true); //disable user from clicking on it
    toolBar.getItems().add(isBagValidCheckbox);
    
    final CheckBox isBagCompleteCheckbox = new CheckBox("Complete?");
    isBagCompleteCheckbox.setMouseTransparent(true);//disable user from clicking on it
    toolBar.getItems().add(isBagCompleteCheckbox);
    
    final CheckBox isBagProfileCompliantCheckbox = new CheckBox("Profile Compliant?");
    isBagProfileCompliantCheckbox.setMouseTransparent(true);//disable user from clicking on it
    toolBar.getItems().add(isBagProfileCompliantCheckbox);
    
    rootPane.getChildren().add(toolBar);
  }
  
  private SplitPane createMainPane(final VBox rootPane) {
    final SplitPane mainPane = new SplitPane();
    mainPane.prefHeightProperty().bind(rootPane.heightProperty());
    rootPane.getChildren().add(mainPane);
    
    return mainPane;
  }
  
  private void createDataTreeSide(final SplitPane mainPane) {
    final VBox dataVbox = new VBox();
    mainPane.getItems().add(dataVbox);
    
    final SplitPane dataSplitPane = new SplitPane();
    dataSplitPane.setOrientation(Orientation.VERTICAL);
    dataVbox.getChildren().add(dataSplitPane);
    
    //TODO make custom treeview that allows users to add files easily
    final TreeView<Path> dataFiles = new TreeView<>();
    dataFiles.setRoot(new TreeItem<Path>(Paths.get("C:\\Users\\John\\work\\personal\\bagging")));
    dataSplitPane.getItems().add(dataFiles);

    //TODO make custom treeview that allows users to add files easily
    final TreeView<Path> tagFiles = new TreeView<>();
    dataSplitPane.getItems().add(tagFiles);
    tagFiles.setRoot(new TreeItem<Path>(Paths.get("C:\\Users\\John\\work\\personal\\bagging")));
  }
  
  private void createMetaDataSide(final SplitPane mainPane) {
    final VBox metadataVbox = new VBox();
    
    mainPane.getItems().add(metadataVbox);
    
    final Label metadataLabel = new Label("Bag Metadata");
    metadataVbox.getChildren().add(metadataLabel);
    final TableView<String> metadataTable = new TableView<>();
    metadataTable.prefHeightProperty().bind(metadataVbox.heightProperty().subtract(metadataLabel.heightProperty()));
    metadataVbox.getChildren().add(metadataTable);
    
    final TableColumn<String, String> keyColumn = new TableColumn<>("Key");
    keyColumn.prefWidthProperty().bind(metadataTable.widthProperty().divide(2).subtract(1));
    keyColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
    metadataTable.getColumns().add(keyColumn);
    
    final TableColumn<String, String> valueColumn = new TableColumn<>("Value");
    valueColumn.prefWidthProperty().bind(metadataTable.widthProperty().divide(2).subtract(1));
    valueColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
    metadataTable.getColumns().add(valueColumn);
    
    //TODO replace with real data
    metadataTable.setItems(FXCollections.observableArrayList("foo", "bar"));
  }

  public static void main(String[] args) {
      launch();
  }
}
