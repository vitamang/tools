package com.wukong.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author zhang.ming
 * @date 2017年7月31日
 * 
 */
public class TestMixing {

	public static void main(String[] args) {
		/**
		 * 3969 V323-14010-9
	3969 V323-14010-84
	3969 P206-61026
	3969 P206-61026A
	TMD-MHIPJOE-E-0047 Re TMD-PJOE-MHI-T-0010 M120-SE-TQ-003
	TMD-MHIPJOE-E-0037 Re TMD-PJOE-MHI-T-0007 M120-SE-TQ-002
		 */
		WuKong w1 = new WuKong("3969 V323-14010-9");
		WuKong w2 = new WuKong("3969 V323-14010-84");
		WuKong w3 = new WuKong("3969 P206-61026");
		WuKong w4 = new WuKong("3969 P206-61026A");
		WuKong w5 = new WuKong("TMD-MHIPJOE-E-0047 Re TMD-PJOE-MHI-T-0010 M120-SE-TQ-003");
		WuKong w6 = new WuKong("TMD-MHIPJOE-E-0037 Re TMD-PJOE-MHI-T-0007 M120-SE-TQ-002");
		List<WuKong> l = new ArrayList<WuKong>();
		l.add(w4);
		l.add(w5);
		l.add(w6);
		l.add(w1);
		l.add(w2);
		l.add(w3);
//		BufferedReader buffer;
//		try {
//			buffer = new BufferedReader(new InputStreamReader(new FileInputStream("f:\\needsort.txt")));
//			String line;
//	        while((line = buffer.readLine()) != null) {
//	        	WuKong w = new WuKong(line);
//	        	l.add(w);
//	        }
//	        
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		MixingComparator c = new MixingComparator();
		Collections.sort(l, c);
//		StringBuffer bf = new StringBuffer();
//		for(WuKong w:l){
//			bf.append(w.getCode());
//			bf.append("\r\n");
//		}
//		try {
//			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream("f:\\needsort1.txt"),"UTF-8");
//			write.write(bf.toString());
//			write.close();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
