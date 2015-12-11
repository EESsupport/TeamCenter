package com.kuka.teamcenter.ui;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 * Created by cberman on 12/9/2015.
 */
public class ClearMouseAdapter extends MouseAdapter {

    public ClearMouseAdapter(Vector vector){
        super();
        this.vector=vector;
    }

    public ClearMouseAdapter(DefaultListModel model){
        this.model=model;
    }
    private DefaultListModel model;
    private Vector vector;
    @Override
    public void mouseClicked(MouseEvent e){
        if (e.getButton()==3){
            doPop(e);
        }else{
            super.mouseClicked(e);
        }

    }

    private void doPop(MouseEvent e){
        JPopupMenu menu = new JPopupMenu();
        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.setMnemonic(KeyEvent.VK_C);
        clearItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                if (model!=null)
                    model.clear();
                if(vector!=null)
                    vector.clear();
            }

        });
        menu.add(clearItem);
        menu.show(e.getComponent(),e.getX(),e.getY());

    }
}
