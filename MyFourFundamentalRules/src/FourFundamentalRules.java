/**        主函数        */

import Funtion.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * @author   Mazin and Uncle-Drew  🌼
 * 主类
 */
public class FourFundamentalRules{

    public static void main(String[] args) throws Exception {
        File f1 = new File("e:/develop/theme/FourFundamentalRules/Exercise.txt");
        File f2 = new File("e:/develop/theme/FourFundamentalRules/Answer.txt");
        File f3 = new File("e:/develop/theme/FourFundamentalRules/Grade.txt");

        FileIO.createOrFlush(f1);
        FileIO.createOrFlush(f2);
        FileIO.createOrFlush(f3);

        boolean tag;


        // tag 做个标记让用户选择是否输出正确率
        Input.inputBase();

        //  输出表达式，等待用户作答
        CreateCorrectExpression2.outCorrectExpression(Input.num,Input.in,f1,f2);

        // 用户答案获取
        String[] s1 = Input.usersAnswer();

        tag = Input.inputExpand();

        // 如果要输出正确率，输入命令

        if (tag) {
            System.out.println("及时检查自己的作业哦");
            //  写入文件
            Accuracy.result(s1,f3);
        }else{
            System.out.println("感谢您的使用");
        }
    }

}
