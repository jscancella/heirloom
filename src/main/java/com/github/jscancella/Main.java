package com.github.jscancella;

import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;

public class Main {
  public static void main(String[] args) {
    // Creating instance of JFrame
    final JFrame frame = new JFrame("Heirloom");
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // open fullsized
    frame.setSize(800, 800);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // TODO split this out into separate methods
    final JTree dataFiles = new JTree();
    final JTree tagFiles = new JTree();
    final JSplitPane dataPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, dataFiles, tagFiles);
    dataPane.setResizeWeight(0.5);
    
    String[] columnNames = {"First Name",
        "Last Name",
        "Sport",
        "# of Years",
        "Vegetarian"};
    Object[][] data = {
        {"Kathy", "Smith",
         "Snowboarding", 5, false},
        {"John", "Doe",
         "Rowing", 3, true},
        {"Sue", "Black",
         "Knitting", 2, false},
        {"Jane", "White",
         "Speed reading", 20, true},
        {"Joe", "Brown",
         "Pool", 10, false}
    };
    final JTable metadataTable = new JTable(data, columnNames);
    
    final JScrollPane metadataPane = new JScrollPane(metadataTable);
    metadataTable.setFillsViewportHeight(true);
    
    final JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dataPane, metadataPane);
    mainPane.setResizeWeight(0.25);
    frame.add(mainPane, BorderLayout.CENTER);

    final JToolBar toolBar = new JToolBar();
    toolBar.setFloatable(false);
    toolBar.add(new Button("button1"));
    toolBar.add(new Button("button2"));
    toolBar.add(new Button("button3"));
    frame.add(toolBar, BorderLayout.NORTH);

    // Setting the frame visibility to true
    frame.setVisible(true);
  }

}
