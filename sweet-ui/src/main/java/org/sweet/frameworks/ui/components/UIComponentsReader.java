package org.sweet.frameworks.ui.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.sweet.frameworks.foundation.resource.ResourceReader;

/**
 * UIComponentsReader
 * @filename:UIComponentsReader
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public class UIComponentsReader {
	protected static Map<String,Map<String,List<Attribute>>> mapper=new ConcurrentHashMap<String,Map<String,List<Attribute>>>();

	/**
	 * 加载配置
	 * @param defaultComponentsConfigXml
	 */
	@SuppressWarnings("unchecked")
	protected static void loadConfigurations(String defaultComponentsConfigXml){
		Document docu=ResourceReader.readXML(defaultComponentsConfigXml);
		if(null!=docu){
			Element root=docu.getRootElement();
			List<Element> components=root.elements("component");
			for(Element component:components){
				Map<String,List<Attribute>> map=new HashMap<String,List<Attribute>>();
				/* 将组件注册name单独存放,便于获取 */
				map.put("component-name",getComponentName(component,AttributeScope.PROTECTED));
				/* protected:将属性id,xtype,class单独存放,便于获取 */
				map.put("component-protected",getProtectedAttributes(component,AttributeScope.PROTECTED));
				/* public */
				map.put("component-public",getPublicAttributes(component,AttributeScope.PUBLIC));
				mapper.put(component.elementTextTrim("component-type"),map);
			}
		}
	}

	private static List<Attribute> getComponentName(Element component,AttributeScope scope){
		List<Attribute> list=new ArrayList<Attribute>();
		Element componentType=component.element("component-type");
		if(null!=componentType){
			Attribute name=new Attribute();
			name.setScope(scope);
			name.setName(componentType.getTextTrim());
			name.setDefaultValue(componentType.getTextTrim());
			name.setValue(componentType.getTextTrim());
			name.setGenerator(Generator.NONE);
			name.setRequired(false);
			list.add(name);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private static List<Attribute> getProtectedAttributes(Element component,AttributeScope scope){
		List<Attribute> list=new ArrayList<Attribute>();
		if(null!=component){
			List<Element> attributes=component.elements("attribute");
			for(Element attribute:attributes){
				Attribute attr=new Attribute();
				attr.setScope(scope);
				attr.setName(attribute.attributeValue("name"));
				attr.setDefaultValue("null".equalsIgnoreCase(attribute.attributeValue("defaultValue")) ? null : attribute
					.attributeValue("defaultValue"));
				attr.setGenerator("auto".equalsIgnoreCase(attribute.attributeValue("generator")) ? Generator.AUTO : Generator.CUSTOM);
				attr.setRequired(Boolean.valueOf(attribute.attributeValue("required")).booleanValue());
				list.add(attr);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private static List<Attribute> getPublicAttributes(Element component,AttributeScope scope){
		List<Attribute> list=new ArrayList<Attribute>();
		if(null!=component){
			Element componentPublic=component.element("component-public");
			if(null!=componentPublic){
				List<Element> attributes=componentPublic.elements("attribute");
				for(Element attribute:attributes){
					Attribute attr=new Attribute();
					attr.setScope(scope);
					attr.setName(attribute.attributeValue("name"));
					attr.setDefaultValue("null".equalsIgnoreCase(attribute.attributeValue("defaultValue")) ? null : attribute
						.attributeValue("defaultValue"));
					attr.setGenerator(Generator.CUSTOM);
					attr.setRequired(Boolean.valueOf(attribute.attributeValue("required")).booleanValue());
					list.add(attr);
				}
			}
		}
		return list;
	}
}