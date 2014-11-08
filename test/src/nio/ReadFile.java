package nio;
import java.io.*;  
import java.nio.*;  
import java.nio.channels.*;  
 
/**
 * 
* @author lizhs
* @date 2014-10-26 上午11:47:30 
* @version V1.0   
* @Description: TODO(用一句话描述该文件做什么)
* <pre>
* 在前面我们说过，任何时候读取数据，都不是直接从通道读取，而是从通道读取到缓冲区。所以使用NIO读取数据可以分为下面三个步骤： 
1. 从FileInputStream获取Channel 
2. 创建Buffer 
3. 将数据从Channel读取到Buffer中
下面是一个简单的使用NIO从文件中读取数据的例子：
* </pre>
 */
public class ReadFile {  
    static public void main( String args[] ) throws Exception {  
        FileInputStream fin = new FileInputStream("G:\\test\\a.txt");  
          
        // 获取通道  
        FileChannel fc = fin.getChannel();  
          
        // 创建缓冲区  
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
          
        // 读取数据到缓冲区  
        fc.read(buffer);  
          
        buffer.flip();  
          
        while (buffer.remaining()>0) {  
            byte b = buffer.get();  
            System.out.print(((char)b));  
        }  
          
        fin.close();  
    }  
}  