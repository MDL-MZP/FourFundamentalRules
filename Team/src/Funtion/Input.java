package Funtion;

import java.util.Scanner;

/**
 * @author Mazin
 * switch case
 */
public class Input {
    public static int num;
    public static int in;

    /**    输出基本信息    */
    public static void inputBase(){
        Scanner sc = new Scanner(System.in);
        String[] s1;
        String str;
        print();
        while(num == 0) {
            System.out.println("\n请输入-n命令");
            str = sc.nextLine().trim();
            s1 = str.split(" +");
            if ("-n".equals(s1[0]) && s1.length > 1) {
                num = Integer.parseInt(s1[1]);
                break;
            } else {
                System.out.println("输入错误,请重新输入");
            }
        }
        while(in == 0) {
            System.out.println("\n请输入-r命令");
            str = sc.nextLine().trim();
            s1 = str.split(" +");
            if ("-r".equals(s1[0]) && s1.length > 1) {
                in = Integer.parseInt(s1[1]);
                break;
            }else{
                System.out.println("输入错误,请重新输入");
            }
        }
        System.out.println("---------------------");
        System.out.println("请打开Exercise.txt文件，并在这里答题");
        System.out.println("注：请在答案前注明序号并加“.”，否则不作答案录入，\n    且同一题号不同答案视为修改。答题顺序不做要求");
    }

    private static void print(){
        System.out.println("【自动生成四则运算器欢迎您！】");
        System.out.println("请输入合格的命令行：");
        System.out.println("-n x: 生成指定数目的题目x");
        System.out.println("-r x: 指定题目中数值的范围，[0,x)");
    }

    /**    扩展    */
    public static boolean inputExpand(){
        System.out.println("做完题后是否进行答案校对？");
        System.out.println("正确命令：-e <exercisefile> -a <answerfile>，退出输入exit");
        System.out.println("-e 指定你答题的题目文件，-a 指定比较的答案文件");
        System.out.println("注：校对的结果和正确率在Grade.txt文件.");
        Scanner sc = new Scanner(System.in);
        String[] s;
        boolean tag;
        while(true) {
            String st = sc.nextLine().trim();
            s = st.split(" ");
            if("exit".equals(s[0])){
                tag = false;
                break;
            }
            if (("-a".equals(s[0]) && "Answer.txt".equals(s[1]) && "-e".equals(s[2]) && "Exercise.txt".equals(s[3])) ||
               ("-a".equals(s[2]) && "Answer.txt".equals(s[3]) && "-e".equals(s[0]) && "Exercise.txt".equals(s[1]))) {
                tag = true;
                break;
            }
            System.out.println("输入有误，请重新输入");
        }
        return tag;
    }

    /**     获取用户答案  */
    public static String[] usersAnswer(){
        Scanner sc = new Scanner(System.in);
        String[] s1 = new String[Input.num];
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < Input.num;){
            String line = sc.nextLine();
            if(sb.length() != 0) {
                sb.delete(0, sb.length());
            }
            int j = 0;
            //  题目序号
            while(j < line.length() && line.charAt(j) != '.'){
                sb.append(line.charAt(j++));
            }
            if(j == line.length()){
                continue;
            }
            int index = Integer.parseInt(sb.toString());
            sb.delete(0,sb.length());
            //  序号的答案
            while(++j < line.length()){
                sb.append(line.charAt(j));
            }
            if(s1[index - 1] == null) {
                s1[index - 1] = sb.toString().trim();
                i++;
            }else{
                s1[index - 1] = sb.toString().trim();
            }
        }
        return s1;
    }
}
