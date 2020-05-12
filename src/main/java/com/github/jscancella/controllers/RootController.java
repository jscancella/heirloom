package com.github.jscancella.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Optional;

import com.github.jscancella.BagTreeItem;
import com.github.jscancella.conformance.BagLinter;
import com.github.jscancella.conformance.exceptions.BagitVersionIsNotAcceptableException;
import com.github.jscancella.conformance.exceptions.FetchFileNotAllowedException;
import com.github.jscancella.conformance.exceptions.MetatdataValueIsNotAcceptableException;
import com.github.jscancella.conformance.exceptions.MetatdataValueIsNotRepeatableException;
import com.github.jscancella.conformance.exceptions.RequiredManifestNotPresentException;
import com.github.jscancella.conformance.exceptions.RequiredMetadataFieldNotPresentException;
import com.github.jscancella.conformance.exceptions.RequiredTagFileNotPresentException;
import com.github.jscancella.domain.Bag;
import com.github.jscancella.domain.BagBuilder;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class RootController {
  @FXML private VBox rootPane;
  @FXML private Button saveBagButton;
  @FXML private Button validateButton;
  @FXML private Button checkAgainstProfileButton;
  @FXML private CheckBox isValidCheckbox;
  @FXML private CheckBox isCompleteCheckbox;
  @FXML private CheckBox ignoreHiddenFiles;
  @FXML private ProgressIndicator progress;
  @FXML private TreeView<Path> dataFilesTree;
  @FXML private TreeView<Path> tagFilesTree;
  @FXML private TableView<SimpleImmutableEntry<String, String>> metadataTable;
  
  private final ContextMenu bagtreeContextMenu = new ContextMenu();
  private final ContextMenu metadataContextMenu = new ContextMenu();
  
  private Bag bag;
  
  @FXML
  public void initialize() {
    initalizeTreeViews();
    initalizeMetadata();
    
    initalizeTreeMenu();
    initalizeMetadataMenu();
    
    handleCreateNewBagButtonAction(null);
  }
  
  private void initalizeTreeViews() {
    tagFilesTree.setContextMenu(bagtreeContextMenu);
    dataFilesTree.setContextMenu(bagtreeContextMenu);
  }
  
  private void initalizeMetadata() {
    metadataTable.getSelectionModel().setCellSelectionEnabled(true);
    metadataTable.setEditable(true);
    //TODO change to be an ObservableList<ObservableList<String>>
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
    
    metadataTable.setContextMenu(metadataContextMenu);
  }
  
  private void initalizeTreeMenu() {
    final MenuItem addFileMenuItem = new MenuItem("Add File");
    addFileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          final List<File> dataFiles = new FileChooser().showOpenMultipleDialog(rootPane.getScene().getWindow());
          for(final File dataFile : dataFiles) {
            dataFilesTree.getRoot().getChildren().add(new BagTreeItem(dataFile.toPath()));
          }
        }
    });
    final MenuItem addFolderMenuItem = new MenuItem("Add Folder");
    addFolderMenuItem.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          final File dir = new DirectoryChooser().showDialog(rootPane.getScene().getWindow());
          if(dir != null) {
            dataFilesTree.getRoot().getChildren().add(new BagTreeItem(dir.toPath()));
          }
        }
    });
    bagtreeContextMenu.getItems().addAll(addFileMenuItem, addFolderMenuItem);
  }
  
  private void initalizeMetadataMenu() {
    final MenuItem addMenuItem = new MenuItem("Add");
    addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        metadataTable.getItems().add(new SimpleImmutableEntry<String, String>("New Key", "New Value"));
      }
    });
    final MenuItem addFolderMenuItem = new MenuItem("Delete");
    addFolderMenuItem.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          metadataTable.getItems().remove(metadataTable.getSelectionModel().getSelectedItem());
        }
    });
    metadataContextMenu.getItems().addAll(addMenuItem, addFolderMenuItem);
  }
  
  @FXML protected void handleValidateButtonAction(ActionEvent event) {    
    try{
//      progress.setVisible(true);
//      progress.setProgress(-1);
      
      //TODO implement as tasks so that progress shows correctly
      isValidCheckbox.setSelected(bag.justValidate());
      isCompleteCheckbox.setSelected(bag.isComplete(ignoreHiddenFiles.isSelected()));
    } catch(Exception e){
      informUserAboutError("Validation Error", "There was an error while validating the bag: [" + e + "]", e);
    }
    
//    progress.setVisible(false);
  }
  
  @FXML protected void handleOpenBagButtonAction(ActionEvent event) {
    final File bagitRootDir = new DirectoryChooser().showDialog(rootPane.getScene().getWindow());
    
    if(bagitRootDir != null) {
      try{
        readBag(bagitRootDir);
      } catch(IOException e){
        informUserAboutError("Error Opening Bag", "There was an error opening the bag [" + e + "]", e);
      }
    }
  }
  
  private void readBag(File bagitRootDir) throws IOException {
    readBag(bagitRootDir.toPath());
  }
  
  //read the bag into heirloom state
  private void readBag(Path bagitRootDir) throws IOException {
    bag = Bag.read(bagitRootDir);
    dataFilesTree.setRoot(new BagTreeItem(bag.getDataDir()));
    dataFilesTree.getRoot().setExpanded(true);
    
    tagFilesTree.setRoot(new BagTreeItem(bag.getRootDir()));
    tagFilesTree.getRoot().getChildren().removeIf(item -> 
      bag.getDataDir().equals(((BagTreeItem) item).getFullPath())
    ); //remove the data dir from the treeview
    tagFilesTree.getRoot().setExpanded(true);
    
    metadataTable.setItems(FXCollections.observableArrayList(bag.getMetadata().getList()));
    
    //now that we have successfully read a bag, we can validate it and check it's profile
    validateButton.setDisable(false);
    checkAgainstProfileButton.setDisable(false);
  }
  
  @FXML protected void handleCheckAgainstProfileButtonAction() {
    final TextInputDialog txtDlg = new TextInputDialog();
    txtDlg.setTitle("Check Against Profile");
    txtDlg.setHeaderText("Please enter the URL for the profile that you wish to validate the bag against.");
    
    final Optional<String> result = txtDlg.showAndWait(); //for test use:
    //https://raw.githubusercontent.com/bagit-profiles/bagit-profiles-validator/master/fixtures/bagProfileBar.json
    
    result.ifPresent(input -> {
      try(InputStream jsonProfile = new URL(result.get()).openStream()){
        BagLinter.checkAgainstProfile(jsonProfile, bag);
      } catch(IOException e){
        informUserAboutError("Failed Profile Check", "There was a problem reading the profile: [" + e + "].", e);
      } catch(FetchFileNotAllowedException e){
        informUserAboutError("Failed Profile Check", e.getMessage(), e);
      } catch(RequiredMetadataFieldNotPresentException e){
        informUserAboutError("Failed Profile Check", e.getMessage(), e);
      } catch(MetatdataValueIsNotAcceptableException e){
        informUserAboutError("Failed Profile Check", e.getMessage(), e);
      } catch(RequiredManifestNotPresentException e){
        informUserAboutError("Failed Profile Check", e.getMessage(), e);
      } catch(BagitVersionIsNotAcceptableException e){
        informUserAboutError("Failed Profile Check", e.getMessage(), e);
      } catch(RequiredTagFileNotPresentException e){
        informUserAboutError("Failed Profile Check", e.getMessage(), e);
      } catch(MetatdataValueIsNotRepeatableException e){
        informUserAboutError("Failed Profile Check", e.getMessage(), e);
      }
    });
  }
  
  @FXML protected void handleCreateNewBagButtonAction(ActionEvent event) {
    metadataTable.setItems(FXCollections.observableArrayList());
    dataFilesTree.setRoot(new BagTreeItem(Paths.get("data")));
    
    final BagTreeItem dummyTagFolderItem = new BagTreeItem(Paths.get("tags"));
    tagFilesTree.setRoot(dummyTagFolderItem);
    
    //disable them until bag is saved
    validateButton.setDisable(true);
    checkAgainstProfileButton.setDisable(true);
    
    //now that we created a new bag, we are allowed to save it
    saveBagButton.setDisable(false);
  }
  
  @FXML protected void handleSaveBagButtonAction(ActionEvent event) {
    final File bagitRootDir = new DirectoryChooser().showDialog(rootPane.getScene().getWindow());
    
    if(bagitRootDir != null) {
      try{
        final BagBuilder bagBuilder = new BagBuilder();
        bagBuilder.bagLocation(bagitRootDir.toPath());
        
        for(TreeItem<Path> dataItem : dataFilesTree.getRoot().getChildren()) {
          final BagTreeItem castItem = (BagTreeItem) dataItem;
          if(ignoreHiddenFiles.isSelected() || !Files.isHidden(castItem.getFullPath())) {
            bagBuilder.addPayloadFile(castItem.getFullPath(), dataItem.getValue().toString());
          }
        }
        
        for(TreeItem<Path> tagItem : tagFilesTree.getRoot().getChildren()) {
          final BagTreeItem castItem = (BagTreeItem) tagItem;
          if(ignoreHiddenFiles.isSelected() || !Files.isHidden(castItem.getFullPath())) {
            bagBuilder.addTagFile(castItem.getFullPath());
          }
        }
        
        for(SimpleImmutableEntry<String, String> entry : metadataTable.getItems()) {
          bagBuilder.addMetadata(entry.getKey(), entry.getValue());
        }
        
        this.bag = bagBuilder.write();
      } catch(IOException e){
        informUserAboutError("Error Saving Bag", "There was an error saving the bag [" + e + "]", e);
      }
    }
  }
  
  private static void informUserAboutError(final String title, final String content, final Exception e) {
    //TODO log to debug.log
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle(title);
    dialog.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonData.CANCEL_CLOSE));
    dialog.setContentText(content);
    dialog.showAndWait();
  }
}
