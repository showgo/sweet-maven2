package org.sweet.frameworks.ui.components;

import java.util.Map;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 组件接口
 * @filename:
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public interface IComponent {
	/**
	 * 返回组件name
	 * @return
	 */
	public String getComponentName();

	/**
	 * 返回组件id
	 * @return
	 */
	public String getId();

	/**
	 * 返回组件xtype
	 * @return
	 */
	public String getXtype();

	/**
	 * 返回组件的属性class
	 * @return
	 */
	public String getStyleClass();

	/**
	 * 返回受保护属性定义集
	 * @return
	 */
	public Map<String,Attribute> getProtectedAttributes();

	/**
	 * 返回受保护属性定义集
	 * @return
	 */
	public Map<String,Object> getProtectedAttributesMap();

	/**
	 * 返回共有属性定义集
	 * @return
	 */
	public Map<String,Attribute> getPublicAttributes();

	/**
	 * 返回共有属性定义集
	 * @return
	 */
	public Map<String,Object> getPublicAttributesMap();

	/**
	 * 获得所有属性
	 * @return
	 */
	public Map<String,Attribute> getAttributes();

	/**
	 * 获得所有属性
	 * @return
	 */
	public Map<String,Object> getAttributesMap();

	/**
	 * 组件校验
	 * @return
	 * @throws RequiredAttributeException
	 */
	public boolean validate() throws RequiredAttributeException;
}
