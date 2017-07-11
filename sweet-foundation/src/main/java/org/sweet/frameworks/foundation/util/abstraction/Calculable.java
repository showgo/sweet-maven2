package org.sweet.frameworks.foundation.util.abstraction;

/**
 * 可计算类接口
 * @filename:Calculable
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public interface Calculable<T> {
	public T add(Object object1,Object object2);

	public T sub(Object object1,Object object2);
}
