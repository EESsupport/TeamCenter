package com.kuka.teamcenter.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.flexdock.docking.defaults.DefaultDockingPort;

public class DockingUtil {

    public static DefaultDockingPort createDockingPort() {
		DefaultDockingPort port = new DefaultDockingPort();
		port.setSingleTabAllowed(true);
		port.setPreferredSize(new Dimension(100, 100));
		return port;
	}
    
    public static JComponent createDockableComponent(String name) {
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.BLUE));
		panel.add(new JLabel(name));
		return panel;
	}
}
