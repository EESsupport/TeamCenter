package com.kuka.teamcenter.ui;

import java.awt.Dimension;

import javax.swing.JScrollPane;

import com.kuka.teamcenter.util.LogUtil;

public class TeamCenterPanel extends DockingBase{
	private MessageTable messageTable = new MessageTable();
	
	private static final String TAG=LogUtil.getLogTag(TeamCenterPanel.class);

	public TeamCenterPanel(){
		super("TeamCenter");
		
		add(new JScrollPane(messageTable));
		super.setPreferredSize(new Dimension(300,300));
	}
}
