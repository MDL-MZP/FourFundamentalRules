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

    /**         读取练习文件以获取用户的答案       */
    public static String[] read(File f) throws Exception{
        // 对一行的读取
        String[] line;
        int i = 0;
        String[] userAnswers = new String[CreateCorrectExpression2.getQuestions().length];
        //  对传入的 路径 读取文件
        FileReader readFile = new FileReader(f);
        BufferedReader br = new BufferedReader(readFile);
        String readLine = br.readLine();
        //  循环，一直读到文件结束
        while (readLine != null) {
            //System.out.println(readLine);
            //  一行表达式只有一个= ，分开后，右边便是用户答案。
            line = readLine.split("=");
            userAnswers[i++] = line[1].trim();
            readLine = br.readLine();
        }
        return userAnswers;
    }

    /**       将content写入文件      */
    public static void write(File fileName,String[] content) throws Exception{
        FileWriter fw = new FileWriter(fileName);
        for(int i = 0; i < content.length && content[i] != null; i++){
            fw.write(String.valueOf(i+1) + ". " + content[i]);
            fw.write("\n");
        }
        fw.close();
    }

    /**       创建文件或者清除文件内容          */
    public static void createOrFlush(File f) throws Exception{
        if (!f.exists()) {
            f.createNewFile();
        }
        FileWriter fw = new FileWriter(f);
        fw.write("");
        fw.flush();
        fw.close();
    }
}
