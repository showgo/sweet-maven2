package org.sweet.frameworks.foundation.util.string;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 * @filename:
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public class IndexedString implements Indexable,CharSequence,IMatchable,Serializable {
	private static final long serialVersionUID=2057941132496469364L;
	/* 考虑线程安全 */
	private StringBuffer stringBuffer=null;
	private List<Integer> list=new Vector<Integer>();

	/**
	 * 构造函数
	 * @param string 源字串
	 * @param target 待查找并索引的字串
	 * @throws NullStringException
	 */
	public IndexedString(String string,String target) throws NullStringException{
		if(StringUtil.isNotEmpty(string)) {
			this.stringBuffer=new StringBuffer(string);
		}else{
			throw new NullStringException("Source string is null or empty: "+string);
		}
		if(StringUtil.isNotEmpty(target)) {
			this.indexesOf(target);
		}else{
			throw new NullStringException("Target string is null or empty: "+target);
		}
	}

	/**
	 * 获取目标字串的所有索引
	 * @param string 索引字符串
	 * @return
	 */
	private List<Integer> indexesOf(String string){
		if(this.stringBuffer.indexOf(string)==-1) {
			return list;
		}
		int index=0;
		int step=string.length();
		String temp=this.stringBuffer.substring(0);
		while(temp.length()>0){
			int pos=temp.indexOf(string);
			if(pos!=-1) {
				index=index+pos;
				list.add(index);
				pos+=step;
				index+=step;
				temp=temp.substring(pos);
			}else{
				break;
			}
		}
		return list;
	}

	/**
	 * 获得索引结果
	 */
	public List<Integer> indexes(){
		return list;
	}

	/**
	 * 获取string在目标字符串中出现的第seque次的索引值
	 * @param seque seque=1,2,3...
	 * @return
	 */
	public int indexOf(int seque){
		return this.list.get(seque>0 ? (seque-1) : 0);
	}

	public CharSequence subSequence(int start,int end){
		return this.stringBuffer.subSequence(start,end);
	}

	public String substring(int start){
		return this.stringBuffer.substring(start);
	}

	public String substring(int start,int end){
		return this.stringBuffer.substring(start,end);
	}

	public char charAt(int index){
		return this.stringBuffer.charAt(index);
	}

	public int length(){
		return this.stringBuffer.length();
	}

	public void replace(int index,String object){
		replace(index,index+1,object);
	}

	public void replace(int start,int end,String object){
		this.stringBuffer.replace(start,end,object);
	}

	public String toString(){
		return this.stringBuffer.toString();
	}
}
