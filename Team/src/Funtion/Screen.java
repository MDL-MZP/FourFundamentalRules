package Funtion;

/**
 *
 *核心思想用递归方法解决算术表达式的分解、判断
 * 注释中基础表达式是指一个运算符两个运算数子，例如："1 - 2"
 *@description: 筛选类，包含筛选表达式所使用的方法
 *@author: MDL
 *@time:  2020.3.31
 *
 */
public class Screen {

    static Screen screen = new Screen();

    /**
     * 粗略筛选
     * @param exps 算术表达式字符串，数字和符号用空格隔开
     * @return 返回字符串，如果为"false"则表明算术表达式不符合
     */
    public String roughScreen(String exps){
        String result = "";//存放结果字符串
        String[] exps_array = screen.splitExpression(exps);//得到分隔后的字符串数组
        if (exps_array.length == 1){//字符串数组只有一位表示为数字
            result = exps;//直接返回值
        }else if (exps_array.length == 3){//一个基础的算术表达式，例如："1 - 2"
            if (true == baseExpression(exps)){//判断是否符合基础规则
                result = screen.espressionResult(exps);//计算返回值
            }else{
                result = "false";//不符合基本规则返回"false"用于递归
            }
        }else if(exps.equals("false")){//递归时用于判断
            result = "false";
        }else {
            String left_exps = "";//左算术式
            String left_result = "";//左算术式结果
            String right_exps = "";//右算术式
            String right_result = "";//右算术结果
            int[] adr_array = screen.operator(exps);//获取算术表达式的运算符优先级，存放的为所在原字符串数组下标
            int adr = adr_array[adr_array.length-1];//获取当前算术运算符
            for (int i = 0;i <= adr-1;i++){//以当前算术运算符向左拼接表达式获取左表达式
                left_exps += exps_array[i] + " ";
            }
            left_exps = screen.deleteBracket(left_exps);//去括号
            for (int j = adr+1;j <= exps_array.length-1;j++){//以当前算术运算符向右拼接表达式获取右表达式
                right_exps += exps_array[j] + " ";
            }
            right_exps = screen.deleteBracket(right_exps);
            left_result = screen.roughScreen(left_exps);//左表达式递归
            right_result = screen.roughScreen(right_exps);//右表达式递归
            if (!left_result.equals("false") && !right_result.equals("false")){//左右子表达式都符合要求
                String new_exps;//把左结果和右结果以及运算符结合成新的运算式
                new_exps = left_result + " " + exps_array[adr]+ " "+ right_result;//生成新的基本表达式
                if (true == baseExpression(new_exps)) {//新的基本表达式是否符合基础要求
                    result = screen.espressionResult(new_exps);//计算结果
                }else{
                    result = "false";//新基础表达式不符合
                }
            }else {
                result = "false";
            }
        }
        return result;
    }

    /**
     * 去除算术表达式括号
     * @param exps 算术表达式字符串，数字和符号用空格隔开
     * @return 返回去掉首尾括号的算术表达式字符串
     */
    public String deleteBracket(String exps){
        String[] exps_array = screen.splitExpression(exps);//得到分隔后的字符串数组
        //首尾是否右括号
        if (exps_array[0].equals("(") && exps_array[exps_array.length-1].equals(")")){
            return exps.substring(2,exps.length()-2);
        }else {
            return exps;
        }
    }

    /**
     * 粗略计算基本表达式结果,不作真分数、分数运算
     * @param exps 算术表达式字符串，数字和符号用空格隔开
     * @return 返回数值用字符串表示
     */
    public String espressionResult(String exps){
        String result_str;//运算结果
        String[] exps_array = screen.splitExpression(exps);//得到分隔后的字符串数组
        //默认左右运算数用double运算
        double left = Double.parseDouble(exps_array[0]);
        double right = Double.parseDouble(exps_array[2]);
        double result;
        switch (exps_array[1]){
            case "＋":
                result = left + right;
                result_str = String.valueOf(result);
                break;
            case "-":
                result = left - right;
                break;
            case "×":
                result = left * right;
                break;
            case "÷":
                result = left / right;
                break;
            default:
                result = -1;
        }
        result_str = String.valueOf(result)+ " ";//结果尾处加一空格，方便字符串分解
        return result_str;
    }

    /**
     * 获取算术表达式运算符的优先级
     * 核心思想遍历一次算术表达式，依次将括号内的运算符、乘除、加减所在数组位置放入各自优先级数组位置，记录的是运算符的地址
     * @param total_exps 算术表达式字符串，数字和符号用空格隔开
     * @return 返回数组记录运算符优先级所在地址
     */
    public int[] operator(String total_exps){
        String[] exps_array = screen.splitExpression(total_exps);//得到分隔后的字符串数组
        int[] adr_add = new int[3];//记录加减号地址的数组
        int[] adr_minus = new int[3];//记录乘除号地址的数组
        int[] adr_bracket = new int[3];//记录括号内运算符地址的数组
        int count = 0;//运算符数量统计
        int adr_bracket_i = 0;//括号优先级数组下标
        int adr_add_i = 0;//加减号优先级数组下标
        int adr_minus_i =0;//乘除号优先级数组下标
        int adr_i = 0;//总优先级数组下标
        int j;
        for (int i = 0;i < exps_array.length; ++i){
            if (exps_array[i].equals("(")){//括号优先级
                if (exps_array[i+1].equals("(")){//例如：((1+2)+3)
                    adr_bracket[adr_bracket_i] = i + 3;
                    adr_bracket[adr_bracket_i+1] = i + 6;
                    adr_bracket_i = adr_bracket_i +2;
                    i = i + 8;
                    count+=2;
                }else if (exps_array[i+3].equals("(")){//例如：(1+(1+2))
                    adr_bracket[adr_bracket_i] = i + 5;
                    adr_bracket[adr_bracket_i+1] = i + 2;
                    adr_bracket_i = adr_bracket_i +2;
                    i = i + 8;
                    count+=2;
                }else if (exps_array[i+4].equals(")")){//例如：(1+2)+3
                    adr_bracket[adr_bracket_i] = i + 2;//存放符号所在exps_array数组地址
                    adr_bracket_i++;
                    i = i + 4;
                    count++;
                }else if (exps_array[i+6].equals(")")){//例如：(1+2+3)+4
                    if (exps_array[i+2].equals("×") || exps_array[i+2].equals("÷")){
                        adr_bracket[adr_bracket_i]= i + 2;
                        adr_bracket[adr_bracket_i+1] = i + 4;
                    }else{ // (exps_array[i+2].equals("+") || exps_array[i+2].equals("-"))
                        if (exps_array[i+4].equals("×") || exps_array[i+4].equals("÷")){
                            adr_bracket[adr_bracket_i]= i + 4;
                            adr_bracket[adr_bracket_i+1] = i + 2;
                        }else {
                            adr_bracket[adr_bracket_i]= i + 2;
                            adr_bracket[adr_bracket_i+1] = i + 4;
                        }
                    }
                    adr_bracket_i = adr_bracket_i + 2;
                    i = i + 6;
                    count+=2;
                }
            }else if(exps_array[i].equals("×") || exps_array[i].equals("÷")){
                adr_minus[adr_minus_i] = i;
                adr_minus_i++;
                count++;
            }else if (exps_array[i].equals("＋") || exps_array[i].equals("-")){
                adr_add[adr_add_i] = i;
                adr_add_i++;
                count++;
            }
        }
        int[] adr = new int[count];//总优先级数组
        for ( j = 0;adr_bracket_i != 0 && j <= adr_bracket_i-1;j++){//依次存入括号优先级运算符地址
            adr[adr_i] = adr_bracket[j];
            adr_i++;
        }
        for ( j = 0;adr_minus_i != 0 && j <= adr_minus_i-1;j++){//依次存入乘除号优先级运算符地址
            adr[adr_i] = adr_minus[j];
            adr_i++;
        }
        for ( j = 0;adr_add_i != 0 && j <= adr_add_i-1;j++){//依次存入加减号优先级运算符地址
            adr[adr_i] = adr_add[j];
            adr_i++;
        }
        return adr;
    }

    /**
     * 分割字符串，以空格为标志将运算数、运算符号、括号作为字符串存入字符串数组
     * @param exps 算术表达式字符串，数字和符号用空格隔开
     * @return 返回字符串数组
     */
    public String[] splitExpression(String exps){
        //以空格为标志
        String[] exps_array = exps.split(" +");
        return exps_array;
    }

    /**
     * 基础算术表达式判断，是否会出现负数结果，是否会出现除数为0
     * @param base_exps 基础算术表达式字符串，数字和符号用空格隔开
     * @return true：符合规则；false：不符合规则
     */
    public boolean baseExpression(String base_exps){
        String[] exps_array = screen.splitExpression(base_exps);//得到分隔后的字符串数组
        //表达式只有一个运算符的，例如"1 + 2"
        if (exps_array.length == 3){
            if (exps_array[1].equals("÷")){//运算符是除号
                if(screen.zeroBehindDenominator(exps_array[2]) == true)//除数为0
                    return false;
            }else if(exps_array[1].equals("-")){//运算符号是减号
                if (screen.negativeExps(exps_array[0],exps_array[2]) == true)//子运算式为负数结果
                    return false;
            }
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断除数是否为0
     * @param s 除数，字符串类型
     * @return true：除数为0；false：除数不为0
     */
    public boolean zeroBehindDenominator(String s){
        //字符串只有一位且为“0”；
        if (s.equals("0") || s.equals("0.0")){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 基本运算式是否会出现负数
     * @param s1 被减数字符串
     * @param s2 减数字符串
     * @return true：运算结果为负数；false：运算结果不为负数
     */
    public boolean negativeExps(String s1,String s2){
        double num1,num2;
        num1 = Double.parseDouble(s1);
        num2 = Double.parseDouble(s2);
        //比较被减数与减数关系
        if (num1 < num2) {
            return true;
        }else {
            return false;
        }
    }

/*    public String[] getPriorityExpression(String exps){
        String[] exps_array = screen.splitExpression(exps);
        int[] adr_array = screen.operator(exps);
        for ()
    }

    public String isRepeat(String exps1,String exps2){
        String[] exps_array1 = screen.splitExpression(exps1);
        String[] exps_array2 = screen.splitExpression(exps2);
        int adr1 = screen.operator(exps1);
        int adr2 = screen.operator(exps2);
        if ()
        return true;
    }*/
}
