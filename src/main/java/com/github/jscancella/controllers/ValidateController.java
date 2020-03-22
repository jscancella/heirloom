package com.github.jscancella.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

//TODO look at https://stackoverflow.com/questions/19342259/how-to-create-multiple-javafx-controllers-with-different-fxml-files
//and break up UI...
public class ValidateController {
  @FXML private CheckBox isValidCheckbox;
  @FXML private CheckBox isCompleteCheckbox;
  
  @FXML protected void handleValidateButtonAction(ActionEvent event) {
//    actiontarget.setText("Sign in button pressed");
  }
}