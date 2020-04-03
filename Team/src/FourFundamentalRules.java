/**        主函数        */

import Funtion.Accuracy;
import Funtion.CreateCorrectExpression;
import Funtion.FileIO;
import Funtion.Input;

import java.io.File;
import java.util.Random;

/**
 * @author   Mazin and Uncle-Drew  🌼
 * 主类
 */
public class FourFundamentalRules {

    public static void main(String[] args) throws Exception {
        File file = new File("");
        File f1 = new File(file.getAbsolutePath()+"\\Exercise.txt");
        File f2 = new File(file.getAbsolutePath()+"\\Answer.txt");
        File f3 = new File(file.getAbsolutePath()+"\\Grade.txt");
        FileIO.createOrFlush(f1);
        FileIO.createOrFlush(f2);
        //  输入一些基本信息，如要生成的表达式数目num，和表达式的数值范围limits
        Input.inputBase();
        //  输出表达式，等待用户作答，num：表达式数目，limits：表达式的数值范围
        long l1 = System.currentTimeMillis();
        CreateCorrectExpression.outCorrectExpression(Input.num, Input.limits,f1,f2);
        long l2 = System.currentTimeMillis();
        System.out.println("生成" + Input.num + "道题一共需要" + (l2-l1) + "ms");
        // 如果要输出正确率，输入正确命令，表示输出自己答题情况
        boolean tag = Input.inputExpand();
        int i = 1;
        // 只有输入 exit 才能退出
        while(tag) {
            // 用户答案获取
            String[] useranswers = FileIO.read(f1);
            FileIO.createOrFlush(f3);
            System.out.println("及时到Grade.txt文件检查自己的作业哦");
            System.out.println("【第" + i++ + "次校对】");
            System.out.println("---------------------------------\n");
            //  写入文件
            Accuracy.result(useranswers,f3);
            tag = Input.inputExpand();
        }
        System.out.println("感谢您的使用");
    }
}
