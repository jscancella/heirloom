package com.github.jscancella;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This class is responsible for setting up the main pane that contains the bag files on the left
 * and the bag metadata list on the right.
 */
public class MainPane extends JSplitPane {
  private static final long serialVersionUID = 1L;
  
  private final JScrollPane dataPane;
  private final JScrollPane metadataPane;
  private final DefaultMutableTreeNode rootNode;
  private final DefaultMutableTreeNode dataNode;
  private Object[][] metadata = new Object[][] { 
    {"foo", "bar"}, 
    {"sandwich", "ham"} 
  };

  public MainPane() {
    super();
    
    this.rootNode = new DefaultMutableTreeNode("BagRoot");
    this.dataNode = new DefaultMutableTreeNode("data");
    rootNode.add(dataNode);
    
    final JTree bagFilesTree = new JTree(rootNode);
    bagFilesTree.setEditable(true); //triple click to edit node name...
    dataPane = new JScrollPane(bagFilesTree);
    
    final JTable metadataTable = new JTable(metadata, new String[]{"Key", "Value"});
    
    metadataPane = new JScrollPane(metadataTable);
    metadataTable.setFillsViewportHeight(true);
    
    this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    this.add(dataPane, JSplitPane.LEFT);
    this.add(metadataPane, JSplitPane.RIGHT);
    this.setResizeWeight(0.25);
  }
}
