package com.github.jscancella;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.List;

import com.github.jscancella.buttons.CheckAgainstProfileButton;
import com.github.jscancella.buttons.CreateNewBagButton;
import com.github.jscancella.buttons.OpenBagButton;
import com.github.jscancella.buttons.SaveBagButton;
import com.github.jscancella.buttons.ValidateBagButton;
import com.github.jscancella.domain.Bag;
import com.github.jscancella.trees.BagTree;
import com.github.jscancella.trees.TreePathItem;

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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main_Old extends Application {
  private Bag bag;
  
  @Override
  public void start(final Stage stage) {
      final VBox rootPane = new VBox();
      
      final ToolBar toolBar = new ToolBar();
      rootPane.getChildren().add(toolBar);
      
      final SplitPane mainPane = createMainPane(rootPane);
      
      //split pane of data and tag files
      final TreeView<TreePathItem> dataFiles = new BagTree();
      final TreeView<TreePathItem> tagFiles = new BagTree();
      createDataTreeSide(mainPane, dataFiles, tagFiles);
      
      final TableView<SimpleImmutableEntry<String, String>> metadataTable = createMetaDataSide(mainPane);
      
      populateToolbar(toolBar, dataFiles, tagFiles, metadataTable, stage);
      
      final Scene scene = new Scene(rootPane, 800, 480);
      stage.setTitle("Heirloom"); //TODO add in version?
      stage.setScene(scene);
      stage.show();
  }
  
  private void populateToolbar(final ToolBar toolBar, TreeView<TreePathItem> dataFiles, TreeView<TreePathItem> tagFiles, TableView<SimpleImmutableEntry<String, String>> metadataTable, final Stage stage) {
    final Button createNewBagButton = new CreateNewBagButton(this, dataFiles, tagFiles, metadataTable);
    toolBar.getItems().add(createNewBagButton);
    
    final Button openBagButton = new OpenBagButton(this, dataFiles, tagFiles, metadataTable, stage);
    toolBar.getItems().add(openBagButton);
    
    final CheckBox isBagValidCheckbox = new CheckBox("Valid?");
    final CheckBox isBagCompleteCheckbox = new CheckBox("Complete?");
    final Button validateBagButton = new ValidateBagButton(this, isBagValidCheckbox, isBagCompleteCheckbox);
    toolBar.getItems().add(validateBagButton);
    
    final CheckBox isBagProfileCompliantCheckbox = new CheckBox("Profile Compliant?");
    final Button checkAgainstProfileButton = new CheckAgainstProfileButton(this, isBagProfileCompliantCheckbox);
    toolBar.getItems().add(checkAgainstProfileButton);
    
    final Button updateBagButton = new SaveBagButton(this, dataFiles, tagFiles, metadataTable, stage);
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
  
  private void createDataTreeSide(final SplitPane mainPane, final TreeView<TreePathItem> dataFiles, final TreeView<TreePathItem> tagFiles) {
    final VBox dataVbox = new VBox();
    mainPane.getItems().add(dataVbox);
    
    final SplitPane dataSplitPane = new SplitPane();
    dataSplitPane.setOrientation(Orientation.VERTICAL);
    dataVbox.getChildren().add(dataSplitPane);
    
    //TODO make custom treeview that allows users to add files easily
//    dataFiles.setOnMouseClicked(event -> handleMouseClicked(event));
    //TODO remove this fake data
    final Path data = Paths.get("C:\\Users\\John\\work\\personal\\bagging\\src\\test\\resources\\bags\\v1_0\\bag\\data");
    dataFiles.setRoot(new TreeItem<TreePathItem>(data));
    dataSplitPane.getItems().add(dataFiles);

    //TODO make custom treeview that allows users to add files easily
    dataSplitPane.getItems().add(tagFiles);
    //TODO remove this fake data
    final Path tagDir = Paths.get("C:\\Users\\John\\work\\personal\\bagging\\src\\test\\resources\\bags\\v1_0\\bag");
    final TreeItem<Path> rootPath = new TreeItem<Path>(tagDir);
    tagFiles.setRoot(rootPath);
  }
  
//  private void handleMouseClicked(MouseEvent event) {
//    Node node = event.getPickResult().getIntersectedNode();
//    // Accept clicks only on node cells, and not on empty spaces of the TreeView
//    if (node instanceof Text && ((Text)node).getText() != null) {
//      System.err.println(((Text)node).getText());
//    }
//  }
  
  private TableView<SimpleImmutableEntry<String, String>> createMetaDataSide(final SplitPane mainPane) {
    final VBox metadataVbox = new VBox();
    
    mainPane.getItems().add(metadataVbox);
    
    //TODO add button or something to add a new row of data... WTF why is this so hard?
    final Label metadataLabel = new Label("Bag Metadata");
    metadataVbox.getChildren().add(metadataLabel);
    final TableView<SimpleImmutableEntry<String, String>> metadataTable = new TableView<>();
    metadataTable.getSelectionModel().setCellSelectionEnabled(true);
    metadataTable.setEditable(true);
    metadataTable.prefHeightProperty().bind(metadataVbox.heightProperty().subtract(metadataLabel.heightProperty()));
    metadataVbox.getChildren().add(metadataTable);
    
    final TableColumn<SimpleImmutableEntry<String, String>, String> keyColumn = new TableColumn<>("Key");
    keyColumn.prefWidthProperty().bind(metadataTable.widthProperty().divide(2).subtract(1));
    keyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    keyColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
    metadataTable.getColumns().add(keyColumn);
    
    final TableColumn<SimpleImmutableEntry<String, String>, String> valueColumn = new TableColumn<>("Value");
    valueColumn.prefWidthProperty().bind(metadataTable.widthProperty().divide(2).subtract(1));
    valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    valueColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));
    metadataTable.getColumns().add(valueColumn);
    
    
    //TODO replace with real data
    final List<SimpleImmutableEntry<String, String>> list = 
        Arrays.asList(new SimpleImmutableEntry<>("foo", "bar"), new SimpleImmutableEntry<>("ham", "sandwich"));
    metadataTable.setItems(FXCollections.observableArrayList(list));
    
    return metadataTable;
  }
  
  public Bag getBag() {
    return this.bag;
  }
  
  public void InformUserAboutError(Exception e) {
    //TODO
  }

  public static void main(String[] args) {
      launch();
  }

  public void resetBag(){
    this.bag = null;
  }
  
  public void setBag(final Bag bag) {
    this.bag = bag;
  }
}
