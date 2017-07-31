package com.wukong.tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author zhang.ming
 * @date 2017年7月27日
 * 
 */
public class MixingComparator implements Comparator<WuKong> {

	/**
	 * 3969 V323-14010-9
	3969 V323-14010-84
	3969 P206-61026
	3969 P206-61026A
	TMD-MHIPJOE-E-0047 Re TMD-PJOE-MHI-T-0010 M120-SE-TQ-003
	TMD-MHIPJOE-E-0037 Re TMD-PJOE-MHI-T-0007 M120-SE-TQ-002
	 */
	
	public int compare(WuKong w1, WuKong w2) {
		String str1 = w1.getCode();
		String str2 = w2.getCode();
		if( null == str1 )
			return -1;
		if( null == str2 )
			return 1;
		if( str1.equals( str2))
			return 0;
		char char1_0 = str1.charAt(0);
		char char2_0 = str2.charAt(0);
		if(char1_0 == char2_0)
		{
			List<Object> r1 = this.split(str1);
			List<Object> r2 = this.split(str2);
			int index = 0;
			while( r1.get(index).equals(r2.get(index)))
			{
				index++;
				if( index == r1.size() || index == r2.size())
					break;
			}
			if( index < r1.size() && index < r2.size() ) {
				if( r1.get(index) instanceof Integer )
				{
					if( r2.get(index) instanceof Integer )
						return Integer.compare((Integer)r1.get(index), (Integer)r2.get(index));
					else
						return -1;
				} else {
					if( r2.get(index) instanceof Integer )
						return 1;
					else {
						String s1 = (String)r1.get(index);
						String s2 = (String)r2.get(index);
						char[] c_s1 = s1.toCharArray();
						char[] c_s2 = s2.toCharArray();
						int c_index = c_s1.length;
						if( c_index > c_s2.length)
							c_index = c_s2.length;
						for( int i=0; i<c_index; i++ )
						{
							if(c_s1[i] == c_s2[i]){
								
							}
							else if(c_s1[i] < c_s2[i])
								return -1;
							else
								return 1;
						}
						return Integer.compare(c_s1.length, c_s2.length);
					}
				}
			} else {
				return Integer.compare(r1.size(), r2.size());
			}
			
		} else {
			if(char1_0 < char2_0)
				return -1;
			else
				return 1;
		}
	}

	private List<Object> split(String str)
	{
		List<Object> result = new ArrayList<Object>();
		char c0 = str.charAt(0);
		boolean isnumber = false;
		if( '0' <= c0 && c0 <= '9')
			isnumber = true;
		String temp = "";
		char[] cs = str.toCharArray();
		for(int i=0; i<cs.length; i++)
		{
			char c = cs[i];
			if( '0' <= c && c <= '9')
			{
				if(isnumber) {
					temp += c;
					if(i == cs.length -1)
						result.add(Integer.valueOf(temp));
				}
				else {
					result.add(temp);
					temp = ""+c;
					isnumber = true;
					if(i == cs.length -1)
						result.add(Integer.valueOf(temp));
				}
			} else {
				if(!isnumber) {
					temp += c;
					if(i == cs.length -1)
						result.add(temp);
				}
				else {
					result.add(Integer.valueOf(temp));
					temp = ""+c;
					isnumber = false;
					if(i == cs.length -1)
						result.add(temp);
				}
			}
		}
		return result;
	}
	
}
