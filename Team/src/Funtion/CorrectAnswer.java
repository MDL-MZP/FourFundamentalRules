package Funtion;

/**
 * @author Mazin
 * 答案
 */

public class CorrectAnswer {


    /**      配合正确输出的算术表达式 并 做出正确答案     */
    public static String answer(String correctExpression) {
        String s;
        StringBuffer temp = new StringBuffer();
        String[] charpriority;
        String[] str = correctExpression.split(" ");
        for (int i = 0; i < str.length; i++) {
            temp.append(str[i]);
        }
        //  得到优先级
        charpriority = priority(temp);
        //  得到charpriority的实际长度len
        int len = 0;
        for(int j = 0; j < charpriority.length && charpriority[j] != null; j++){
                len++;
        }
        // 除去括号的 str 数组, 并调整优先级的下标
        for(int i = 0; i < str.length;){
            if("(".equals(str[i]) || ")".equals(str[i])){
                for(int j = i; j < str.length-1; j++) {
                    str[j] = str[j + 1];
                }
                for(int j = 0; j < len; j++){
                    int n = Integer.parseInt(charpriority[j]);
                    if(n > i){
                        charpriority[j] = String.valueOf(--n);
                    }
                }
            }else{
                i++;
            }
        }
        //    按照运算符的顺序. 一个一个传入计算 , 在函数里计算
        s = adjust(str,charpriority,len);
        return s;
    }

    /**          调整表达式，算法思想体现          */
    private static String adjust(String[] str, String[] charpriority,int len) {
        String s = "";
        int oldIndex = 0;
        for(int count = 0; count < len; count++) {
            int i = Integer.parseInt(charpriority[count]);
            //  将第1、2次的累计结果放在 上一次运算符 最靠近下一个运算符 的位置
            if (count >= 1) {
                if (i > oldIndex) {
                    str[oldIndex + 1] = s;
                } else {
                    str[oldIndex - 1] = s;
                }
            }
            // 如果有3个运算符，那么最后一个，就强制把第二次的结果放到最后一个运算符的临位
            if(count == 2){
                if (i > oldIndex) {
                    str[i - 1] = s;
                } else {
                    str[i + 1] = s;
                }
            }
            //  得到运算符（String）的 char 类型
            char[] c = str[i].toCharArray();
            s = beforeCaculate(str[i - 1],str[i + 1],c[0]);
            oldIndex = i;
        }
        return s;
    }

    /**   真分数数值计算, 转化为统一形式      */
    private static String beforeCaculate(String a,String b,char c) {
        String[] separate1,separate2,temp;
        int[] num1 = new int[3];
        int[] num2 = new int[3];
        String s;
        // 变为统一形式
        if(!a.contains("/")){
            a = a + "\'0/1";
        }else if(!a.contains("'")){
            a = 0 + "'" + a;
        }
        if(!b.contains("/")){
            b = b + "\'0/1";
        }else if(!b.contains("'")){
            b = 0 + "'" + b;
        }
        //  提取 字符 a ，用separate1数组
        separate1 = a.split("'");

        temp = separate1[1].split("/");

        num1[0] = Integer.parseInt(separate1[0]);
        num1[1] = Integer.parseInt(temp[0]);
        num1[2] = Integer.parseInt(temp[1]);
        // 提取 字符 b ，用separate2数组
        separate2 = b.split("'");
        temp = separate2[1].split("/");
        num2[0] = Integer.parseInt(separate2[0]);
        num2[1] = Integer.parseInt(temp[0]);
        num2[2] = Integer.parseInt(temp[1]);
        s = caculate(num1,num2,c);
        return s;
    }

    /**       选择运算符，通分计算            */
    private static String caculate(int[] num1,int[] num2,char c){
        String s = "";
        // up 分子 down 分母
        int up1,down1,up2,down2;
        up1 = num1[0] * num1[2] + num1[1];
        down1 = num1[2];
        up2 = num2[0] * num2[2] + num2[1];
        down2 = num2[2];
        // 通分结果，result[0]放分子，result[1]放分母
        int[] result = new int[2];
        // 无脑通分
        up1 = up1 * down2;
        up2 = up2 * down1;
        result[1] = down1 * down2;

        switch (c) {
            case '＋':
                result[0] = up1+up2;
                break;
            case '-':
                result[0] = up1-up2;
                break;
            case '÷':
                result[0] = up1 * result[1];
                result[1] = up2 * result[1];
                break;
            case '×':
                result[0] = up1*up2;
                result[1] = result[1] * result[1];
                break;
            default:
        }
        // 整数部分
        int integer = result[0] / result[1];
        // 分子部分
        int fraction = result[0] % result[1];
        // 求最大公约数
        if(fraction != 0) {
            int common = f(fraction, result[1]);
            //  约分
            fraction = fraction / common;
            result[1] = result[1] / common;
        }

        if (integer != 0) {
            if(fraction != 0) {
                s = integer + "'" + fraction + "/" + result[1];
            }else{
                s = integer + "";
            }
        } else {
            if(fraction != 0) {
                s = fraction + "/" + result[1];
            }else{
                s = "0";
            }
        }
        return s;
    }

    /**   辗转相除 求最大公约数,down大 up小 */
    private static int f(int up,int down){
        int temp;
        while(true) {
            temp = down % up;
            if (temp == 0) {
                return up;
            } else {
                down = up;
                up = temp;
            }
        }
    }

    /**
     * 计算符号优先级
     * 基于 已知两个括号,但也可以扩展,只用left和right,
     * 把括号也放入数组,优先级不考虑括号
     */
    public static String[] priority(StringBuffer temp) {
        String[] newstr;
        //  找外层括号的符号, 找是否有内层括号,有的内层先放内层符号,再放外层
        //  4个括号的下标数组,初始化-1
        StringBuffer t = new StringBuffer(temp);
        int[] blankets = {-1, -1, -1, -1};
        int[] real = {-1,-1,-1,-1};
        blankets[0] = t.indexOf("(");
        if(blankets[0] != -1) {
            t.deleteCharAt(blankets[0]);
            if (t.indexOf("(") == -1) {
                //  仅有一个括号,且被删除了 (
                blankets[1] = t.indexOf(")") + 1;
            } else if (t.indexOf("(") > t.indexOf(")")) {
                blankets[1] = t.indexOf(")") + 1;
                //  被删除了 （
                blankets[2] = t.indexOf("(") + 1;
                blankets[3] = t.lastIndexOf(")") + 1;
            } else {
                blankets[1] = t.lastIndexOf(")") + 1;
                blankets[2] = t.indexOf("(") + 1;
                blankets[3] = t.indexOf(")") + 1;
            }
        }

        //  real = blankets;   不报语法错误，但是，real改变，blanket也改变？
        for(int i = 0; i < 4; i++){
            real[i] = blankets[i];
        }

        for(int j = 0; j < 4; j++) {
            boolean flag = true;
            int count = -1;
            if(real[j] != -1) {
                x:
                for (int i = 0; i <= real[j]; i++) {
                    boolean tag1 = (temp.charAt(i) >= 48 && temp.charAt(i) <= 57);
                    boolean tag2 = temp.charAt(i) == '×' || temp.charAt(i) == '-' || temp.charAt(i) == '＋' || temp.charAt(i) == '÷';
                    if (tag1 && flag) {
                        count++;
                        flag = false;
                    }
                    if (tag2) {
                        count++;
                        flag = true;
                    }
                    if (!tag1 && !tag2) {
                        if (i == real[j]) {
                            real[j] = ++count;
                            break x;
                        }else{
                            flag = true;
                            count++;
                        }
                    }
                }
            }
        }

        //   求运算符和下标
        String[] oldstr = charAndIndex(temp);

        newstr= sort(blankets, oldstr,temp,real);
        return newstr;
    }

    private static String[] sort(int[] blankets, String[] oldstr,StringBuffer expression,int[] real) {
        String[] newstr = new String[6];
        //  按照无括号的形式排序优先级all，其实只是为了找到未进行排序的运算符下标
        // all 和 oldstr 的区别：对整条表达式，all比较了优先级（不包括括号的情况），oldstr未比较，只是从左到右
        String[] oldstring = new String[6];
        for(int i = 0 ; i < 6; i++){
            oldstring[i] = oldstr[i];
        }
        //  不这样赋值，oldstr进入函数会被改变
        String[] all = blanketsJudge(oldstr);

        // 无内层括号
        if (blankets[2] == -1) {
            //  无内层无外层括号
            if (blankets[0] == -1) {
                newstr = all;
                return newstr;
            } else {
                String[] str;
                //  截取出括号内的表达式
                StringBuffer s = new StringBuffer(expression.substring(blankets[0]+1,blankets[1]));

                // 求出括号内表达式的运算符和下标
                str = charAndIndex(s);

                //  截取出来的运算符下标，少了括号的下标，要加上
                int j = 0;
                while(str[j] != null){
                    str[j] = String.valueOf(Integer.parseInt(str[j]) + real[0] + 1);
                    j += 2;
                }
                //  对 str 的运算符做优先级排序，输出运算符下标
                newstr = blanketsJudge(str);

                // 算str的实际长度, 其实也就是 newstr现有长度的两倍
                int len = 0;
                for(j = 0; str[j] != null; j++){
                    len++;
                }
                len /= 2;
                // 找到,未进行排序的 运算符下标, 而all已经排序了，从头找到未排序的按顺序添加至newstr末尾就行
                x:
                for (int i = 0; i < all.length && all[i] != null;i++) {
                    for (int x = 0; newstr[x] != null; x++) {
                        if (newstr[x].equals(all[i])) {
                            continue x;
                        }
                    }
                    newstr[len++] = all[i];
                }
                return newstr;
            }
        }else{
            //  不具备拓展性代码, 2个括号,一定是3个运算符
            String[] temp = new String[6];
            int t = 0;
            int j = 0, p;
            String[] str1;
            String[] str2;
            //  双括号嵌套
        if(blankets[1] > blankets[2]) {
            StringBuffer s1 = new StringBuffer(expression.substring(blankets[0] + 1, blankets[1]));
            StringBuffer s2 = new StringBuffer(expression.substring(blankets[2] + 1, blankets[3]));
            // 求出括号内表达式的运算符和下标
            str1 = charAndIndex(s1);
            str2 = charAndIndex(s2);

            //  截取出来的运算符下标，少了括号的下标，要加上
            while (str2[j] != null) {
                str2[j] = String.valueOf(Integer.parseInt(str2[j]) + real[2] + 1);
                j += 2;
            }
            j = 0;
            while (str1[j] != null) {
                str1[j] = String.valueOf(Integer.parseInt(str1[j]) + real[0] + 1);
                j += 2;
            }
            // str2是最内层括号内的下标+运算符
            newstr[0] = str2[0];
            if (str1[0].equals(str2[0])) {
                newstr[1] = str1[2];
            } else {
                newstr[1] = str1[0];
            }
            x:
            for (p = 0; p < all.length; p++) {
                for (int x = 0; newstr[x] != null; x++) {
                    if (newstr[x].equals(all[p])) {
                        continue x;
                    }
                }
                newstr[2] = all[p];
                break x;
            }
            return newstr;
        }else{
                newstr[0] = oldstring[0];
                newstr[1] = oldstring[4];
                newstr[2] = oldstring[2];
                return newstr;
            }
        }
    }

    /**      输出  str数组内运算符 按优先级排序好的顺序, 记录下标      */
    public static String[] blanketsJudge(String[] str){
        boolean tag1,tag2,flag = false,endtag = true;
        int temp = -1,j = 0,i;
        for(i = 1; i < str.length; i+=2) {
            //   拿出运算符在表达式的下标,判断是否在括号内
            //  拿出下标对应的运算符
            tag1 = "＋".equals(str[i]) || "-".equals(str[i]);
            tag2 = "×".equals(str[i]) || "÷".equals(str[i]);
            //  遇到 +-
            if (tag1) {
                flag = true;
                temp = i;
            }
            if (tag2) {
                if (flag) {
                    // 两个交换,把下标和运算符都交换
                    swap(str, i, temp);
                    swap(str, i - 1, temp - 1);
                    // 有交换,不结束
                    endtag = false;
                    // temp 跟进 +- 号
                    temp = i;
                } else {
                    endtag = true;
                }
            }
            if (!endtag && i == str.length - 1) {
                flag = false;
                temp = -1;
                i = -1;
            } else if (endtag && i == str.length - 1) {
                break;
            }
        }
        String[] newstr = new String[6];
        //   输出排序后, 运算符的下标
        for(int index = 0;index < str.length; index+=2){
            newstr[j++] = str[index];
        }
        return newstr;
    }

    private static void swap(String[] oldstr,int a,int b){
        String temp;
        temp = oldstr[a];
        oldstr[a] = oldstr[b];
        oldstr[b] = temp;
    }

    /**   求运算符和下标       */
    public static String[] charAndIndex(StringBuffer temp){
        int count = -1 ;
        boolean flag = true;
        String[] oldstr = new String[6];
        for (int i = 0, j = 0; i < temp.length(); i++) {
            boolean tag1 = (temp.charAt(i) >= 48 && temp.charAt(i) <= 57);
            boolean tag2 = temp.charAt(i) == '(' || temp.charAt(i) == ')' || temp.charAt(i) == '=';
            if(tag1 && flag){
                count++;
                flag = false;
            }
            if(tag2){
                count++;
            }
            if (!tag1 && !tag2) {
                // 运算符的下标
                oldstr[j++] = String.valueOf(++count);
                // 运算符
                oldstr[j++] = String.valueOf(temp.charAt(i));
                flag = true;
            }
        }
        return oldstr;
    }

}