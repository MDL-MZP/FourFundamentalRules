package Funtion;

import java.util.Stack;

public class IsRepeatExpression {
    private Ruler ruler = new Ruler();

    /**
     *
     * @param expression 传入表达式
     *  @param targetArray 由于后缀表达式在计算后缀表达式时会用到，这里直接传入后缀表达式减少再次用表达式求后缀的重复.
     *
     * 完成从后缀表达式构造查重表达式的过程(主要是运用堆栈的做法，基本和计算后缀表达式一样的做法).
     * 算法描述大致如下：例如原式为:3*(1+2) +5   其后缀为 1 2 + 3 * 5 +
     * 以后缀的长度循环，遇到数字压栈，遇到字符连续出栈两个数字字符，出栈后将“#”压入数字栈，这个字符只起占位的作用，
     * 方便代码编写.循环结束后即可得到查重表达式
     *
     */

    public void getisrepeatExpressionArray(String[] targetArray, Expression expression){
        String topStr=  null;//栈顶的字符串
        int isrepeatArrayLength = 0;//用于表示查重表达式的长度
        Stack<String> stack = new Stack<String>();
        int lenth = ruler.getTrueLength(targetArray);//获得后缀表达式的真实长度
        String tempStr = null;
        for (int i = 0; i < lenth; i++) {// 字符串的长度超过一，代表这是数字则压栈
            if (targetArray[i].length() > 1||targetArray[i].equals("#")) {
                stack.push(targetArray[i]);
            } else {// 代表这是运算符
                expression.getisrepeatExprssion()[isrepeatArrayLength++] = targetArray[i];//加入到查重表达式中
                topStr = stack.pop();
                if(!topStr.equals("#")){
                    expression.getisrepeatExprssion()[isrepeatArrayLength++] = topStr;//加入到查重表达式中
                }
                topStr = stack.pop();
                if(!topStr.equals("#")){
                    expression.getisrepeatExprssion()[isrepeatArrayLength++] = topStr;//加入到查重表达式中
                }
                stack.push("#");
            }
        }
    }

    /**
     *
     *
     * @param exp1 要查重的表达式一
     * @param exp2 要查重的表达式二
     * @return 这两表达式是否重复
     */
    public Boolean isrepeatExression(Expression exp1, Expression exp2){
        String [] isrepeatStrArray1 = exp1.getisrepeatExprssion();
        String [] isrepeatStrArray2 = exp2.getisrepeatExprssion();
        int arrayLenth1 = ruler.getTrueLength(isrepeatStrArray1); //获得表达式一查重数组的真实长度
        int arrayLenth2 = ruler.getTrueLength(isrepeatStrArray2);//获得表达式二查重数组的真实长度
        if(arrayLenth1!=arrayLenth2) return false;// 若数组长度不相等，则表达式一定不同.
        if(ruler.arrayToString(isrepeatStrArray1).equals(ruler.arrayToString(isrepeatStrArray2))) return true; //如果查重数组完全一致则是为重复数组.
        if(isrepeatStrArray1[0].equals("+")||isrepeatStrArray1[0].equals("*")){//只有加或乘的情况才可能出现 交换左右操作数当做重复的表达式
            String temp = isrepeatStrArray1[1]; //交换首个符号之后的两个数字
            isrepeatStrArray1[1] = isrepeatStrArray1[2];
            isrepeatStrArray1[2] = temp;
        }
        //若交换后相等则也为重复.
        if(ruler.arrayToString(isrepeatStrArray1).equals(ruler.arrayToString(isrepeatStrArray2))) return true; //如果查重数组完全一致则是为重复数组.
        return false;
    }
}
