package org.sweet.frameworks.foundation.util.string;

import java.util.List;

/**
 * @filename:
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public interface Indexable {
	/**
	 * 获得索引结果
	 * @return
	 */
	public List<Integer> indexes();

	/**
	 * 获取string在目标字符串中出现的第seque次的索引值
	 * @param seque seque=1,2,3...
	 * @return
	 */
	public int indexOf(int seque);

	public String substring(int start);

	public String substring(int start,int end);

	public void replace(int index,String object);

	/**
	 * Replaces the characters in a substring of this sequence with characters in the specified {@code String}. The substring
	 * begins at the specified {@code start} and extends to the character at index {@code end - 1} or to the end of the sequence
	 * if no such character exists. First the characters in the substring are removed and then the specified {@code String} is
	 * inserted at {@code start}. (This sequence will be lengthened to accommodate the specified String if necessary.)
	 * @param start The beginning index, inclusive.
	 * @param end The ending index, exclusive.
	 * @param object String that will replace previous contents.
	 */
	public void replace(int start,int end,String object);
}
