package Funtion;

public class Screen {
/*    String add = new String("＋");//加号字符串
    String minus = new String("-");//减号字符串
    String div = new String("÷");//除号字符串
    String mul = new String("×");//乘号字符串
    String zero = new String("0");//0字符串
    String left_bracket = new String("(");//左括号
    String right_bracket = new String(")");//右括号*/
    static Screen screen = new Screen();

    public String roughScreen(String exps){
        String result = "";
        String[] exps_array = screen.splitExpression(exps);
        if (exps_array.length == 1){
            result = exps;
        }else if (exps_array.length == 3){
            if (true == baseExpression(exps)){
                result = screen.espressionResult(exps);//计算用的
            }else{
                result = "false";
            }
        }else if(exps.equals("false")){
            result = "false";
        }else {
            String left_exps = "";
            String left_result = "";
            String right_exps = "";
            String right_result = "";
            int[] adr_array = screen.operator(exps);
            int adr = adr_array[adr_array.length-1];
            for (int i = 0;i <= adr-1;i++){
                left_exps += exps_array[i] + " ";
            }
            left_exps = screen.deleteBracket(left_exps);
            for (int j = adr+1;j <= exps_array.length-1;j++){
                right_exps += exps_array[j] + " ";
            }
            right_exps = screen.deleteBracket(right_exps);
            left_result = screen.roughScreen(left_exps);
            right_result = screen.roughScreen(right_exps);
            if (!left_result.equals("false") && !right_result.equals("false")){
                String new_exps;//把左结果和右结果以及运算符结合成新的运算式
                new_exps = left_result + " " + exps_array[adr]+ " "+ right_result;
                if (true == baseExpression(new_exps)) {
                    result = screen.espressionResult(new_exps);//计算用的
                }else{
                    result = "false";
                }
            }else {
                result = "false";
            }
        }
        return result;
    }

    public String deleteBracket(String exps){
        String[] exps_array = screen.splitExpression(exps);
        if (exps_array[0].equals("(") && exps_array[exps_array.length-1].equals(")")){
            return exps.substring(2,exps.length()-2);
        }else {
            return exps;
        }
    }

    public String espressionResult(String exps){
        String result_str;
        String[] exps_array = screen.splitExpression(exps);
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
        result_str = String.valueOf(result)+ " ";
        return result_str;
    }

    public int[] operator(String total_exps){
        String[] exps_array = screen.splitExpression(total_exps);
        int[] adr_add = new int[3];
        int[] adr_minus = new int[3];
        int[] adr_bracket = new int[3];
        int count = 0;//运算符统计
        int adr_bracket_i = 0;
        int adr_add_i = 0;
        int adr_minus_i =0;
        int adr_i = 0;
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
        int[] adr = new int[count];
        for ( j = 0;adr_bracket_i != 0 && j <= adr_bracket_i-1;j++){
            adr[adr_i] = adr_bracket[j];
            adr_i++;
        }
        for ( j = 0;adr_minus_i != 0 && j <= adr_minus_i-1;j++){
            adr[adr_i] = adr_minus[j];
            adr_i++;
        }
        for ( j = 0;adr_add_i != 0 && j <= adr_add_i-1;j++){
            adr[adr_i] = adr_add[j];
            adr_i++;
        }
        return adr;
    }

    public String[] splitExpression(String exps){
        String[] exps_array = exps.split(" +");
        return exps_array;
    }

    public boolean baseExpression(String base_exps){
        String[] exps_array = screen.splitExpression(base_exps);
        //表达式只有一个运算符的，例如"1 + 2"
        /*System.out.println(exps_array.length);
        for (int i = 0; i < exps_array.length;i++){
            System.out.println(exps_array[i]);
        }*/
        if (exps_array.length == 3){
            if (exps_array[1].equals("÷")){//运算符是除号
                if(screen.zeroBehindDenominator(exps_array[2]) == true)
                    return false;
            }else if(exps_array[1].equals("-")){//运算符号是减号
                if (screen.negativeExps(exps_array[0],exps_array[2]) == true)
                    return false;
            }
            return true;
        }else {
            return false;
        }
    }

    public boolean zeroBehindDenominator(String s){
        //字符串只有一位且为“0”；
        if (s.equals("0") || s.equals("0.0")){
            return true;
        }else {
            return false;
        }
    }

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
