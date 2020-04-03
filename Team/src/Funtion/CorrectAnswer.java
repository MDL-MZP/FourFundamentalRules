package Funtion;

/**
 * @author Mazin
 * 答案
 */

public class CorrectAnswer {

    /**
     *   配合正确输出的算术表达式 并 做出正确答案
     * @param correctExpression 经过筛选过后的表达式
     * @return 返回表达式的结果
     */
    public static String answer(String correctExpression) {
        // 准备接受正确的结果
        String s;
        // 表达式的StringBuffer类型
        StringBuffer temp = new StringBuffer();
        // 运算符优先级数组
        String[] charpriority;
        // 计算主体 str 数组
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
            // 遇到括号，准备删除
            if("(".equals(str[i]) || ")".equals(str[i])){
                // 将括号后面的数据全部前移一位
                for(int j = i; j < str.length-1; j++) {
                    str[j] = str[j + 1];
                }
                // 需要调整所有的运算符
                for(int j = 0; j < len; j++){
                    // 取出 运算符在 str 数组的下标
                    int n = Integer.parseInt(charpriority[j]);
                    // 运算符在括号后面才需要 -1 前移
                    if(n > i){
                        charpriority[j] = String.valueOf(--n);
                    }
                }
            }else{
                i++;
            }
        }
        //按照运算符的顺序. 一个一个传入计算 , 得到最终结果
        s = adjust(str,charpriority,len);
        return s;
    }

    /**
     * 调整表达式，计算算法思想体现
     * @param str 表达式的String数组，主要在这里计算
     * @param charpriority 运算符的优先级String数组
     * @param len 运算符优先级数组的实际长度
     * @return 返回运算结果
     */
    private static String adjust(String[] str, String[] charpriority,int len) {
        String s = "";
        int oldIndex = 0;
        // count是计量 charpriority数组的实际长度
        for(int count = 0; count < len; count++) {
            //  取出 运算符 在 str 数组的下标
            int i = Integer.parseInt(charpriority[count]);
            //  将第1、2次的累计结果放在 上一次运算符 最靠近下一个运算符 的位置
            if (count >= 1) {
                if (i > oldIndex) {
                    str[oldIndex + 1] = s;
                } else {
                    str[oldIndex - 1] = s;
                }
            }
            // 如果有3个运算符，那么最后一个，就强制把第二次的结果放到最后一个运算符的临位（左或右）
            if(count == 2){
                if (i > oldIndex) {
                    str[i - 1] = s;
                } else {
                    str[i + 1] = s;
                }
            }
            //  得到运算符（String）的 char 类型
            char[] c = str[i].toCharArray();
            // 做计算前的准备，并进入下一层去计算
            s = beforeCaculate(str[i - 1],str[i + 1],c[0]);
            // 记录上一次运算符的位置
            oldIndex = i;
        }
        return s;
    }

    /**
     *   真分数数值计算, 转化为统一形式
     * @param a 参与运算的数字1的String形式
     * @param b 参与运算的数字2的String形式
     * @param c 运算符的字符形式
     * @return 返回运算结果
     */
    private static String beforeCaculate(String a,String b,char c) {
        String[] separate1,separate2,temp;
        int[] num1 = new int[3];
        int[] num2 = new int[3];
        // 变为统一形式, 要求数字必须为 "整数'分子/分母”形式
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
        // 计算前准备完成，计算
        String s = caculate(num1,num2,c);
        return s;
    }

    /**
     *     选择运算符，通分计算
     * @param num1 运算的第一个数
     * @param num2 运算的第二个数
     * @param c  运算符的字符类型
     * @return 返回num1和num2的运算结果
     */
    private static String caculate(int[] num1,int[] num2,char c){
        String s = "";
        // up：分子，down：分母
        int up1,down1,up2,down2;
        up1 = num1[0] * num1[2] + num1[1];
        down1 = num1[2];
        up2 = num2[0] * num2[2] + num2[1];
        down2 = num2[2];
        // 通分结果，result[0]放分子，result[1]放分母
        int[] result = new int[2];
        // 简单通分，直接分子分母相乘
        up1 = up1 * down2;
        up2 = up2 * down1;
        // 通分后分母
        result[1] = down1 * down2;
        // 选择运算符
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
        // 因为最后需要除以公约数，防止除以0，因为0和其他数的最大公约为0
        if(fraction != 0) {
            // 求最大公约数
            int common = f(fraction, result[1]);
            //  约分
            fraction = fraction / common;
            result[1] = result[1] / common;
        }
        // 选择输出的形式
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

    /**
     *  辗转相除 求最大公约数,down大 up小
     * @param up 分子
     * @param down 分母
     * @return 返回up和down的最大公约数
     */
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
     *      计算符号优先级
     * @param temp 表达式的StringBuffer类型
     * @return 返回排好运算符优先级的String[]数组，[0]优先级最高
     */
    private static String[] priority(StringBuffer temp) {
        String[] newstr;
        StringBuffer t = new StringBuffer(temp);
        //  4个括号的下标数组,初始化-1
        // blankets 记录 表达式StringBuffer类型的 括号下标，[0][1]记录外层括号，[2][3]记录内层括号
        int[] blankets = {-1, -1, -1, -1};
        // real 记录 表达式String[]类型的 括号下标，（主要）[0][1]记录外层括号，[2][3]记录内层括号
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
        //  real先拿到blankets的值，再做更新
        for(int i = 0; i < 4; i++){
            real[i] = blankets[i];
        }
        // 更新real为 str 数组的括号下标，一共4个括号
        for(int j = 0; j < 4; j++) {
            // flag 用于多位数的 计数，如 123 应该记为 1。
            boolean flag = true;
            //  count是记录实际 str 数组的括号下标，-1表示不存在
            int count = -1;
            if(real[j] != -1) {
                x:
                // 在遇到 指定 的括号前，计数 数字、运算符、其他括号
                for (int i = 0; i <= real[j]; i++) {
                    boolean tag1 = (temp.charAt(i) >= 48 && temp.charAt(i) <= 57);
                    boolean tag2 = temp.charAt(i) == '×' || temp.charAt(i) == '-' || temp.charAt(i) == '＋' || temp.charAt(i) == '÷';
                    // 遇到数字
                    if (tag1 && flag) {
                        count++;
                        flag = false;
                    }
                    // 遇到运算符
                    if (tag2) {
                        count++;
                        flag = true;
                    }
                    // 遇到括号，是否为 指定 括号待定
                    if (!tag1 && !tag2) {
                        // 到达末尾，则为 指定 括号
                        if (i == real[j]) {
                            real[j] = ++count;
                            break x;
                        }
                        // 不是 指定 括号
                        else{
                            flag = true;
                            count++;
                        }
                    }
                }
            }
        }

        //求表达式StringBuffer类型下的运算符和下标，不考虑优先级，直接从左到右读取运算符
        String[] oldstr = charAndIndex(temp);
        // 结合括号，运算符 整理得到 最终 按优先级排序的 运算符 的 下标
        newstr= sort(blankets, oldstr,temp,real);
        return newstr;
    }

    /**
     * 统计括号的情况，
     * @param blankets 表达式StringBuffer类型的 括号下标
     * @param oldstr  表达式 运算符及其下标的数组，下标在前，运算符在后
     * @param expression 表达式的StringBuffer类型
     * @param real  表达式String数组类型的 括号下标
     * @return  返回排好运算符优先级的String[]数组，[0]优先级最高，且只存下标，该下标针对表达式的String数组下标
     */
    private static String[] sort(int[] blankets, String[] oldstr,StringBuffer expression,int[] real) {
        String[] newstr = new String[6];
       //  将oldstr数组拷贝到 oldstring 数组
        String[] oldstring = new String[6];
        for(int i = 0 ; i < 6; i++){
            oldstring[i] = oldstr[i];
        }
        //  按照无括号的形式排序优先级得到all，其实只是为了方便找到未进行排序的运算符下标
        String[] all = blanketsJudge(oldstr);
        // all 和 oldstr 的区别：对整条表达式，all比较了优先级（“忽视”括号的情况），oldstr未比较优先级，只是从左到右记录
        // 无内层括号
        if (blankets[2] == -1) {
            //  无内层无外层括号
            if (blankets[0] == -1) {
                // 无括号便是 all 的情况
                newstr = all;
                return newstr;
            } else {
                String[] str;
                //  截取出括号内的表达式，这也是为什么要blankets的原因
                StringBuffer s = new StringBuffer(expression.substring(blankets[0]+1,blankets[1]));
                // 求出括号内表达式的运算符和下标
                str = charAndIndex(s);
                //截取出来的运算符下标，这个下标是在str数组内的，所以少了括号的下标用real，要加上
                int j = 0;
                while(str[j] != null){
                    str[j] = String.valueOf(Integer.parseInt(str[j]) + real[0] + 1);
                    j += 2;
                }
                // 对括号内的运算符做优先级排序，输出运算符下标
                newstr = blanketsJudge(str);
                // 算str的实际长度, 其实也就是 newstr现有长度的两倍
                int len = 0;
                for(j = 0; str[j] != null; j++){
                    len++;
                }
                len /= 2;
                // 找到,未进行排序的 运算符下标, 而all已经排序了，从头找到未进行排序的下标，按顺序添加至newstr末尾就行
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
            //2个括号,一定是3个运算符
            String[] temp = new String[6];
            int j = 0, p;
            String[] str1;
            String[] str2;
            //  双括号嵌套
        if(blankets[1] > blankets[2]) {
            // 截取括号内的StringBuffer表达式
            StringBuffer s1 = new StringBuffer(expression.substring(blankets[0] + 1, blankets[1]));
            StringBuffer s2 = new StringBuffer(expression.substring(blankets[2] + 1, blankets[3]));
            // 求出括号内表达式的运算符和下标
            str1 = charAndIndex(s1);
            str2 = charAndIndex(s2);
            //  截取出来的运算符下标，这个下标是在str数组内的，所以少了括号的下标用real，要加上
            while (str2[j] != null) {
                str2[j] = String.valueOf(Integer.parseInt(str2[j]) + real[2] + 1);
                j += 2;
            }
            j = 0;
            while (str1[j] != null) {
                str1[j] = String.valueOf(Integer.parseInt(str1[j]) + real[0] + 1);
                j += 2;
            }
            //str2是必定是最内层括号内的 下标和运算符
            newstr[0] = str2[0];
            // 比较外层括号里的2个运算符，找到跟内层括号不同的运算符下标
            if (str1[0].equals(str2[0])) {
                newstr[1] = str1[2];
            } else {
                newstr[1] = str1[0];
            }
            x:
            // 第3个运算符需要跟 all 比较，少的那个直接加到 newstr 末尾
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
        }
        // 平行双括号（1+2）-(3+4), 那么，必定是第一个运算符，第三个运算符，第二个运算符这样的顺序
        else{
                newstr[0] = oldstring[0];
                newstr[1] = oldstring[4];
                newstr[2] = oldstring[2];
                return newstr;
            }
        }
    }

    /**
     * 输出  str数组内运算符 按优先级排序好的顺序, 记录下标
     * @param str 需要作运算符优先级判断的数组
     * @return 返回str数组内运算符的优先级顺序
     */
    private static String[] blanketsJudge(String[] str){
        boolean tag1,tag2,flag = false,endtag = true;
        // temp用来记录 +- 符号的 下标
        int temp = -1,j = 0;
        for(int i = 1; i < str.length; i+=2) {
            //  拿出下标对应的运算符
            tag1 = "＋".equals(str[i]) || "-".equals(str[i]);
            tag2 = "×".equals(str[i]) || "÷".equals(str[i]);
            //  遇到 +-
            if (tag1) {
                // flag 表示先遇到+-在遇到×÷,那么就需要调整优先级
                flag = true;
                // temp 记录 +- 下标
                temp = i;
            }
            // 遇到×÷
            if (tag2) {
                // flag为true，就是需要交换了（+-在×÷之前），到时候直接从数组开头到末尾读取
                if (flag) {
                    // 把下标和运算符都交换
                    swap(str, i, temp);
                    swap(str, i - 1, temp - 1);
                    // 有交换,那么不结束
                    endtag = false;
                    // temp 跟进 +- 号
                    temp = i;
                }
                // 如果前面不做交换，那么有结束的前提
                else {
                    endtag = true;
                }
            }
            // 结束的标志有 endtag，且得到表达式末尾
            if (!endtag && i == str.length - 1) {
                // 到达末尾，但不能结束，那么，初始化数据，重新遍历，i=-1，是为了i+=2能变成1
                flag = false;
                temp = -1;
                i = -1;
            }
            // endtag为true表示可以结束了，如果此时到末尾，直接结束
            else if (endtag && i == str.length - 1) {
                break;
            }
            // 处理下一个运算符
        }
        String[] newstr = new String[6];
        //   输出排序后, 运算符的下标，偶数下标
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

    /**
     *   求运算符和下标
     * @param temp 表达式的StringBuffer类型
     * @return 返回运算符及其下标的数组，下标在前，运算符在后
     */
    private static String[] charAndIndex(StringBuffer temp){
        int count = -1 ;
        // flag 计数 运算符前的 个数
        boolean flag = true;
        String[] oldstr = new String[6];
        for (int i = 0, j = 0; i < temp.length(); i++) {
            // 数字
            boolean tag1 = (temp.charAt(i) >= 48 && temp.charAt(i) <= 57);
            // 括号或=
            boolean tag2 = temp.charAt(i) == '(' || temp.charAt(i) == ')' || temp.charAt(i) == '=';
            // 遇到数字，且flag为true，flag是保证在多位数情况下正确计数
            if(tag1 && flag){
                count++;
                flag = false;
            }
            // 遇到括号或=
            if(tag2){
                count++;
                flag = true;
            }
            // 遇到运算符
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