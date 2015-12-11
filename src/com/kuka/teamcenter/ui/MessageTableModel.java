package com.kuka.teamcenter.ui;

import com.kuka.teamcenter.util.LogUtil;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.Vector;

/**
 * Created by cberman on 12/7/2015.
 */
@SuppressWarnings("unchecked")
public class MessageTableModel extends AbstractTableModel {
    public static final String DATE_COLUMN="Date";
    public static final String SENDER_COLUMN="Sender";
    public static final String MESSAGE_COLUMN="Message";
    protected static final int DATE_INDEX=0;
    protected static final int SENDER_INDEX=1;
    protected static final int MESSAGE_INDEX=2;
    public static final int HIDDEN_INDEX = 3;
    private static final String TAG= LogUtil.getLogTag(MessageTableModel.class);
    private String[] columnNames={"Date","Sender","Message"};
    protected Vector dataVector;
    public MessageTableModel(){
        dataVector= new Vector();
    }

    public Message get(int idx){
        return (Message)dataVector.get(idx);
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public boolean hasEmptyRow() {
        if (dataVector.size() == 0) return false;
        Message message = (Message)dataVector.get(dataVector.size() - 1);
        return message.getDate().toString().trim().equals("") &&
                message.getSender().trim().equals("") &&
                message.getMessage().toString().trim().equals("");
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        Message message = (Message)dataVector.get(row);
        switch(column){
            case DATE_INDEX:
                message.setDate((Date)value);
                break;
            case SENDER_INDEX:
                message.setSender((String)value);
                break;
            case MESSAGE_INDEX:
                message.setMessage(value);
                break;
            default:
                LogUtil.Log(TAG,"invalid index");
                break;

        }
//        super.setValueAt(aValue, rowIndex, columnIndex);
        fireTableCellUpdated(row,column);
    }

    @Override
    public int getRowCount() {
        return dataVector.size();
    }
    @Override
    public Class getColumnClass(int column){
        switch(column){
           case DATE_INDEX:
                return Date.class;
            case MESSAGE_INDEX:
                return Object.class;
            default:
                return String.class;
        }
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if (rowIndex>=dataVector.size())
            return new Object();
        Message message = (Message)dataVector.get(rowIndex);
        switch(columnIndex){
            case DATE_INDEX:
                return message.getDate();
            case SENDER_INDEX:
                return message.getSender();
            case MESSAGE_INDEX:
                return message.getMessage();
            default:
                return new Object();
        }
    }
    public void clear(){
        dataVector.clear();
        addEmptyRow();
        fireTableDataChanged();
    }
    public void writeMessage(String sender,Object message){
        Message msg = new Message(sender,message);
        dataVector.add(msg);
        fireTableRowsInserted(
                dataVector.size() - 1,
                dataVector.size() - 1);
    }
    public void addEmptyRow() {
        dataVector.add(new Message());
        fireTableRowsInserted(
                dataVector.size() - 1,
                dataVector.size() - 1);
    }
}
