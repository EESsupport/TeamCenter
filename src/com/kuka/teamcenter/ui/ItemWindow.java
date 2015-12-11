package com.kuka.teamcenter.ui;

import com.kuka.teamcenter.util.LogUtil;
import com.teamcenter.soa.client.model.strong.*;
import com.teamcenter.soa.exceptions.NotLoadedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by cberman on 12/8/2015.
 */
public class ItemWindow  extends JPanel{
    JList list;
    private static final String TAG= LogUtil.getLogTag(ItemWindow.class);


    DefaultListModel model;

    public void addItems(ArrayList items){
        model.clear();

        for(int i=0;i<items.size();i++){
            Object o = items.get(i);
            model.addElement(o);
        }

    }
    public ItemWindow(){
        setLayout(new BorderLayout());
        model= new DefaultListModel();
        list = new JList(model);


        list.addMouseListener(new ClearMouseAdapter(model));
        JScrollPane pane = new JScrollPane(list);
        list.setCellRenderer(new ItemRenderer());
        add(pane);
    }

    public void AddItem(){

    }

    class ItemRenderer extends JLabel implements ListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            String name = value.getClass().getSimpleName();

            getValue(name,value);

            return this;

        }

        private void getValue(String name,Object value){
            String result;
            String text="";
            try {
       if (value instanceof Item){
           Item i=(Item)value;
           LogUtil.Log(TAG,i);


               text=String.format("name:%s, last modified:%s",i.get_object_string(),new Date(i.get_last_mod_date().getTimeInMillis()));

       }else if (value instanceof ItemRevision){
           ItemRevision i=(ItemRevision)value;
           text=String.format("name:%s, last modified:%s",i.get_object_string(),new Date(i.get_last_mod_date().getTimeInMillis()));
           LogUtil.Log(TAG,i);
       }
            } catch (NotLoadedException e) {
                LogUtil.Log(TAG,"NotLoadedException error:"+e.getMessage());
            }
            if (!text.isEmpty()){
                result = String.format("type:%s, %s",name,text);
                setText(result);
                return;
            }

            switch(name){
                case "MEWorkarea":
                    text=getMEWorkArea(value);
                    break;
                case "Form":
                    text=getForm(value);
                    break;
                case "MEWorkareaRevision":
                    text=getMEWorkareaRevision(value);break;
                case "BOMView":
                    text=getBomView(value);
                    break;
                case "BOMView_Revision":
                    text=getBomViewRevision(value);break;
                case "DirectModel":
                    text=getDirectModel(value);break;
                default:
                    LogUtil.Log(TAG,"account for "+name);

                    break;
            }
            result = String.format("type:%s, %s",name,text);
            setText(result);

        }

        private String getDirectModel(Object value){
            DirectModel obj = (DirectModel)value;

            String result="";
            try {
                result = String.format("name:%s, last modified:%s",obj.get_object_string(),new Date(obj.get_last_mod_date().getTimeInMillis()));
            } catch (NotLoadedException e) {
                e.printStackTrace();
            }
            return result;
        }
        private String getMEWorkareaRevision(Object value){

            MEWorkareaRevision obj = (MEWorkareaRevision)value;

            String result="";
            try {
                result = String.format("name:%s, last modified:%s",obj.get_object_string(),new Date(obj.get_last_mod_date().getTimeInMillis()));
            } catch (NotLoadedException e) {
                e.printStackTrace();
            }
            return result;

        }

        private String getBomViewRevision(Object value){

            BOMView_Revision obj = (BOMView_Revision)value;
            String result="";
            try {
                result = String.format("name:%s, last modified:%s",obj.get_object_string(),new Date(obj.get_last_mod_date().getTimeInMillis()));
            } catch (NotLoadedException e) {
                e.printStackTrace();
            }
            return result;
        }
        private String getBomView(Object value){
            BOMView obj = (BOMView)value;
            String result="";
            try {
                result = String.format("name:%s, last modified:%s",obj.get_object_string(),new Date(obj.get_last_mod_date().getTimeInMillis()));
            } catch (NotLoadedException e) {
                e.printStackTrace();
            }
            return result;
        }
        private String getMEWorkArea(Object value){
            MEWorkarea obj = (MEWorkarea)value;
            String result="";
            try {
                result = String.format("name:%s, last modified:%s",obj.get_object_string(),new Date(obj.get_last_mod_date().getTimeInMillis()));
            } catch (NotLoadedException e) {
                e.printStackTrace();
            }
            return result;
        }

        private String getForm(Object value){
            Form obj = (Form)value;
            String result="";
            try {
                result = String.format("name:%s, last modified:%s",obj.get_object_string(),new Date(obj.get_last_mod_date().getTimeInMillis()));
            } catch (NotLoadedException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}
