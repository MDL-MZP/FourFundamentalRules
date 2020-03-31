/**        主函数        */

import Funtion.Accuracy;
import Funtion.CreateCorrectExpression;
import Funtion.FileIO;
import Funtion.Input;

import java.io.File;

/**
 * @author   Mazin and Uncle-Drew  🌼
 * 主类
 */
public class FourFundamentalRules {

    public static void main(String[] args) throws Exception {
        /**
         * 提要求：给用户说明怎么使用以及实现最后文件自动删除
         */
        File file = new File("");
        String file_path = file.getAbsolutePath();
        File f1 = new File(file_path+"\\Exercise.txt");
        File f2 = new File(file_path+"\\Answer.txt");
        File f3 = new File(file_path+"\\Grade.txt");

        FileIO.createOrFlush(f1);
        FileIO.createOrFlush(f2);
        FileIO.createOrFlush(f3);

        //代码规范
        boolean tag;


        // tag 做个标记让用户选择是否输出正确率
        Input.inputBase();

        //  输出表达式，等待用户作答
        //说明一下num是什么
        CreateCorrectExpression.outCorrectExpression(Input.num, Input.in,f1,f2);

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
