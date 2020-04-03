package Funtion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author Mazin
 * 读写功能类
 */
public class FileIO {

    /**
     *   读取文件答案
     * @param f   要读取的文件对象
     * @return  返回用户的答案
     * @throws Exception
     */
     public static String[] read(File f) throws Exception{
         String[] answers = new String[Input.num];
         FileReader fr = new FileReader(f);
         BufferedReader br = new BufferedReader(fr);
         String line = br.readLine();
         int j =0;
         while(line != null){
             String[] sp = line.split("=");
             if(sp.length > 1){
                 StringBuffer s = new StringBuffer(sp[0]);
                 answers[Integer.parseInt(s.substring(0,s.indexOf("."))) - 1] = sp[1].trim();
             }
             line = br.readLine();
         }
         return answers;
     }

    /**
     *      将content写入文件
     * @param fileName 要写入的文件
     * @param content  要写入的内容
     * @throws Exception
     */
    public static void write(File fileName,String[] content) throws Exception{
        FileWriter fw = new FileWriter(fileName);
        for(int i = 0; i < content.length && content[i] != null; i++){
            // 写入序号和内容
            fw.write(String.valueOf(i+1) + ". " + content[i]);
            fw.write("\n");
        }
        fw.close();
    }

    /**
     *  创建文件或者清除文件内容
     * @param f  要创建或清楚内容的文件
     * @throws Exception
     */
    public static void createOrFlush(File f) throws Exception{
        if (!f.exists()) {
            f.createNewFile();
        }
        FileWriter fw = new FileWriter(f);
        // 清空文件
        fw.write("");
        fw.flush();
        fw.close();
    }
}
