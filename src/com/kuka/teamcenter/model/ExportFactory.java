package com.kuka.teamcenter.model;

import com.kuka.teamcenter.xml.XmlHelper;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.Date;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by cberman on 12/3/2015.
 */
public class ExportFactory {

    private static final String regexString="(<simulationInfo>[\\s\\S]*</simulationInfo>)";
    private static final String  TAGSTRING = "(<{0}\\s*ExternalI>d[\\s\\S]*</{0}>)";
    private static final Regex replaceRegex = new Regex(regexString);


    private Dictionary<String,List<ExternalIdImpl>> items;
    public Dictionary<String,List<ExternalIdImpl>> getItems(){return items;}
    private void setItems(Dictionary<String,List<ExternalIdImpl>> items){this.items=items;}


    public ExportFactory(String filename){

    }

    public void parseData(String filename) throws Exception {
        Date start = new Date(System.currentTimeMillis());

        String filename1 = filename;

        Data dataObject = XmlHelper.read(filename);
    }

}
