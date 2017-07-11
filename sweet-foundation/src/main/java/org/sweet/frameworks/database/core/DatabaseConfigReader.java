/**
 * 
 */
package org.sweet.frameworks.database.core;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.ibatis.io.Resources;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * DatabaseConfigReader
 * @filename:DatabaseConfigReader
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public final class DatabaseConfigReader {
	private Document document=null;
	private Element mappers=null;

	/**
	 * 构造函数
	 * @param configXML
	 * @throws IOException
	 */
	public DatabaseConfigReader(String configXML) throws IOException{
		try{
			this.document=new SAXReader().read(Resources.getResourceURL(configXML));
			this.mappers=this.document.getRootElement().element("mappers");
		}catch(DocumentException e){
			throw new IOException(e.getCause());
		}
	}

	/**
	 * 添加mapper资源
	 * @param resource
	 * @return
	 */
	public DatabaseConfigReader addMapperResource(String resource){
		if(null!=this.mappers){
			Element mapper=this.mappers.addElement("mapper");
			mapper.addAttribute("resource",resource);
		}
		return this;
	}

	/**
	 * 创建reader
	 * @return
	 */
	public Reader build(){
		return new StringReader(this.document.asXML());
	}
}
