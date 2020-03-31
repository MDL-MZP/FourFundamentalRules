package Funtion;


public class Ruler {

    public int getTrueLength(String []array){
        int len = 0;
        for(int i = 0;i<array.length;i++){
            if(array[i]!=null) len++;
        }
        return len;
    }


    public String arrayToString(String []strArray){
        String str = "";
        for(int i = 0;i<getTrueLength(strArray);i++){
            str += strArray[i];
        }
        return str;
    }


}