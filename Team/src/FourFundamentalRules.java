/**        主函数        */

import Funtion.Accuracy;
import Funtion.CreateCorrectExpression;
import Funtion.FileIO;
import Funtion.Input;

import java.io.File;

/**
 * @date 2020.03.31
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
        //  输入一些基本信息，如要生成的表达式数目num，和表达式的数值范围limits
        Input.inputBase();
        //  输出表达式，等待用户作答，num：表达式数目，limits：表达式的数值范围
        CreateCorrectExpression.outCorrectExpression(Input.num, Input.limits,f1,f2);
        // 用户答案获取
        String[] useranswers = Input.usersAnswer();
        // 如果要输出正确率，输入正确命令，表示输出自己答题情况
        boolean tag = Input.inputExpand();
        if (tag) {
            System.out.println("及时检查自己的作业哦");
            //  写入文件
            Accuracy.result(useranswers,f3);
        }else{
            System.out.println("感谢您的使用");
        }
    }

}
