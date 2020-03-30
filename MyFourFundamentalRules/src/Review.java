import Funtion.Accuracy;
import Funtion.CorrectAnswer;
import Funtion.CreateCorrectExpression2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Review {
    public static void main(String[] args) throws Exception{
        //RandomTest();
        //read();
        //create();
        int s = Integer.parseInt("14");
        int ss = Integer.parseInt("13");
        System.out.println(s+ss);

        String str = "(2+3)+1";
        StringBuffer st = new StringBuffer(str);
        //                        l+1,r
        String temp = st.substring(1,4);
        System.out.println(temp);

        int t = 0;
        String[] tmp = new String[6];
        String[] s1 = {"1","4","9","7"};
        String[] s2 = {"9","4"};
        //  完全覆盖
        s1 = s2;
        for(t=0;t<s1.length && s1[t]!=null;t++) {
            System.out.println(s1[t]);
        }
        System.out.println("------------");
        String[] stri = {"(","1","＋","2",")","×","3","-","4","="};
        String s3 = "＋-×÷";
        String[] character = {"2","5","7"};
        for(int i = 0; i < stri.length;){
            if("(".equals(stri[i]) || ")".equals(stri[i])){
                for(int j = i; j < stri.length-1; j++) {
                    stri[j] = stri[j + 1];
                }
                for(int j = 0; j < character.length; j++){
                    int n = Integer.parseInt(character[j]);
                    if(n > i){
                        character[j] = String.valueOf(--n);
                    }
                }
            }else{
                i++;
            }
        }

        for(int i = 0; i < stri.length; i++){
            System.out.print(stri[i]);
        }
        System.out.println();
        for(int i = 0; i < character.length; i++){
            System.out.println(character[i]);
        }
        String strt = "1230";
        String[] td = new String[2];
        td = strt.split(" ");
        System.out.println(td[0]);
        System.out.println(td[1]);
    }

    /**            Random             */
    public static void RandomTest(){
        int t;
        Random r = new Random();
        for(int i = 0; i < 10 ; i++) {
            //    范围  [0,10)
            t = r.nextInt(10);
            System.out.println(t);
        }
    }

    public static void test(String a){
        a = "test";
    }

    /**   辗转相除 求最大公约数,b大a小 */
    public static int t(int a,int b){
        int temp;
        while(true) {
             temp = b % a;
            if (temp == 0) {
                return a;
            } else {
                b = a;
                a = temp;
            }
        }
    }

}
