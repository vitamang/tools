package com.wukong.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * 
 * @author zhang.ming
 * @date 2017年5月23日
 * 
 */
public class CopyFileUtil {
	
	private static final Long CHUNKSIZE = 50l * 1024 * 1024;
	
	private static final int BUFFER_SIZE = 60 * 1024;

	/**
	 * 
	 * @param from
	 * @param to
	 * @throws IOException 
	 */
	public static void copySmallFile(File from, File to) throws IOException
	{
		InputStream in = null ;
		OutputStream out = null ;
		try 
		{
			in = new BufferedInputStream( new FileInputStream(from));
			out = new BufferedOutputStream( new FileOutputStream(to));
			byte [] buffer = new byte [BUFFER_SIZE];
			int len = 0; 
			while ( ( len = in.read( buffer ) ) > 0 ) 
			{
				out.write( buffer, 0, len ); 
			}
		}
		finally  
		{
			if ( null != in ) { in.close(); } 
			if ( null != out ) { out.close();} 
		}
	}
	
	/**
	 * 
	 * 大文件拷贝，分成多次写入，每次50M
	 * 小于50M的不要调用此方法
	 * 
	 * @param from
	 * @param to
	 * @param filesize
	 * @throws IOException
	 */
	public static void copyLargeFile(File from, File to, Long filesize) throws IOException
	{
		System.out.println("File:"+to.getName()+"----"+filesize);
		int each = (int)Math.ceil( filesize.doubleValue() / CHUNKSIZE.doubleValue() );
		for( int i=0; i<each; i++ )
    	{
    		Long start = i*CHUNKSIZE;
    		Long end = (i+1)*CHUNKSIZE - 1l;
    		if( i == (each-1) )
    			end = filesize - 1l;
    		Long bufsize = Long.valueOf(end-start+1);
    		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(from));
			bis.skip(start);
			
			RandomAccessFile raf = new RandomAccessFile(to, "rw");
			System.out.println("Begin write File:"+to.getName()+"----"+start/1024/1024+"----to----"+end/1024/1024);
			raf.seek(start);
			
			int len = 0;
			byte[] buff = new byte [bufsize.intValue()];
			len = bis.read(buff);
			raf.write( buff, 0, len ); 
			raf.close();
			bis.close();
    	}
	}
	
	/**
	 * 
	 * 同服务器备份，可以选择验证MD5
	 * 如果拷贝文件到另外一台服务器，不建议使用此方法
	 * 因为验证拷贝文件的MD5相当于把文件重新下载回来
	 * @param from
	 * @param to
	 * @param filesize
	 * @throws IOException
	 */
	public static void copyLargeFileValidationMD5(File from, File to, Long filesize) throws IOException 
	{
		String md5from = FileValidationUtil.getLargeFileMD5(from);
//		System.out.println("md5from-"+md5from);
		copyLargeFile(from, to, filesize);
		String md5to = FileValidationUtil.getLargeFileMD5(to);
		while( !md5from.equals(md5to) )
		{
			copyLargeFile(from, to, filesize);
			md5to = FileValidationUtil.getLargeFileMD5(to);
		}
//		System.out.println("md5from-"+md5to);
	}
	
}
