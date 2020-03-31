package Funtion;

import java.io.File;
import java.io.FileWriter;

/**
 * @author Mazin
 * 正确率Result
 */
public class Accuracy{

   /**    输出正确率到文件中，需要比较userAnswers和Answers  */
    public static void result(String[] userAnswers,File grade) throws Exception {
        //  拿到正确答案集，和用户答案做比较
        int correctNum = 0;
        int wrongNum = 0;
        String correctIndex = "";
        String wrongIndex = "";
        String[] answers = CreateCorrectExpression.getAnswers();
        String[] write = new String[2];
        for (int i = 0; i < answers.length && answers[i] != null; i++) {
            if (answers[i].equals(userAnswers[i])) {
                correctNum++;
                //  +1 是为了达到正确的题目序号
                correctIndex += i + 1 + " ,";
            } else {
                wrongNum++;
                wrongIndex += i + 1 + " ,";
            }
        }
        StringBuffer s1 = new StringBuffer(correctIndex);
        StringBuffer s2 = new StringBuffer(wrongIndex);
        if (correctNum != 0){
            s1.insert(0, "( ");
            //  取代末尾的  , 变为 ）
            s1.replace(s1.length()-1,s1.length(),")");
        }
        if(wrongNum != 0) {
            s2.insert(0, "(");
            s2.replace(s2.length()-1,s2.length(),")");
        }
        correctIndex = s1.toString();
        wrongIndex = s2.toString();
        write[0] = "Correct : " + correctNum + " " + correctIndex;
        write[1] = "Wrong : " + wrongNum + " " + wrongIndex;
        write(grade,write);
    }

    /**   专门用于Grade文件的写     */
    private static void write(File fileName,String[] content) throws Exception{
        FileWriter fw = new FileWriter(fileName);
        for(int i = 0; i < content.length; i++) {
            fw.write(content[i]);
            fw.write("\n");
        }
        fw.close();
    }

}
