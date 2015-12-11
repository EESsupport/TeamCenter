package com.kuka.teamcenter.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by cberman on 12/4/2015.
 */
public class Button extends JButton {

    public Button(String text, ActionListener listener,int mnemonic,boolean enabled){
        super();
        setText(text);
        addActionListener(listener);
        setMnemonic(mnemonic);
        setEnabled(enabled);

    }
}
