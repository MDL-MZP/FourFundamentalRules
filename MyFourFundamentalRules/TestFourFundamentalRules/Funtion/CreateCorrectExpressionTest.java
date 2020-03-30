package Funtion;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


import static org.junit.Assert.*;

public class CreateCorrectExpressionTest {
    @Test
    public void testNormal() throws Exception{
//        File f1 = new File("e:/develop/theme/FourFundamentalRules/Exercise.txt");
//        FileWriter fw = new FileWriter(f1);
//        fw.flush();
//        String[] s = new String[30];
//        for(int i = 0; i < 20; i++) {
//            s[i] = CreateCorrectExpression2.createExpression(10);
//            System.out.println(s[i]);
//        }
//        FileIO.write(f1,s);
//        fw.close();
    }

        @Test
        public void testroughScreen(){
            String s1 = "11 + 222";//233.0
            String s2 = "12 - 234";//false
            String s3 = "21 ÷ 0.0";//false
            String s4 = "11 × 3";//33.0
            String s5 = "12 - 12";//0.0
            String s6 = "( 1 + 2 ) × 3 ";//7.0
            String s7 = "1 + 2 - 30";//6.0
            String s8 = "1 + 2 + 3 - 7";
            String s9 = "1 + 2 ÷ ( 33 - 33 )";
            String s10 = "1 + 2 ÷ 3 + 44";
            String s11 = "( 1 + 2 ) × ( 3 + 4 )";
            String result = "";
            String[] s = new String[30];
//            for(int i = 0; i < 20; i++) {
//                s[i] = CreateCorrectExpression2.createExpression(10);
//                result = new Screen().roughScreen(s[i]);
//                System.out.println(s[i] + " = " + result);
//            }
        }

    @Test
    public void testoutCorrectExpression()throws Exception{
        File f1 = new File("E:/develop/theme/FourFundamentalRules/Exercise.txt");


//        String[] s = FileIO.read(f1);
//        for(int j = 0; j < s.length && s[j] != null; j++){
//            System.out.print(s[j] + " ");
//        }

    }
        @Test
        public void test4(){
//        StringBuffer sb = new StringBuffer("12＋35-10÷18=");
//        String[] s = CorrectAnswer.priority(sb);
//        for(int i = 0; i < s.length && s[i] != null; i++) {
//            System.out.print(s[i] + " ");
//        }
            String s = "11 ＋ ( 23 - 12 ) = ";
            StringBuffer sb = new StringBuffer("(111＋23-15)×4=");
//            String[] st = CorrectAnswer.charAndIndex(sb);

            String[] st = CorrectAnswer.priority(sb);
//            String[] sd = {"1","＋","4","-"};
//            String[] s1 = CorrectAnswer.blanketsJudge(sd);
//        System.out.println(s1[0]);
//        System.out.println(s1[1]);
        System.out.println(st[0]);
        System.out.println(st[1]);
        System.out.println(st[2]);
        System.out.println(st[3]);
//        System.out.println(CorrectAnswer.answer(s));
    }
}