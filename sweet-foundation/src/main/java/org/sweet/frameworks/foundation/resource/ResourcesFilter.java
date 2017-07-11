package org.sweet.frameworks.foundation.resource;

/**
 * 资源过滤器(ResourcesFilter)
 * @filename:ResourcesFilter
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-25
 * @modifyrecords:
 */
public abstract class ResourcesFilter {
	/**
	 * 设定资源是否满足过滤条件
	 * @param path 资源基础路径
	 * @param resource 资源绝对路径
	 * @return
	 */
	public abstract boolean accept(String path,String resource);
}
