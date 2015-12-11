package com.kuka.teamcenter.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * Created by cberman on 12/10/2015.
 */
public class ProgressDialog extends JDialog implements ActionListener {

    private static final int TIMER_DELAY=50;

    private final JProgressBar progress = new JProgressBar() {
        @Override public void updateUI() {
            super.updateUI();
            setUI(new ProgressCircleUI());
            setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        }
    };
    private boolean isCircular=false;
    public void setIsCircular(boolean value){
        isCircular=value;
    }


    public void setMax(int value){progress.setMaximum(value);}
    public void setMin(int value){progress.setMinimum(value);}
    public void setValue(int value){progress.setValue(value);}
    private boolean isIndeterminate=true;
    private void setIsIndeterminate(boolean value){isIndeterminate=value;}


    public ProgressDialog(JFrame parent,String title,String message){
        super(parent,title,false);

        getContentPane().add(BorderLayout.CENTER, progress);
        progress.setForeground(new Color(0xAAFFAAAA));
        progress.setStringPainted(true);
        progress.setFont(progress.getFont().deriveFont(24f));

        Timer timer = new Timer(TIMER_DELAY, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
	            if(!isIndeterminate)
	                return;
	            int value=progress.getValue();
	            value=value>=100?0:value+1;

	            progress.setValue(value);
			}
        	
        });
        timer.start();
        setSize(350,350);
        setLocationRelativeTo(parent);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel buttonPane = new JPanel();

        JButton button = new JButton("Cancel");
        buttonPane.add(button);
        button.addActionListener(this);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();


    }

    public void showDialog(){

        setVisible(true);
    }
    public void showDialog(boolean circular){
        isCircular=circular;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    final class CircularProgress extends JPanel{
        private final JProgressBar progress = new JProgressBar(){
            @Override public void updateUI() {
                super.updateUI();
                setUI(new ProgressCircleUI());
                setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
            }
        };
    }
    class ProgressCircleUI extends BasicProgressBarUI {
        @Override public Dimension getPreferredSize(JComponent c) {
            Dimension d = super.getPreferredSize(c);
            int v = Math.max(d.width, d.height);
            d.setSize(v, v);
            return d;
        }
        @Override public void paint(Graphics g, JComponent c) {
            Insets b = progressBar.getInsets(); // area for border
            int barRectWidth  = progressBar.getWidth()  - b.right - b.left;
            int barRectHeight = progressBar.getHeight() - b.top - b.bottom;
            if (barRectWidth <= 0 || barRectHeight <= 0) {
                return;
            }

            // draw the cells
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(progressBar.getForeground());
            double degree = 360 * progressBar.getPercentComplete();
            double sz = Math.min(barRectWidth, barRectHeight);
            double cx = b.left + barRectWidth  * .5;
            double cy = b.top  + barRectHeight * .5;
            double or = sz * .5;
            double ir = or * .5; //or - 20;
            Shape inner = new Ellipse2D.Double(cx - ir, cy - ir, ir * 2, ir * 2);
            Shape outer = new Arc2D.Double(
                    cx - or, cy - or, sz, sz, 90 - degree, degree, Arc2D.PIE);
            Area area = new Area(outer);
            area.subtract(new Area(inner));
            g2.fill(area);
            g2.dispose();

            // Deal with possible text painting
            if (progressBar.isStringPainted()) {
                paintString(g, b.left, b.top, barRectWidth, barRectHeight, 0, b);
            }
        }
    }
}
