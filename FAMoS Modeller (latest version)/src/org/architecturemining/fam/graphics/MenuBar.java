package org.architecturemining.fam.graphics;

import java.io.*;
import java.awt.event.*;
import javax.swing.*;

import org.architecturemining.fam.IO.ConsoleDemo;
import org.architecturemining.fam.IO.ReadXML;
import org.architecturemining.fam.IO.TraceExportXES;
import org.architecturemining.fam.model.FunctionalArchitectureModel;
import org.architecturemining.fam.model.Trace;


public class MenuBar {

	private Window window;
	
	public MenuBar(Window window) {
		this.window = window;
	}
	
    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenuItem menuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the Menu.
        JMenu menu = new JMenu("File");
        menu.getAccessibleContext().setAccessibleDescription(
                "Menu with options for current file");
        menuBar.add(menu);
		
        //A group of JMenuItems
        menuItem = new JMenuItem(new AbstractAction("Open FAM") {
			//opens a file chooser to select a file
			public void actionPerformed(ActionEvent e) {
			System.out.println("Open FAM");
			
			//Create a file chooser
			JFileChooser fc = new JFileChooser();
			
			int returnVal = fc.showOpenDialog(menu);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                FunctionalArchitectureModel fam = new FunctionalArchitectureModel();
                //This is the code to open the file.
                ReadXML.readXML(fam, file);
                ConsoleDemo.main(fam);
                System.out.println("Opening: " + file.getName() + ".");
                
                window.updateFAM(fam);
                
                //reset traces               
                window.traceList.clear();
                window.menuPanelComponents.clearTable();
                window.currentTrace = 0;
                window.traceList.add(new Trace());
                
            } else {
            	System.out.println("Open command cancelled by user.");
            }
			}
		});
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This action opens a fAM");
        menu.add(menuItem);
 
 
        menuItem = new JMenuItem(new AbstractAction("Save Traces") {
			//Opens a file chooser to save a file
			public void actionPerformed(ActionEvent e) {
			System.out.println("Save file");
			
			//Create and save file with the traces
			TraceExportXES.writeXES(window.traceList);
			}
		});
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This action saves the current file");
        menu.add(menuItem);
        
        
        /*menuItem = new JMenuItem(new AbstractAction("Import Traces") {
			//Imports a trace file
			public void actionPerformed(ActionEvent e) {
			System.out.println("Import Traces");
			window.menuPanelComponents.updateTable("name", window.currentTrace, 0);
			window.menuPanelComponents.updateTable("f1-f7", window.currentTrace, 1);
			window.currentTrace++;
			}
		});
		menuItem.setMnemonic(KeyEvent.VK_I);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This action closes the current file");
        menu.add(menuItem); */
		
		
        menu.addSeparator();
		menuItem = new JMenuItem(new AbstractAction("Close Program") {
			//Closes the current file 
			public void actionPerformed(ActionEvent e) {
			System.out.println("Close Program");
			System.exit(1);
			}
		});
		menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This action closes the current file");
        menu.add(menuItem);
		
        return menuBar;
    }
}
