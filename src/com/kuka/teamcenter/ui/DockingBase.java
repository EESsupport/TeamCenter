package com.kuka.teamcenter.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.flexdock.docking.DockingConstants;
import org.flexdock.docking.DockingManager;
import org.flexdock.docking.defaults.DefaultDockingPort;

public class DockingBase extends JPanel {
	
	DefaultDockingPort port;
	public DockingBase(String title){
		setBorder(new LineBorder(Color.BLUE));
		add(new JLabel(title));		
		port = new DefaultDockingPort();
		port.setSingleTabAllowed(true);
		port.setPreferredSize(new Dimension(100,100));
		DockingManager.registerDockable(this);
		port.dock(this, DockingConstants.CENTER_REGION);
	}

	
	public DefaultDockingPort getPort(){
		return port;
	}
    private static void connectToDockingPort(JComponent component, DefaultDockingPort port) {
		DockingManager.registerDockable(component);
		port.dock(component, DockingConstants.CENTER_REGION);
	}
}
