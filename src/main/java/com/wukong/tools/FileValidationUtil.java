package com.wukong.tools;
import org.apache.commons.codec.digest.DigestUtils;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.nio.MappedByteBuffer;  
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.util.zip.CRC32;  



/**
 * 
 * https://my.oschina.net/laigous/blog/106646
 * 
 * http://blog.csdn.net/mylmhcj/article/details/50616908
 * 
 * @author Administrator
 *
 */
public class FileValidationUtil {  
    /** 
     * 计算大文件 md5获取getMD5(); SHA1获取getSha1() CRC32获取 getCRC32() 
     */  
    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',  
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
    public static MessageDigest messagedigest = null;  
  
    /** 
     * 对一个文件获取md5值 
     *  
     * @return md5串 
     * @throws NoSuchAlgorithmException 
     */  
    public static String getMD5(File file) throws IOException,  
            NoSuchAlgorithmException {  
  
        messagedigest = MessageDigest.getInstance("MD5");  
        FileInputStream in = new FileInputStream(file);  
        FileChannel ch = in.getChannel();  
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,  
                file.length());  
        messagedigest.update(byteBuffer);
        ch.close();
        in.close();
        return bufferToHex(messagedigest.digest());  
    }  
  
     /**  
     * @param target 字符串 求一个字符串的md5值 
     * @return md5 value 
     */  
    public static String StringMD5(String target) {  
        return DigestUtils.md5Hex(target);  
    }  
      
    /*** 
     * 计算SHA1码 
     *  
     * @return String 适用于上G大的文件 
     * @throws NoSuchAlgorithmException 
     * */  
    public static String getSha1(File file) throws OutOfMemoryError,  
            IOException, NoSuchAlgorithmException {  
        messagedigest = MessageDigest.getInstance("SHA-1");  
        FileInputStream in = new FileInputStream(file);  
        FileChannel ch = in.getChannel();  
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0l,  
                file.length());  
        messagedigest.update(byteBuffer);  
        ch.close();
        in.close();
        return bufferToHex(messagedigest.digest());  
    }  
  
    /** 
     * 获取文件CRC32码 
     *  
     * @return String 
     * */  
    public static String getCRC32(File file) {  
        CRC32 crc32 = new CRC32();  
        // MessageDigest.get  
        FileInputStream fileInputStream = null;  
        try {  
            fileInputStream = new FileInputStream(file);  
            byte[] buffer = new byte[8192];  
            int length;  
            while ((length = fileInputStream.read(buffer)) != -1) {  
                crc32.update(buffer, 0, length);  
            }  
            return crc32.getValue() + "";  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            return null;  
        } catch (IOException e) {  
            e.printStackTrace();  
            return null;  
        } finally {  
            try {  
                if (fileInputStream != null)  
                    fileInputStream.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    public static String getMD5String(String s) {  
        return getMD5String(s.getBytes());  
    }  
  
    public static String getMD5String(byte[] bytes) {  
        messagedigest.update(bytes);  
        return bufferToHex(messagedigest.digest());  
    }  
  
    /** 
     * @Description 计算二进制数据 
     * @return String 
     * */  
    private static String bufferToHex(byte bytes[]) {  
        return bufferToHex(bytes, 0, bytes.length);  
    }  
  
    private static String bufferToHex(byte bytes[], int m, int n) {  
        StringBuffer stringbuffer = new StringBuffer(2 * n);  
        int k = m + n;  
        for (int l = m; l < k; l++) {  
            appendHexPair(bytes[l], stringbuffer);  
        }  
        return stringbuffer.toString();  
    }  
  
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {  
        char c0 = hexDigits[(bt & 0xf0) >> 4];  
        char c1 = hexDigits[bt & 0xf];  
        stringbuffer.append(c0);  
        stringbuffer.append(c1);  
    }  
  
    public static boolean checkPassword(String password, String md5PwdStr) {  
        String s = getMD5String(password);  
        return s.equals(md5PwdStr);  
    } 
    
    /**
     * 
     * @param inputFile
     * @return
     * @throws IOException
     */
    public static String getLargeFileMD5(File inputFile) throws IOException {
        // 缓冲区大小（这个可以抽出一个参数）
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
           // 拿到一个MD5转换器（同样，这里可以换成SHA1）
           MessageDigest messageDigest =MessageDigest.getInstance("MD5");
           // 使用DigestInputStream
           fileInputStream = new FileInputStream(inputFile);
           digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
           // read的过程中进行MD5处理，直到读完文件
           byte[] buffer =new byte[bufferSize];
           while (digestInputStream.read(buffer) > 0);
           // 获取最终的MessageDigest
           messageDigest= digestInputStream.getMessageDigest();
           // 拿到结果，也是字节数组，包含16个元素
           byte[] resultByteArray = messageDigest.digest();
           // 同样，把字节数组转换成字符串
           return bufferToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
           return null;
        } finally {
           try {
              digestInputStream.close();
           } catch (Exception e) {
           }
           try {
              fileInputStream.close();
           } catch (Exception e) {
           }
        }
     }
}  