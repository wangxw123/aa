package com.reapal.inchannel.cmbcpayxm.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlUtil {
	/**
	 * 获取XML根节点
	 * @param xmlStr XML字符串
	 * */
	public static Element getRootElement(String xmlStr) throws Exception {
		StringReader xmlReader = new StringReader(xmlStr);
		InputSource xmlSource = new InputSource(xmlReader);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(xmlSource);
		return doc.getRootElement();
	}
	
	/**
	 * 解析XML报文
	 * @param xmlStr 带解析的XML字符串
	 * */
	public static Map<String, String> parseXML(String xmlStr) throws JDOMException, IOException {
		StringReader xmlReader = new StringReader(xmlStr);
		InputSource xmlSource = new InputSource(xmlReader);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(xmlSource);
		Element root = doc.getRootElement();
		List<Element> child = root.getChildren();
		Map<String, String> para = new HashMap<String, String>();
		parseEle(child, para);
		return para;
	}
	private static void parseEle(List<Element> element, Map<String, String> para) {
		for (Element el : element) {
			List<Element> childs = el.getChildren();
			if (childs.size() > 0) {
				parseEle(childs, para);
			} else {
				para.put(el.getName(), el.getText());
			}
		}
	}

    public static List<Map<String, String>> parseXMLToList(String xmlStr) throws JDOMException, IOException {
        StringReader xmlReader = new StringReader(xmlStr);
        InputSource xmlSource = new InputSource(xmlReader);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(xmlSource);
        Element root = doc.getRootElement();
        List<Element> child = root.getChildren();
        List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
        parseEle(child, retList);
        return retList;
    }
    private static void parseEle(List<Element> element, List<Map<String, String>> retList) {
        Map<String, String> paramMap = new HashMap<String, String>();
        for (Element el : element) {
            List<Element> childs = el.getChildren();
            if (childs.size() > 0) {
                parseEle(childs, retList);
            } else {
                paramMap.put(el.getName(), el.getText());
            }
        }
        if (paramMap.size() > 0) {
            retList.add(paramMap);
        }
    }

//	public static void main(String[] args) throws Exception {
//
//		String strXml = "<?xml version='1.0' encoding='UTF-8'?><Ans><Version>1.0</Version><SettDate>20150807</SettDate><TransTime>170620</TransTime><ReqSerialNo>2015080717085001</ReqSerialNo><ExecType>S</ExecType><ExecCode>000000</ExecCode><ExecMsg>交易成功</ExecMsg><MerId></MerId><OriReqSerialNo>2015550716554601</OriReqSerialNo><OriSettDate>20150807</OriSettDate><OriTransTime>165316</OriTransTime><OriPaySerialNo>2015080704205795</OriPaySerialNo><OriExecType>S</OriExecType><OriExecCode>000000</OriExecCode><OriExecMsg>交易成功</OriExecMsg><Resv></Resv></Ans>";
//
//		Element element = XmlUtil.getRootElement(strXml);
//
//		Map<String, String> para = XmlUtil.parseXML(strXml);
//
//		System.out.println(element);
//		System.out.println(para);
//
//
//	}
}
