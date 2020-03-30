//public class test {
//    if(blankets[1] > blankets[2]){
//
//        StringBuffer s1 = new StringBuffer(expression.substring(blankets[0]+1,blankets[1]));
//        StringBuffer s2 = new StringBuffer(expression.substring(blankets[2]+1,blankets[3]));
//        // 求出括号内表达式的运算符和下标
//        str1 = charAndIndex(s1);
//        str2 = charAndIndex(s2);
//        //  截取出来的运算符下标，少了括号的下标，要加上
//        while(str2[j] != null){
//            str2[j] = String.valueOf(Integer.parseInt(str2[j])+real[2] + 1);
//            j+=2;
//        }
//        j = 0;
//        while(str1[j] != null){
//            str1[j] = String.valueOf(Integer.parseInt(str1[j])+real[0] + 1);
//            j+=2;
//        }
//        // str2是最内层括号内的下标+运算符
//        newstr[0] = str2[0];
//        if(str1[0].equals(str2[0])){
//            newstr[1] = str1[2];
//        }else {
//            newstr[1] = str1[0];
//        }
//        x:
//        for (p = 0; p < all.length; p++) {
//            for (int x = 0; newstr[x] != null; x++) {
//                if (newstr[x].equals(all[p])) {
//                    continue x;
//                }
//            }
//            newstr[2] = all[p];
//            break x;
//        }
//        return newstr;
//}
