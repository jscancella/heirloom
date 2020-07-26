package com.github.jscancella;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Label;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

/**
 * This class is responsible for creating the toolbars.
 * The toolbars contain all the buttons for interacting with the bag.
 * It also contains information about the state of the bag and its status.
 */
public class ToolBars extends JPanel {
  private static final long serialVersionUID = 1L;
  
  private final Button createNewBagButton = new Button("Create New Bag");
  private final Button openBagButton = new Button("Open Bag");
  private final Button validateBagButton = new Button("Validate");
  private final Button checkAgainstProfileButton = new Button("Check Against Profile");
  private final Button saveBagButton = new Button("Save Bag");
  private final Checkbox ignoreHiddenFilesCheckbox = new Checkbox("Ignore Hidden Files");
  private final Label isValidLabel = new Label("Valid? : UNKNOWN");
  private final Label isCompleteLabel = new Label("Complete? : UNKNOWN");
  private final Label isProfileCompliantLabel = new Label("Profile Compliant? : UNKNOWN");

  public ToolBars() {
    //TODO add the MainPane as a listener to all the buttons here
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
    final JToolBar toolBar = new JToolBar();
    toolBar.setFloatable(false);
    toolBar.add(createNewBagButton);
    toolBar.add(openBagButton);
    toolBar.add(validateBagButton);
    toolBar.add(checkAgainstProfileButton);
    toolBar.add(saveBagButton);
    toolBar.add(new JSeparator(JSeparator.VERTICAL));
    toolBar.add(ignoreHiddenFilesCheckbox);
    this.add(toolBar);
    
    final JToolBar bagStatus = new JToolBar();
    bagStatus.setFloatable(false);
    bagStatus.add(isValidLabel);
    bagStatus.add(isCompleteLabel);
    bagStatus.add(isProfileCompliantLabel);
    this.add(bagStatus);
  }
}
