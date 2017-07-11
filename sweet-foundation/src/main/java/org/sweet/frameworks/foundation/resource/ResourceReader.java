package org.sweet.frameworks.foundation.resource;

import java.io.InputStream;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

/**
 * 资源Reader
 * @filename:ResourceReader
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public final class ResourceReader {
	private ResourceReader(){
	}

	/**
	 * 读取XML文件并返回Document
	 * @param filePath 文件类路径(或者绝对路径)
	 * @return 配置文件的Document
	 */
	public static Document readXML(String filePath){
		try{
			/* xml资源对象(URL) */
			java.net.URL url=getResource(filePath);
			if(null!=url){
				SAXReader reader=new SAXReader();
				return reader.read(url);
			}
			/* xml文件对象(File) */
			java.io.File fi=new java.io.File(filePath);
			if(null!=fi&&fi.exists()){
				return readXML(fi);
			}
			/* xml文本(字符串) */
			Document docu=DocumentHelper.parseText(filePath);
			if(null!=docu){
				return docu;
			}
			throw new DocumentException("Destination resource not found: "+filePath);
		}catch(DocumentException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取XML文件并返回Document
	 * @param file 文件对象
	 * @return 配置文件的Document
	 */
	public static Document readXML(java.io.File file){
		try{
			if(null!=file){
				SAXReader reader=new SAXReader();
				return reader.read(file);
			}
			throw new DocumentException("Destination resource not found: "+file);
		}catch(DocumentException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得资源的输入流
	 * @param path 资源相对路径
	 * @return
	 */
	public static InputStream getResourceAsStream(String path){
		if(null!=path&&!"".equals(path)){
			return ResourceReader.class.getClassLoader().getResourceAsStream(path);
		}
		return null;
	}

	/**
	 * 获得资源的URL
	 * @param path 资源相对路径
	 * @return
	 */
	public static URL getResource(String path){
		if(null!=path&&!"".equals(path)){
			return ResourceReader.class.getClassLoader().getResource(path);
		}
		return null;
	}
}
