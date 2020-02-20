package com.github.jscancella;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.jscancella.buttons.CheckAgainstProfileButton;
import com.github.jscancella.buttons.CreateNewBagButton;
import com.github.jscancella.buttons.OpenBagButton;
import com.github.jscancella.buttons.UpdateBagButton;
import com.github.jscancella.buttons.ValidateBagButton;
import com.github.jscancella.domain.Bag;
import com.github.jscancella.trees.BagTree;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Node;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
  private Bag bag;
  
  @Override
  public void start(final Stage stage) {
      final VBox rootPane = new VBox();
      
      final ToolBar toolBar = new ToolBar();
      rootPane.getChildren().add(toolBar);
      
      final SplitPane mainPane = createMainPane(rootPane);
      
      //split pane of data and tag files
      final TreeView<Path> dataFiles = new BagTree();
      final TreeView<Path> tagFiles = new BagTree();
      createDataTreeSide(mainPane, dataFiles, tagFiles);
      
      final TableView<String> metadataTable = createMetaDataSide(mainPane);
      
      populateToolbar(toolBar, dataFiles, tagFiles, metadataTable, stage);
      
      final Scene scene = new Scene(rootPane, 800, 480);
      stage.setTitle("Heirloom"); //TODO add in version?
      stage.setScene(scene);
      stage.show();
  }
  
  private void populateToolbar(final ToolBar toolBar, TreeView<Path> dataFiles, TreeView<Path> tagFiles, TableView<String> metadataTable, final Stage stage) {
    final Button createNewBagButton = new CreateNewBagButton(dataFiles, tagFiles, metadataTable);
    toolBar.getItems().add(createNewBagButton);
    
    final Button openBagButton = new OpenBagButton(bag, dataFiles, tagFiles, metadataTable, stage);
    toolBar.getItems().add(openBagButton);
    
    final CheckBox isBagValidCheckbox = new CheckBox("Valid?");
    final CheckBox isBagCompleteCheckbox = new CheckBox("Complete?");
    final Button validateBagButton = new ValidateBagButton(bag, isBagValidCheckbox, isBagCompleteCheckbox);
    toolBar.getItems().add(validateBagButton);
    
    final CheckBox isBagProfileCompliantCheckbox = new CheckBox("Profile Compliant?");
    final Button checkAgainstProfileButton = new CheckAgainstProfileButton(isBagProfileCompliantCheckbox);
    toolBar.getItems().add(checkAgainstProfileButton);
    
    final Button updateBagButton = new UpdateBagButton(dataFiles, tagFiles, metadataTable, stage);
    toolBar.getItems().add(updateBagButton);
    
    final Separator verticalSeparator = new Separator(Orientation.VERTICAL);
    toolBar.getItems().add(verticalSeparator);
    
    isBagValidCheckbox.setMouseTransparent(true); //disable user from clicking on it
    toolBar.getItems().add(isBagValidCheckbox);
    
    isBagCompleteCheckbox.setMouseTransparent(true);//disable user from clicking on it
    toolBar.getItems().add(isBagCompleteCheckbox);
    
    isBagProfileCompliantCheckbox.setMouseTransparent(true);//disable user from clicking on it
    toolBar.getItems().add(isBagProfileCompliantCheckbox);
  }
  
  private SplitPane createMainPane(final VBox rootPane) {
    final SplitPane mainPane = new SplitPane();
    mainPane.prefHeightProperty().bind(rootPane.heightProperty());
    rootPane.getChildren().add(mainPane);
    
    return mainPane;
  }
  
  private void createDataTreeSide(final SplitPane mainPane, final TreeView<Path> dataFiles, final TreeView<Path> tagFiles) {
    final VBox dataVbox = new VBox();
    mainPane.getItems().add(dataVbox);
    
    final SplitPane dataSplitPane = new SplitPane();
    dataSplitPane.setOrientation(Orientation.VERTICAL);
    dataVbox.getChildren().add(dataSplitPane);
    
    //TODO make custom treeview that allows users to add files easily
    dataFiles.setOnMouseClicked(event -> handleMouseClicked(event));
    //TODO remove this fake data
    dataFiles.setRoot(new TreeItem<Path>(Paths.get("C:\\Users\\John\\work\\personal\\bagging")));
    dataSplitPane.getItems().add(dataFiles);

    //TODO make custom treeview that allows users to add files easily
    dataSplitPane.getItems().add(tagFiles);
    //TODO remove this fake data
    final TreeItem<Path> rootPath = new TreeItem<Path>(Paths.get("C:\\Users\\John\\work\\personal\\bagging"));
    tagFiles.setRoot(rootPath);
  }
  
  private void handleMouseClicked(MouseEvent event) {
    Node node = event.getPickResult().getIntersectedNode();
    // Accept clicks only on node cells, and not on empty spaces of the TreeView
    if (node instanceof Text && ((Text)node).getText() != null) {
      System.err.println(((Text)node).getText());
    }
  }
  
  private TableView<String> createMetaDataSide(final SplitPane mainPane) {
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
    
    return metadataTable;
  }

  public static void main(String[] args) {
      launch();
  }
}
