package Funtion;

public class Expression {

    private String operatorNumber[];//表示操作数 ,该字符串数组只存储操作数
    private String operator[];//表示操作符  ,该字符串数组只存储操作符，特别的第一个位置表示该表达式是否为操作符开头.
    private String answer; //表示为结果
    private String isrepeatExprssion[];//用于查重.
    private int isrepeatFlag = 0; //用于标识该表达式是否重复.

    public int getisrepeatFlag() {
        return isrepeatFlag;
    }


    public void setisrepeatFlag(int isrepeatFlag) {
        this.isrepeatFlag = isrepeatFlag;
    }


    public String[] getisrepeatExprssion() {
        return isrepeatExprssion;
    }


    public void setisrepeatExprssion(String[] isrepeatExprssion) {
        this.isrepeatExprssion = isrepeatExprssion;
    }
//    public Expression(){
//        operatorNumber = new String[5]; //为方便计算将所有数组初始化为空
//        for(int i = 0;i<operatorNumber.length;i++){
//            operatorNumber[i] = null;
//        }
//        operator = new String[6]; //为方便计算将所有数组初始化为空
//        for(int i = 0;i<operator.length;i++){
//            operator[i] = null;
//        }
//        isrepeatExprssion = new String[7];
//        for(int i = 0;i<isrepeatExprssion.length;i++){
//            isrepeatExprssion[i] = null;
//        }
//    }
   /* public String[] getOperatorNumber() {
        return operatorNumber;
    }
    public void setOperatorNumber(String[] operatorNumber) {
        this.operatorNumber = operatorNumber;
    }
    public String[] getOperator() {
        return operator;
    }
    public void setOperator(String[] operator) {
        this.operator = operator;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    *//**
     *
     * @return 以字符串的形式返回表达式
     *//*
    public String getExpression(){
        String expression  = "";
        int i=0,k = 0;
        if(operator[k]!=null) expression+=operator[k];
        k++;
        for(;operatorNumber[i]!=null;){
            expression+=operatorNumber[i++];
            if(operator[k]!=null) {
                expression+=operator[k];
                k++;
            }else{
                break;
            }
        }
        if(operatorNumber[i]!=null) expression+=operatorNumber[i];
        if(operator[k]!=null) expression+=operator[k];
        return expression;
    }

    *//**
     *
     * @return 以字符数组的形式返回表达式
     *//*
    public String[] toStringArray(){
        String [] result = new String[9];
        int i=0,k = 0,j=0;
        if(operator[k]!=null) result[j++] = operator[k];
        k++;
        for(;operatorNumber[i]!=null;){
            result[j++] = operatorNumber[i++];
            if(operator[k]!=null) {
                if(operator[k].length()>1){
                    result[j++] = operator[k].substring(0, 1);
                    result[j++] = operator[k].substring(1, 2);
                }else{
                    result[j++] = operator[k];
                }
                k++;
            }else{
                break;
            }
        }
        if(operatorNumber[i]!=null)  	result[j++] = operatorNumber[i];
        if(operator[k]!=null) result[j++] =operator[k];
        return result;
    }*/



}
