package org.sweet.frameworks.foundation.util.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @Filename: ListWrapper
 * @Company:
 * @Author: wugz
 * @Create: 2017/7/31
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public class ListWrapper<T> {
	private List<T> list;

	public ListWrapper(){
		this(new ArrayList<T>());
	}

	/**
	 * 构造函数
	 * @param list
	 */
	public ListWrapper(List<T> list){
		this.list=list;
	}

	/**
	 * 添加元素
	 * @param obj
	 * @return
	 */
	public ListWrapper<T> push(T obj){
		this.list.add(obj);
		return this;
	}

	/**
	 * 列表连接
	 * @param separator
	 * @return
	 */
	public String join(String separator){
		StringBuilder builder=new StringBuilder();
		if(null!=this.list&&this.list.size()>0){
			builder.append(list.get(0));
			for(int e=1;e<list.size();e++){
				builder.append(separator);
				builder.append(list.get(e).toString());
			}
			return builder.toString();
		}
		return null;
	}
}
