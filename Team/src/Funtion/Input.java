package Funtion;

import java.util.Scanner;

/**
 * @author Mazin
 * 输入
 */
public class Input {
    public static int num;
    public static int limits;

    /**
     *   输入基本信息，输出一些指导信息
     */
    public static void inputBase(){
        Scanner sc = new Scanner(System.in);
        String[] s1;
        String str;
        print();
        t:
        // 表达式数目num没被赋值不结束
        while(num == 0) {
            System.out.println("\n请输入-n命令");
            str = sc.nextLine().trim();
            s1 = str.split(" +");
            // 要求必须“-n "，后面再加东西
            if ("-n".equals(s1[0]) && s1.length > 1) {
                // for循环 要求必须输入数字
                for (int j = 0; j < s1[1].length(); j++) {
                    boolean tag = s1[1].charAt(j) >= 48 && s1[1].charAt(j) <= 57;
                    if (!tag) {
                        System.out.println("请勿输入不合规范的字符");
                        continue t;
                    }
                }
                num = Integer.parseInt(s1[1]);
                break;
            }
            System.out.println("输入错误,请重新输入");
        }
       // 表达式数值范围limits不被赋值不结束
        s:
        while(limits == 0) {
            System.out.println("\n请输入-r命令");
            str = sc.nextLine().trim();
            s1 = str.split(" +");
            if ("-r".equals(s1[0]) && s1.length > 1) {
                // for循环 要求必须输入数字
                for (int j = 0; j < s1[1].length(); j++) {
                    boolean tag = s1[1].charAt(j) >= 48 && s1[1].charAt(j) <= 57;
                    if (!tag) {
                        System.out.println("请勿输入不合规范的字符");
                        continue s;
                    }
                }
                limits = Integer.parseInt(s1[1]);
                break;
            }
            System.out.println("输入错误,请重新输入");
        }
        System.out.println("---------------------");
        System.out.println("【请在控制台答题！】");
        System.out.println("真分数的输入请严格按照：“整数'分子/分母”形式输入！ ");
    }

    private static void print(){
        System.out.println("         👍❤ 自动生成四则运算器欢迎您！❤👍");
        System.out.println("-------------------------------------------------------");
        System.out.println("请输入如以下合格的命令行：");
        System.out.println("-n x: 生成指定数目的题目数x");
        System.out.println("-r x: 指定题目中数值的范围，[0,x)");
        System.out.println("最后生成的练习、答案（Exercise.txt、Answer.txt）文件在当前目录下！");
    }

    /**
     *  扩展输入内容
     */
    public static boolean inputExpand(){
        System.out.println("做完题后是否进行答案校对？");
        System.out.println("正确命令：-e <exercisefile> -a <answerfile>，退出输入exit");
        System.out.println("-e 指定你答题的题目文件，-a 指定比较的答案文件");
        System.out.println("例如：-e Exercise.txt -a Answer.txt");
        System.out.println("注：校对的结果和正确率在Grade.txt文件.");
        Scanner sc = new Scanner(System.in);
        String[] s;
        boolean tag;
        while(true) {
            String st = sc.nextLine().trim();
            s = st.split(" ");
            // 选择退出，不校对
            if("exit".equals(s[0])){
                tag = false;
                break;
            }
            // 正确输入，校对
            else if(s.length > 3) {
                boolean flag = ("-a".equals(s[0]) && "Answer.txt".equals(s[1]) && "-e".equals(s[2]) && "Exercise.txt".equals(s[3])) ||
                        ("-a".equals(s[2]) && "Answer.txt".equals(s[3]) && "-e".equals(s[0]) && "Exercise.txt".equals(s[1]));
                if (flag) {
                    tag = true;
                    break;
                }
            }
                System.out.println("输入有误，请重新输入");
        }
        return tag;
    }

    /**
     *    获取用户答案
     * @return 返回用户答案的String数组
     */
    public static String[] usersAnswer(){
        String[] ques = CreateCorrectExpression.getQuestions();
        Scanner sc = new Scanner(System.in);
        // 获取控制台用户答案
        String[] useranswer = new String[Input.num];
        StringBuffer sb = new StringBuffer();
        x:
        for(int i = 0; i < Input.num;){
            System.out.print(i+1 + " . " + ques[i]);
            //  获取用户的输入，是否为答案待处理
            String line = sc.nextLine();
            // 输入为空，视为不回答问题
            if(line.isEmpty()){
                System.out.println("请勿跳过作答！");
                continue;
            }
            // 输入的应该是规范的答案，不能有字母或者除 ' 、/ 外的字符
            for(int j = 0; j < line.length(); j++){
                boolean tag = (line.charAt(j) >= 47 && line.charAt(j) <= 57) || line.charAt(j) == '\'' || line.charAt(j) == '/';
                if(!tag){
                    System.out.println("请勿输入不合规范的字符");
                    continue x;
                }
            }
            //  排除上面所有的情况，那么输入的就是答案
            useranswer[i] = line;
            i++;
        }
        return useranswer;
    }
}
