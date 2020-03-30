import Funtion.Screen;
import org.junit.Test;

public class ScreenUnitlTest {
    @Test
    public void testjudgeExpression(){
        String s1 = "( 1 + 4 + 7 )";
        boolean b = new Screen().baseExpression(s1);
        System.out.println(b);
    }

    @Test
    public void testsplitExpression(){
        String s1 = "1 + 2 + 3 - 7";
        String[] test = new Screen().splitExpression(s1);
        for (int i =0;i < test.length;i++)
        System.out.println(test[i]);
    }

    @Test
    public void testoperator(){
        String s = "1 + 2";//1
        String s1 = "( 1 + 2 ) ÷ 3 + 5";//257
        String s2 = "1 ÷ ( 2 ÷ 3 )";//41
        String s3 = "1 + 2 ÷ 3";//31
        String s4 = "( 1 + 2 ) ÷ ( 2 - 6 )";//285
        String s5 = "( ( 1 + 2 ) - 6 ) + 6";//369
        String s6 = "2 ÷ ( 1 + ( 1 - 4 ) )";//741
        String s7 = "1 + 1 ÷ 3 + 4";
//        int a = new Screen().operator(s6);
//        System.out.println(a);
       /* for(int i =0; i < a.length;i++){
            System.out.println(a[i]);
        }*/
    }

    @Test
    public void testespressionResult(){
        String s1 = "1 + 2";//3.0
        String s2 = "1 ÷ 0";//不存在
        String s3 = "1 × 100";//100.0
        String s4 = "19 - 2";//17.0
        String result = new Screen().espressionResult(s4);
        System.out.println(result);
    }

    @Test
    public void testdeleteBracket(){
        String s1 = "1 + 3";//1 + 3
        String s2 = "( 1 + 3 )";//1 + 3
        String s3 = "( 1 + ( 1 + 3 ) )";//1 + ( 1 + 3 )
        String exp = new Screen().deleteBracket(s2);
        System.out.println(exp);
    }

    @Test
    public void testroughScreen(){
        String s1 = "8 + 3";//233.0
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
        result = new Screen().roughScreen("8 ＋ 3");
        System.out.println(result);
    }
}
