/**
 * 
 */
package org.sweet.frameworks.database.envir;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.io.Resources;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * DBEnvironments
 * @filename:DBEnvironments
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public final class DBEnvironments {
	private static Map<String,DBEnvironment> environmentsMap=new ConcurrentHashMap<String,DBEnvironment>();

	/**
	 * 通过配置文件加载数据库环境
	 * @param configXML
	 */
	@SuppressWarnings("unchecked")
	public static void loadEnvironments(String configXML){
		Document doc=null;
		try{
			URL url=Resources.getResourceURL(configXML);
			if(null!=url){
				SAXReader reader=new SAXReader();
				doc=reader.read(url);
			}
		}catch(DocumentException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		if(null!=doc){
			Element environments=doc.getRootElement().element("environments");
			String defaultId=environments.attributeValue("default");
			if(null!=environments){
				List<Element> envirs=environments.elements("environment");
				for(Element envir:envirs){
					DBEnvironment dbEnvir=new DBEnvironment();
					/* transactionManager properties */
					Element transactionManager=envir.element("transactionManager");
					dbEnvir.setTransactionType(transactionManager.attributeValue("type"));
					List<Element> transactionProperties=transactionManager.elements("property");
					if(null!=transactionProperties){
						for(Element property:transactionProperties){
							dbEnvir.setTransactionProperties(property.attributeValue("name"),property.attributeValue("value"));
						}
					}
					/* dataSource properties */
					Element dataSource=envir.element("dataSource");
					dbEnvir.setDataSourceType(dataSource.attributeValue("type"));
					List<Element> dataSourceProperties=dataSource.elements("property");
					if(null!=dataSourceProperties){
						for(Element property:dataSourceProperties){
							dbEnvir.setDataSourceProperties(property.attributeValue("name"),property.attributeValue("value"));
						}
					}
					/* id,type,default */
					dbEnvir.setId(envir.attributeValue("id"));
					dbEnvir.setType(dbEnvir.getDataSourceProperties().get("driver"));
					dbEnvir.setDefault(dbEnvir.getId().equals(defaultId) ? true : false);
					environmentsMap.put(dbEnvir.getId(),dbEnvir);
				}
			}
		}
	}

	/**
	 * 返回所有数据库环境配置
	 * @return
	 */
	public static Map<String,DBEnvironment> getEnvironments(){
		if(null!=environmentsMap){
			return environmentsMap;
		}
		return null;
	}

	/**
	 * 通过指定数据库环境名称返回数据库环境
	 * @param dbName
	 * @return
	 */
	public static DBEnvironment getEnvironment(String dbName){
		return environmentsMap.get(dbName);
	}

	/**
	 * 获得默认数据库环境
	 * @return
	 */
	public static DBEnvironment getDefaultEnvironment(){
		for(Map.Entry<String,DBEnvironment> entry:environmentsMap.entrySet()){
			if(entry.getValue().isDefault()){
				return entry.getValue();
			}
		}
		return null;
	}
}
