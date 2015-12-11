package com.kuka.teamcenter.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by cberman on 12/10/2015.
 */
public class TestDialog extends JDialog implements ActionListener {

    public TestDialog(JFrame parent,String title,String message){
        super(parent, title, true);
        if (parent != null) {
            Dimension parentSize = parent.getSize();
            Point p = parent.getLocation();
            setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
        }
        JPanel messagePane = new JPanel();
        messagePane.add(new JLabel(message));
        getContentPane().add(messagePane);
        JPanel buttonPane = new JPanel();
        JButton button = new JButton("OK");
        buttonPane.add(button);
        button.addActionListener(this);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
    }
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
}
