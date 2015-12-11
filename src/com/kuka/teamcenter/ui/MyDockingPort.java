package com.kuka.teamcenter.ui;

import org.flexdock.docking.DockingManager;
import org.flexdock.docking.DockingPort;
import org.flexdock.docking.defaults.DefaultDockingPort;
import org.flexdock.docking.defaults.DefaultDockingStrategy;
import org.flexdock.docking.defaults.StandardBorderManager;
import org.flexdock.plaf.common.border.ShadowBorder;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Created by cberman on 12/10/2015.
 */
public class MyDockingPort extends DefaultDockingPort {
    static{
        initStatic();
    }
    private static void initStatic(){
        DockingManager.setDockingStrategy(MyDockingPort.class,new DockingStrategy());
    }

    public MyDockingPort(){
        this(new ShadowBorder());
    }

    public MyDockingPort(Border portletBorder){
        super();
        if(portletBorder!=null){
            setBorderManager(new StandardBorderManager(portletBorder));
        }
    }

    protected JTabbedPane createTabbedPane(){
        JTabbedPane tabbed = super.createTabbedPane();
        tabbed.putClientProperty("jgoodies.embeddedTabs",Boolean.TRUE);
        return tabbed;
    }

    private static class DockingStrategy extends DefaultDockingStrategy{
        protected DockingPort createDockingPortImpl(DockingPort base){
            return new MyDockingPort();
        }
    }
}
