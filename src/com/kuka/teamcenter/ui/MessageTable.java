package com.kuka.teamcenter.ui;

import com.kuka.teamcenter.util.LogUtil;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cberman on 12/7/2015.
 */
@SuppressWarnings("ALL")
public class MessageTable extends JPanel {
    private MessageTableModel tableModel;

    private static final String PARTIAL_ERROR = "PartialError";
    private static final String STATE = "State";

    private static final String SERVICE_REQUEST = "Service Request";
    private static final String SERVICE_RESPONSE = "Service Response";
    private static final String DELETE = "Delete";
    private static final String LOCAL_OBJECT_CHANGE="Local Object Change";
    JCheckBox stateCheckBox  =createCheckbox(STATE);

    JCheckBox partialErrorCheckbox  =createCheckbox(PARTIAL_ERROR);
    JCheckBox serviceRequestCheckBox = createCheckbox(SERVICE_REQUEST);
    JCheckBox serviceResponseCheckBox = createCheckbox(SERVICE_RESPONSE);
    JCheckBox deleteCheckbox = createCheckbox(DELETE);
    JCheckBox localObjectChangeCheckBox = createCheckbox(LOCAL_OBJECT_CHANGE);

    TableRowSorter<MessageTableModel> sorter;
    protected JTable table;
    protected JScrollPane scroller;
    public MessageTable(){
        initComponent();
    }


    private final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){
            if (e.getButton()==3){
                doPop(e);
            }else{
                super.mouseClicked(e);
            }
        }
    };

    private void doPop(MouseEvent e){

        JPopupMenu menu = new JPopupMenu();
        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.setMnemonic(KeyEvent.VK_C);
        clearItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                tableModel.clear();
            }

        });
        menu.add(clearItem);
        menu.show(e.getComponent(),e.getX(),e.getY());


    }



    private boolean senderMatch(String title,String value){
        return title.replaceAll(" ","").toUpperCase().equals(value.replaceAll(" ","").toUpperCase());
    }
    private void initComponent(){


        tableModel = new MessageTableModel();
        tableModel.addTableModelListener(new InteractiveTableModelListener());


        sorter = new TableRowSorter<>(tableModel);



        table = new JTable();
        table.setModel(tableModel);

        RowFilter<Object,Object> filter = new RowFilter<Object, Object>() {
            public boolean include(Entry entry){
                String value = entry.getValue(1).toString();
                if (senderMatch(STATE,value)){
                    return stateCheckBox.isSelected();
                }

                if (senderMatch(STATE,value)){
                    return stateCheckBox.isSelected();
                }

                if (senderMatch(SERVICE_REQUEST,value)){
                    return serviceRequestCheckBox.isSelected();
                }

                if (senderMatch(SERVICE_RESPONSE,value)){
                    return serviceResponseCheckBox.isSelected();
                }

                if (senderMatch(DELETE,value)){
                    return deleteCheckbox.isSelected();
                }
                if (senderMatch(LOCAL_OBJECT_CHANGE,value)){
                    return localObjectChangeCheckBox.isSelected();
                }


                return true;


            }
        };
        sorter.setRowFilter(filter);
        table.setRowSorter(sorter);


        TableModelListener l = new TableModelListener() {
            @Override
            public void tableChanged(final TableModelEvent e) {
                if (e.getType()==e.INSERT){
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            int viewRow=table.convertRowIndexToView(e.getFirstRow());
                            table.scrollRectToVisible(table.getCellRect(viewRow,0,true));
                        }
                    });

                }
            }
        };
        table.getModel().addTableModelListener(l);
        table.setSurrendersFocusOnKeystroke(true);

        if (!tableModel.hasEmptyRow()) {
            tableModel.addEmptyRow();
        }



        table.addMouseListener(mouseAdapter);
        scroller = new javax.swing.JScrollPane(table);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
        table.setAutoscrolls(true);

        setLayout(new BorderLayout());

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel,BoxLayout.Y_AXIS));



        sidePanel.add(partialErrorCheckbox);
        sidePanel.add(serviceRequestCheckBox);
        sidePanel.add(serviceResponseCheckBox);
        sidePanel.add(deleteCheckbox);
        sidePanel.add(stateCheckBox);
        sidePanel.add(localObjectChangeCheckBox);

        add(sidePanel,BorderLayout.WEST);
        add(scroller, BorderLayout.CENTER);
        TableColumn dateColumn=table.getColumnModel().getColumn(MessageTableModel.DATE_INDEX);
        TableColumn senderColumn=table.getColumnModel().getColumn(MessageTableModel.SENDER_INDEX);
        TableColumn messageColumn=table.getColumnModel().getColumn(MessageTableModel.MESSAGE_INDEX);

        Dimension tableSize =  Toolkit.getDefaultToolkit().getScreenSize();
        double pct = tableSize.getWidth()/100;
        int msgWidth=(int)(pct*80);
        int dateWidth=(int)(pct*10);
        int senderWidth=(int)(pct*10);
        dateColumn.setPreferredWidth(dateWidth);
        senderColumn.setPreferredWidth(senderWidth);
        messageColumn.setPreferredWidth(msgWidth);
        dateColumn.setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Message msg = tableModel.get(row);
                Color color=Color.black;
                if (msg.getSender().contains("Error")){
                    color = Color.red;
                }
                switch(msg.getSender()){

                    default:
                        break;
                }
                JLabel label = new JLabel(value.toString());
                label.setForeground(color);
                return label;
            }
        });
    }


    private JCheckBox createCheckbox(String text){
        final JCheckBox result = new JCheckBox(text);
        result.setSelected(true);
        result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sorter.sort();
            }
        });
        return result;
    }

    public void writeText(String sender,Object message){
        tableModel.writeMessage(sender,message);
    }
    public void addMessage(Message message){
        if (tableModel!=null&&tableModel.dataVector!=null)
        tableModel.dataVector.add(message);
    }
    public void highlightLastRow(int row){
        int lastRow=tableModel.getRowCount();

        int idx = (row==lastRow-1)?lastRow-1:row+1;
            table.setRowSelectionInterval(idx,idx);
    }



    class InteractiveRenderer extends DefaultTableCellRenderer{
        protected int interactiveColumn;
        public InteractiveRenderer(int interactiveColumn){
            this.interactiveColumn = interactiveColumn;
        }

        public Component getTableRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
            Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);

            if (column == interactiveColumn && hasFocus){
             highlightLastRow(row);
            }
            return c;
        }


    }

    public class InteractiveTableModelListener implements TableModelListener{

        private final String TAG = LogUtil.getLogTag(InteractiveTableModelListener.class);
        @Override
        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                LogUtil.Log(TAG,"row: " + row + " column: " + column);

                table.setColumnSelectionInterval(column + 1, column + 1);
                table.setRowSelectionInterval(row, row);
            }
        }
    }
}
