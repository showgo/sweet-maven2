package org.sweet.frameworks.ui.components;

/**
 * 组件属性实体类
 * @filename:
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public final class Attribute {
	/**
	 * 作用域(protected/public/events/data_options)
	 */
	private AttributeScope scope=null;
	/**
	 * 属性名称
	 */
	private String name=null;
	/**
	 * 现值
	 */
	private Object value=null;
	/**
	 * 缺省值
	 */
	private Object defaultValue=null;
	/**
	 * 值生成器
	 */
	private Generator generator=null;
	/**
	 * 是否必须
	 */
	private boolean required=false;

	/**
	 * 构造函数
	 */
	public Attribute(){
	}

	/**
	 * 对象拷贝
	 * @param attribute
	 * @return
	 */
	public static Attribute wrap(Attribute attribute){
		Attribute attr=new Attribute();
		attr.scope=attribute.scope;
		attr.name=attribute.name;
		attr.defaultValue=attribute.defaultValue;
		attr.generator=attribute.generator;
		attr.required=attribute.required;
		return attr;
	}

	public AttributeScope getScope(){
		return scope;
	}

	public void setScope(AttributeScope scope){
		this.scope=scope;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name=name;
	}

	public Object getValue(){
		return value;
	}

	public void setValue(Object value){
		this.value=value;
	}

	public Object getDefaultValue(){
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue){
		this.defaultValue=defaultValue;
	}

	public Generator getGenerator(){
		return generator;
	}

	public void setGenerator(Generator generator){
		this.generator=generator;
	}

	public boolean isRequired(){
		return required;
	}

	public void setRequired(boolean required){
		this.required=required;
	}
}
