package com.kuka.teamcenter;

import com.kuka.teamcenter.ui.MainWindow;

import javax.swing.*;

//import groovy.swing.LookAndFeelHelper;
import org.flexdock.docking.DockingManager;
import org.flexdock.util.SwingUtility;

/**
 * Created by cberman on 12/2/2015.
 */
public class Project {

    private static boolean configureDocking(){
        DockingManager.setFloatingEnabled(false);
        return false;
    }
    public static void main(String[] args){
/* Use an appropriate Look and Feel */
        try {

           UIManager.LookAndFeelInfo[] feels= UIManager.getInstalledLookAndFeels();

            for(int i=0;i<feels.length;i++){
                System.out.println();
            }
           // UIManager.setLookAndFeel(feels[0]);
            UIManager.setLookAndFeel(feels[1].getClassName());
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        boolean loaded=configureDocking();
        /* Turn off metal's use bold fonts */
        //UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              MainWindow w = new MainWindow();

            }
        });
    }
}
