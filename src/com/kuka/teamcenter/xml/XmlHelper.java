package com.kuka.teamcenter.xml;

/**
 * Created by cberman on 12/3/2015.
 */
import com.kuka.teamcenter.model.Data;
import com.kuka.teamcenter.model.ExportFactory;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;


import com.kuka.teamcenter.model.Keys;
import com.kuka.teamcenter.model.PrLine;
import com.kuka.teamcenter.util.LogUtil;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
public class XmlHelper {
    private static final String TAG= LogUtil.getLogTag(XmlHelper.class);
    public static void write(ExportFactory factory, String filename)throws Exception{
        XMLEncoder encoder =new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
        encoder.writeObject(factory);
        encoder.close();
    }

    public static void parseNodes(NodeList nodeList,Node parent){

        for(int i=0;i<nodeList.getLength();i++){
            Node node = nodeList.item(i);
            parseNode(node,null);
        }
    }

    private static void parseData(Node node){
        Data mData = new Data();
        if (node.hasChildNodes()){
            NodeList children = node.getChildNodes();
            for(int i=0;i<children.getLength();i++){
                Node n=children.item(0).getNextSibling();
                if (n.getNodeName().equals("Objects")){
                    parseObjects(n,node);
                }else{
                    throw new RuntimeException("idk");
                }

            }
        }

    }
    private static void parseObjects(Node node,Node parent){
        String name = node.getNodeName();
        LogUtil.Log(TAG,name);

        NodeList nodeList=node.getChildNodes();
        for(int i=0;i<nodeList.getLength();i++){
            Node child = nodeList.item(i).getNextSibling();
            parseNode(child,node);
        }




    }
    public static void createParts(Node node){

    }

    public static void parseNode(Node node,Node parent){
        String name = node.getNodeName();
        LogUtil.Log(TAG,name);
        switch(name){
            case Keys.PrLine:
                if(node instanceof PrLine){
                    LogUtil.Log(TAG,name);
                }
                break;
        }
        if (name.equals("Objects")){
            parseObjects(node,parent);
        }
        if (node instanceof Data){
            parseData(node);
        }

        if (node.hasChildNodes()){
            NodeList nodeList=node.getChildNodes();
            parseNodes(nodeList,node);
        }


    }
    public static Data read(String filename) throws Exception{
        File file = new File(filename);
        if (!file.exists())
        {

        }
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();
            StringBuilder xmlStringBuilder = new StringBuilder();
            String name=doc.getDocumentElement().getNodeName();


            NodeList docs = doc.getElementsByTagName("Data");
            for(int i=0;i<docs.getLength();i++){
                Node node = docs.item(i);
                parseData(node);
            }

            // Iterating through the nodes and extracting the data.
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            parseNodes(nodeList,null);

        }catch (Exception e){
            e.printStackTrace();
        }

        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        Data data=(Data)decoder.readObject();
        decoder.close();
        return data;
    }
}
