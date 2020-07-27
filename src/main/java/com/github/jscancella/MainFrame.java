package com.github.jscancella;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * The purpose of this class is to setup the look and feel of the application.
 * It is also responsible for wiring up the logic and using the bagging library
 */
public class MainFrame extends JFrame implements ActionListener, TableModelListener, TreeModelListener{
  private static final long serialVersionUID = 1L;
  
  //global shared variables
  private final JTree bagFilesTree;
  private final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("BagRoot");
  private final DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode("data");
  private final JTable metadataTable;
  private Object[][] metadata;
  private final JButton createNewBagButton = new JButton("Create New Bag");
  private final JButton openBagButton = new JButton("Open Bag");
  private final JButton validateBagButton = new JButton("Validate");
  private final JButton checkAgainstProfileButton = new JButton("Check Against Profile");
  private final JButton saveBagButton = new JButton("Save Bag");
  private final JCheckBox ignoreHiddenFilesCheckbox = new JCheckBox("Ignore Hidden Files");
  private final JLabel isValidLabel = new JLabel("Valid? : UNKNOWN");
  private final JLabel isCompleteLabel = new JLabel("Complete? : UNKNOWN");
  private final JLabel isProfileCompliantLabel = new JLabel("Profile Compliant? : UNKNOWN");
  //end global shared variables

  public MainFrame() {
    super("Heirloom");
    
    metadata  = new Object[][] {{"Date Created", LocalDate.now()}};
    metadataTable = new JTable(metadata, new String[]{"Key", "Value"});
    rootNode.add(dataNode);
    bagFilesTree = new JTree(rootNode);
    
    bagFilesTree.getModel().addTreeModelListener(this);
    metadataTable.getModel().addTableModelListener(this);
    createNewBagButton.addActionListener(this);
    openBagButton.addActionListener(this);
    validateBagButton.addActionListener(this);
    checkAgainstProfileButton.addActionListener(this);
    saveBagButton.addActionListener(this);
    ignoreHiddenFilesCheckbox.addActionListener(this);

    createMainPane(this, rootNode, metadata);
    setupToolbars(this);
    
    this.setExtendedState(JFrame.MAXIMIZED_BOTH); // open full sized
    this.setSize(800, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
  
  private void createMainPane(final JFrame frame, final DefaultMutableTreeNode rootNode, final Object[][] metadata) {
    bagFilesTree.setEditable(true); //triple click to edit node name...
    final JScrollPane dataPane = new JScrollPane(bagFilesTree);
    
    final JScrollPane metadataPane = new JScrollPane(metadataTable);
    metadataTable.setFillsViewportHeight(true);
    
    final JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dataPane, metadataPane);
    mainPane.setResizeWeight(0.25);
    frame.add(mainPane, BorderLayout.CENTER);
  }
  
  private void setupToolbars(final JFrame frame) {
    final JPanel toolbars = new JPanel();
    toolbars.setLayout(new BoxLayout(toolbars, BoxLayout.Y_AXIS));
    
    final JToolBar toolBar = new JToolBar();
    toolBar.setFloatable(false);
    toolBar.add(this.createNewBagButton);
    toolBar.add(this.openBagButton);
    toolBar.add(this.validateBagButton);
    this.validateBagButton.setEnabled(false); //shouldn't be available until the bag has been saved
    toolBar.add(this.checkAgainstProfileButton);
    this.checkAgainstProfileButton.setEnabled(false); //shouldn't be available until the bag has been saved
    toolBar.add(this.saveBagButton);
    toolBar.add(Box.createHorizontalGlue());
    toolBar.add(this.ignoreHiddenFilesCheckbox);
    toolbars.add(toolBar);
    
    final JToolBar bagStatus = new JToolBar();
    bagStatus.setFloatable(false);
    bagStatus.add(this.isValidLabel);
    bagStatus.add(Box.createHorizontalGlue());
    bagStatus.add(this.isCompleteLabel);
    bagStatus.add(Box.createHorizontalGlue());
    bagStatus.add(this.isProfileCompliantLabel);
    toolbars.add(bagStatus);
    
    frame.add(toolbars, BorderLayout.NORTH);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    resetBagStatus();
    
    //TODO implement application logic here...
    switch(e.getActionCommand()) {
      case "Create New Bag" -> System.err.println(e.getActionCommand());
      case "Open Bag" -> System.err.println(e.getActionCommand());
      case "Validate" -> System.err.println(e.getActionCommand());
      case "Check Against Profile" -> System.err.println(e.getActionCommand());
      case "Save Bag" -> System.err.println(e.getActionCommand());
      case "Ignore Hidden Files" -> System.err.println(e.getActionCommand());
      default -> System.err.println("no matching case for: " + e.getActionCommand()); 
    }
  }

  @Override
  public void tableChanged(TableModelEvent e) {
    resetBagStatus();
  }

  @Override
  public void treeNodesChanged(TreeModelEvent e) {
    resetBagStatus();
  }

  @Override
  public void treeNodesInserted(TreeModelEvent e) {
    resetBagStatus();
  }

  @Override
  public void treeNodesRemoved(TreeModelEvent e) {
    resetBagStatus();
  }

  @Override
  public void treeStructureChanged(TreeModelEvent e) {
    resetBagStatus();
  }
  
  private void resetBagStatus() {
    this.isValidLabel.setText(BagState.VALID_UNKNOWN.getLabel());
    this.isCompleteLabel.setText(BagState.COMPLETE_UNKNOWN.getLabel());
    this.isProfileCompliantLabel.setText(BagState.PROFILE_COMPLIANT_UNKNOWN.getLabel());
  }
}
