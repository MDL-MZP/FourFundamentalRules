package Funtion;

import java.io.File;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Mazin and Uncle-Drew
 *
 *  最终输出正确的表达式
 *
 */
public class CreateCorrectExpression {

    private static String[] questions = new String[Input.num];
    private static String[] answers = new String[Input.num];

    public static String[] getQuestions(){
        return questions;
    }

    public static String[] getAnswers(){
        return answers;
    }

    /**
     * 生成一条表达式
     * @param limits  数值范围限制
     * @return 一条初始表达式
     */
    public static String createExpression(int limits){
        StringBuffer expression = new StringBuffer();
        // 4个运算符随机选择
        String character = "＋-×÷";
        int n;
        char c;
        String s = "";
        int charNum = 0;
        Random r = new Random();
        // 表达式字符个数charNum 1,2,3随机
        charNum = r.nextInt(3) + 1;
        for(int countCharNum = 0;countCharNum < charNum; countCharNum++) {
            // 生成1个数 , 范围  [0,in)
            n = r.nextInt(limits);
            //   拿到符号
            c = character.charAt(r.nextInt(character.length()));
            //   每一趟都是  数字和运算符连接
            expression.append(String.valueOf(n) + c);
        }
        // 表达式出来后末尾是运算符,需要在加一个数字
        n = r.nextInt(limits);
        expression.append(n);
        // 加括号
        expression = addBrackets(expression);
        return expression.toString();
    }

    /**
     *  筛选正确算术表达式，写入文件Exercise.txt，并做出答案，写入文件Answer.txt
     * @param num    生成的表达式数目
     * @param limits  表达式中数值的范围
     * @param exercise  练习文件
     * @param answer    答案文件
     * @throws Exception
     */
    public static void outCorrectExpression(int num, int limits, File exercise,File answer) throws Exception{
        int i = 0;
        Date d = new Date();
        Screen screen = new Screen();
        //粗略+精细筛选
        x:
        while (i != num){
            //生成的未判断表达式
            String exps = createExpression(limits);
            if (!screen.roughScreen(exps).equals("false")){
                questions[i] = exps + " =";
                i++;//最后i等于num
            }else {
                continue x;
            }
        }
        // 正确的表达式 写入练习文件
        FileIO.write(exercise,questions);
        // 做出答案
        for(int j = 0;j < num ;j++){
            answers[j] = CorrectAnswer.answer(questions[j]);
        }
        // 写入答案文件
        FileIO.write(answer,answers);
    }

    /**
     *   对整个表达式，将(1)这种括号将数字包含的情况彻底删除
     * @param expression 表达式的StringBuffer类型
     */
    private static void standard(StringBuffer expression){
        //  从1开始,length-2结束,是因为,如果0,length-1是数字,那么,不可能有()包住的情况
        for(int i = 1; i < expression.length()-1; i++){
            int l = 1,r = 1;
            // 数字
            boolean tag = expression.charAt(i) >= 48 && expression.charAt(i) <= 57;
            if(tag) {
                //  l定位到数字左端
                l = leftMove(expression, i);
                // 数字最左端是开头，变为1
                if(l == 0){
                    l = 1;
                }
                //  r定位到数字右端
                r = rightMove(expression, i);
                //  数字左边为“（”  同时右边为“)”
                if (expression.charAt(l - 1) == '(' && expression.charAt(r + 1) == ')') {
                    expression.deleteCharAt(l - 1);
                    // 已经删除了一个 ( ,原先在i+1处的 ) 现在在 i 处
                    expression.deleteCharAt(r);
                }
                // 经过上面的修改或移位，i可以跳过循环的一些过程
                i = r;
            }
        }
    }

    /**
     *  判断是否删除掉首尾的括号
     * @param expression 表达式的StringBuffer类型
     * @return 返回表达式去除无用首尾括号的StringBuffer类型
     */
    private static StringBuffer deleteHeadAndTail(StringBuffer expression){
        boolean tag3 = expression.charAt(0)=='(' && expression.charAt(expression.length()-1)==')';
        // 0下标和尾下标为括号
        if(tag3){
            StringBuffer temp = new StringBuffer(expression);
            temp.deleteCharAt(0);
            temp.deleteCharAt(temp.length()-1);
            boolean judge1 = !temp.toString().contains("(") && !temp.toString().contains(")");
            boolean judge2 = temp.toString().indexOf("(")<temp.toString().indexOf(")");
            // 如果删除完首尾的括号后没有括号了，为了预防多个括号都在首尾
            if(judge1){
                expression = temp;
            }
            // 删除完首尾括号还有括号，并且剩下的括号满足左括号在右括号前
            else if(judge2){
                expression = temp;
            }
        }
        return expression;
    }

    /**
     *   在数字、括号、运算符周围增加空格
     * @param expression 表达式的StringBuffer类型
     */
    private static void addBlank(StringBuffer expression) {
        String str = "＋-×÷()";
        for (int i = 0; i < expression.length(); i++) {
            boolean tag = str.contains(String.valueOf(expression.charAt(i)));
            // 找到运算符或者括号
            if(tag){
                // 不是在末尾，那么在后面1位插入空格
                if(i != expression.length() - 1) {
                    expression.insert(++i, " ");
                }
            }
            // 不是运算符，就只能是数字
            else{
                // 右移
                i = rightMove(expression,i);
                // 不是在末尾，那么在后面1位插入空格
                if(i != expression.length() - 1) {
                    expression.insert(++i, " ");
                }
            }
        }
    }

    /**
     *   添加左括号的移位, 多位数需要移动到数的最左边添加括号
     * @param expression 表达式的StringBuffer类型
     * @param leftIndex  待定左括号的下标
     * @return  返回应该插入左括号的下标
     */
    private static int leftMove(StringBuffer expression,int leftIndex) {
        while (expression.charAt(leftIndex) >= 48 && expression.charAt(leftIndex) <= 57) {
            // 下标-1 左移
            if(leftIndex != 0){
                leftIndex -= 1;
            }
            // 数字最左端到开头
            else{
                return 0;
            }
        }
        // 跳出循环，找到第一个非数字的下标，+1回去最后一位数字的下标
        return leftIndex+1;
    }

    /**
     *   添加右括号的移位，多位数需要移动到数的最右边添加括号
     * @param expression 表达式的StringBuffer类型
     * @param rightIndex 待定右括号的下标
     * @return 返回应该插入右括号的下标
     */
    private static int rightMove(StringBuffer expression,int rightIndex){
        while(expression.charAt(rightIndex) >= 48 && expression.charAt(rightIndex) <= 57){
            // 下标+1 右移
            if(rightIndex != expression.length() - 1) {
                rightIndex += 1;
            }
            // 数字最右端到末尾
            else{
                return expression.length()-1;
            }
        }
        // 跳出循环，找到第一个非数字的下标，-1回去最后一位数字的下标
        return rightIndex-1;
    }

    /**
     *    加括号并对括号进行正确判断
     * @param expression 表达式的StringBuffer类型
     * @return 增添了正确括号的表达式的StringBuffer类型
     */
    private static StringBuffer addBrackets(StringBuffer expression){
        int i = 0;
        int leftIndex;
        Random r = new Random();
        // i用来控制括号的个数，且固定加入两个括号
        while(i < 2) {
            // 挑选数字，在左边加括号
            while (true) {
                // 随机下标
                leftIndex = r.nextInt(expression.length());
                // 是数字，就在数字左边加 (
                boolean tag1 = expression.charAt(leftIndex) >= 48 && expression.charAt(leftIndex) <= 57;
                leftIndex = leftMove(expression,leftIndex);
                // 插入（
                if (tag1) {
                    expression.insert(leftIndex, "(");
                    break;
                }
            }
            // 挑选数字，在右边加括号
            while (true) {
                //  相匹配的右括号 ) , 必须在左括号 ( 右边，这个随机数能满足
                int rightIndex = r.nextInt(expression.length() - leftIndex) + leftIndex;
                boolean flag = true;
                //  index>t ,是为了保证 ) 在 ( 右边, 由于在原数字左边 插入了( ,那么该数字的下标比原来多 1
                boolean tag2 = expression.charAt(rightIndex) >= 48 && expression.charAt(rightIndex) <= 57 && rightIndex > leftIndex;
                if (tag2) {
                    // 下标右移到数字最右端
                    rightIndex = rightMove(expression,rightIndex);
                    // 最左和最右不会出现“第二个括号中 ( 左边是 ( 同时 即将插入的 ) 的右边也是 )”的情况
                    if(rightIndex != expression.length() - 1 && leftIndex != 0) {
                        //  如果第二个括号中 ( 左边是 ( 同时 即将插入的 ) 的右边也是 ) 那么,不插入,且删除第二个 (
                        flag = expression.charAt(rightIndex + 1) != ')' || expression.charAt(leftIndex - 1) != '(';
                    }
                    // 不属于“第二个括号中 ( 左边是 ( 同时 即将插入的 ) 的右边也是 )”的情况
                    if (flag) {
                        //  插入时index+1, ) 放入数字右边
                        expression.insert(rightIndex + 1, ")");
                        break;
                    } else {
                        //  遇到了 ((1+2))+3 类似的情况
                        expression.deleteCharAt(leftIndex);
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
