package Funtion;

import java.io.File;
import java.util.Random;

/**
 *
 * @author Mazin and Uncle-Drew
 *
 *  最终输出正确的表达式
 *
 */
public class CreateCorrectExpression2 {
    private static String[] questions = new String[10000];
    private static String[] answers = new String[10000];


    public static String[] getQuestions(){
        return questions;
    }

    public static String[] getAnswers(){
        return answers;
    }

       /**     生成一条表达式       */
    public static String createExpression(int in){
        StringBuffer expression = new StringBuffer();
        // 4个运算符随机选择
        String character = "＋-×÷";
        int t;
        char c;
        int charNum = 0;
        Random r = new Random();
        // 表达式字符个数charNum 1,2,3随机
        charNum = r.nextInt(3) + 1;
        for(int countCharNum = 0;countCharNum < charNum; countCharNum++) {
            //    生成1个数 , 范围  [0,in)
            t = r.nextInt(in);
            //   拿到符号
            c = character.charAt(r.nextInt(character.length()));
            //   每一趟都是  数字和运算符连接
            expression.append(String.valueOf(t) + c);
        }
        // 表达式出来后末尾是运算符,需要在加一个数字
        t = r.nextInt(in);
        expression.append(t);
        // 加括号
        expression = addBrackets(expression);
        return expression.toString();
    }

    /**    筛选正确算术表达式，写入文件1 ，并做出答案，写入文件2  */
    public static String[] outCorrectExpression(int num, int in, File Exercise,File Answer) throws Exception{

        String correctExpression = "";
        String exps = "";
        int i = 0;
        Screen screen = new Screen();
        //粗略筛选
        x:
        while (i != num){
            //生成的未判断表达式
            exps = createExpression(in);
            if (!screen.roughScreen(exps).equals("false")){
                questions[i] = exps + " =";
                i++;//最后i等于num
            }else {
                continue x;
            }
        }

        // 正确的表达式 写入练习文件
        FileIO.write(Exercise,questions);
//        for(int j = 0; j < questions.length && questions[j]!=null;j++) {
//            System.out.println(questions[j]);
//        }
        //精细筛选

        // 做出答案
        for(int j = 0;j < num ;j++){
            answers[j] = CorrectAnswer.answer(questions[j]);
        }
//        for(int j = 0; j < answers.length && answers[j]!=null;j++) {
//            System.out.println(answers[j]);

        // 写入答案文件
        FileIO.write(Answer,answers);
        return questions;
    }

    /**         将(1)这种情况彻底删除    (12) */
    private static void standard(StringBuffer expression){
        //  从1开始,length-2结束,是因为,如果0,length-1是数字,那么,不可能有()包住的情况
        for(int i = 1; i < expression.length()-1; i++){
            int l = 1,r = 1;
            // 数字
            boolean tag = expression.charAt(i) >= 48 && expression.charAt(i) <= 57;
            if(tag) {
                //  数字左端
                l = leftMove(expression, i);
                if(l == 0){
                    l = 1;
                }
                //  数字右端
                r = rightMove(expression, i);
                //  数字左边 (    同时     右边 )
                if (expression.charAt(l - 1) == '(' && expression.charAt(r + 1) == ')') {
                        expression.deleteCharAt(l - 1);
                    // 已经删除了一个 ( ,原先在i+1处的 ) 现在在 i 处
                    expression.deleteCharAt(r);

                }
                i = r;
            }
        }
    }

    /**    判断是否删除掉首尾的括号       */
    private static StringBuffer deleteHeadAndTail(StringBuffer expression){
        boolean tag3 = expression.charAt(0)=='(' && expression.charAt(expression.length()-1)==')';
        if(tag3){
            StringBuffer temp = new StringBuffer(expression);
            temp.deleteCharAt(0);
            temp.deleteCharAt(temp.length()-1);
            boolean judge1 = !temp.toString().contains("(") && !temp.toString().contains(")");
            boolean judge2 = temp.toString().indexOf("(")<temp.toString().indexOf(")");
            if(judge1){
                expression = temp;
            }else if(judge2){
                expression = temp;
            }
        }
        return expression;
    }

    /**     增加空格        */
    private static void addBlank(StringBuffer expression) {
        String str = "＋-×÷()";
        for (int i = 0; i < expression.length(); i++) {
            boolean tag = str.contains(String.valueOf(expression.charAt(i)));
            if(tag){
                if(i != expression.length() - 1) {
                    expression.insert(++i, " ");
                }
            }else{
                i = rightMove(expression,i);
                if(i != expression.length() - 1) {
                    expression.insert(++i, " ");
                }
            }
        }
    }

    /**      添加左括号的移位, 多位数需要移动到数的最左边添加括号    */
    private static int leftMove(StringBuffer expression,int t) {
        while (expression.charAt(t) >= 48 && expression.charAt(t) <= 57) {
            if(t != 0){
                t -= 1;
            }else{
                return 0;
            }
        }
        return t+1;
    }

    /**      添加右括号的移位，多位数需要移动到数的最右边添加括号    */
    private static int rightMove(StringBuffer expression,int index){
        while(expression.charAt(index) >= 48 && expression.charAt(index) <= 57){
            if(index != expression.length() - 1) {
                index += 1;
            }else{
                return expression.length()-1;
            }
        }
        return index-1;
    }

    /**     加括号并对括号进行正确判断         */
    private static StringBuffer addBrackets(StringBuffer expression){
        // 随机决定要不要加括号 , 且只加入一个括号, 加括号概率好低这个随机数,我怀疑你在演我
        int i = 0;
        int t;
        Random r = new Random();
        // i用来控制加括号的个数
        while(i < 2) {
            // 挑选数字加括号
            while (true) {
                t = r.nextInt(expression.length());
                // 在数字左边加 (
                boolean tag1 = expression.charAt(t) >= 48 && expression.charAt(t) <= 57;
                t = leftMove(expression,t);
                if (tag1) {
                    expression.insert(t, "(");
                    break;
                }
            }
            while (true) {
                //  相匹配的右括号 ) , 必须在左括号 ( 右边
                int index = r.nextInt(expression.length() - t) + t;
                boolean flag = true;
                //  index>t ,是为了保证 ) 在 ( 右边, 由于在原数字左边 插入了( ,那么该数字的下标就+1,
                boolean tag2 = expression.charAt(index) >= 48 && expression.charAt(index) <= 57 && index > t;
                if (tag2) {
                    index = rightMove(expression,index);
                    // 排除((1+2))+3 类似的情况,第二个括号才会出现
           // 若是第一个括号,在临界值, 以防越界,也不需要(())的判断
           //  如果第二个括号中 ( 左边是 ( 同时 即将插入的 ) 的右边也是 ) 那么,不插入,删除第二个 (
                    if(index != expression.length() - 1 && t != 0) {
                        flag = expression.charAt(index + 1) != ')' || expression.charAt(t - 1) != '(';
                    }
     // index是找到的数字的下标, index+'1' 是右括号的下标,'1'是一个数字单元, t是左括号的下标
                    if (/*index != t + 1 &&*/ flag) {
                        //  插入时index+1, ) 放入数字右边
                        expression.insert(index + 1, ")");
                        break;
                    } else {
   // 如果 index = t+1 说明 ( 放在数字左边, 而 ) 就在数字右边,括号内只有一个数字,不选择插入,把上面的 ( 删除
   //  如果是 index+1为 ( , t-1 为 ) ,那么就是遇到了 ((1+2))+3 类似的情况
                        expression.deleteCharAt(t);
                        break;
                    }
                }
            }
            //  排除首尾括号无用的情况
            expression = deleteHeadAndTail(expression);
            i++;
        }
        // 除去(112)类似的情况
        standard(expression);
        // 增加空格
        addBlank(expression);
        return expression;
    }

}
