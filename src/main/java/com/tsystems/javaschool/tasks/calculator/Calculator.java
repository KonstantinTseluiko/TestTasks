package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayList;
import java.util.List;
import java.math.*;

public class Calculator {
    private  static final int LEFT_BRACKET = 0;
    private  static final int RIGHT_BRACKET = 1;
    private  static final int PLUS = 2;
    private  static final int MINUS = 3;
    private  static final int MULT = 4;
    private  static final int DIVIDE = 5;
    private  static final int NUMBER = 6;
    private  static final int END = 7;
    public static String stringExp;
    public static int countDot;
    static List<Lexeme> lexemes = new ArrayList<>();

    public void analysExpression(String expr) throws CalcException {
        StringBuilder sb = new StringBuilder();
        String [] strings = expr.split("\\s");
        for (String string : strings) {
            sb.append(string);
        }
        stringExp = sb.toString();
        for (int pos = 0; pos < stringExp.length(); pos++) {
            if (stringExp.charAt(pos) =='('){
                Lexeme lex = new Lexeme(LEFT_BRACKET,"(");
                lexemes.add(lex);
            }
            else if(stringExp.charAt(pos)==')'){
                Lexeme lex = new Lexeme(RIGHT_BRACKET,")");
                lexemes.add(lex);
            }else if(stringExp.charAt(pos)=='+'){
                Lexeme lex = new Lexeme(PLUS,"+");
                lexemes.add(lex);
            }else if(stringExp.charAt(pos)== '-'){
                Lexeme lex = new Lexeme(MINUS,"-");
                lexemes.add(lex);
            }else if(stringExp.charAt(pos)=='*'){
                Lexeme lex = new Lexeme(MULT,"*");
                lexemes.add(lex);
            }else if(stringExp.charAt(pos)=='/'){
                Lexeme lex = new Lexeme(DIVIDE,"/");
                lexemes.add(lex);
            }
            else {
                if (!Character.isDigit(stringExp.charAt(pos))){
                    throw new RuntimeException("not expresson");
                }

                StringBuilder number = new StringBuilder();
                    countDot = 0;
                do{
                    if (Character.isDigit(stringExp.charAt(pos))){
                        number.append(stringExp.charAt(pos));
                    }
                    else if (stringExp.charAt(pos) == '(' || stringExp.charAt(pos) == ')' ||
                            stringExp.charAt(pos) == '*' || stringExp.charAt(pos) == '/' || stringExp.charAt(pos) == '+'||
                            stringExp.charAt(pos) == '-'){
                        break;
                    }
                    else if (stringExp.charAt(pos)== '.'){
                        countDot++;
                        if (countDot > 1){
                            throw new CalcException();
                        }
                        else{
                            number.append(stringExp.charAt(pos));
                        }
                    }
                    else{
                        throw new CalcException();
                    }
                    pos++;
                }while (pos < stringExp.length());
                pos--;
                Lexeme lex = new Lexeme(NUMBER, number.toString());
                lexemes.add(lex);
            }
        }
        lexemes.add(new Lexeme(END, null));
    }

    public static float expression(RulerBuffer ruler) throws CalcException{
        Lexeme lexeme = ruler.nextDigit();
        if(lexeme.type == END){
            return 0;
        }
        else{
            ruler.back();
            return plusAndMinus(ruler);
        }
    }

    public static float decider(RulerBuffer ruler) throws CalcException {
        Lexeme lexeme = ruler.nextDigit();
        if(lexeme.type == NUMBER){
            return Float.parseFloat(lexeme.value);
        }
        else if(lexeme.type == LEFT_BRACKET){
            float result = plusAndMinus(ruler);
            lexeme = ruler.nextDigit();
            if(lexeme.type != RIGHT_BRACKET){
                throw new RuntimeException();
            }
            return result;
        }
        else {
           throw new CalcException();
        }

    }

    public static float plusAndMinus(RulerBuffer ruler) throws CalcException{
        float value = multAndDivide(ruler);
        while (true) {
            Lexeme lexeme = ruler.nextDigit();
            if(lexeme.type == PLUS){
                value += multAndDivide(ruler);
            }
            else if(lexeme.type == MINUS){
                value -= multAndDivide(ruler);
            }
            else if(lexeme.type == END||lexeme.type== NUMBER||lexeme.type== DIVIDE||lexeme.type == RIGHT_BRACKET){
                ruler.back();
                return value;
            }
            else {
                throw new CalcException();
            }
        }
    }

    public static float multAndDivide(RulerBuffer ruler) throws CalcException{
        float value = decider(ruler);
        while (true){
            Lexeme lexeme = ruler.nextDigit();
            if(lexeme.type == MULT){
                value *= decider(ruler);
            }
            else if(lexeme.type == DIVIDE){
                value /= decider(ruler);
            }
            else if(lexeme.type == PLUS||lexeme.type == END||lexeme.type== NUMBER||lexeme.type == RIGHT_BRACKET||lexeme.type == MINUS){
                ruler.back();
                return value;
            }
            else {
                throw new CalcException();
            }
        }

    }

    static class Lexeme{
        int type;
        String value;

        public Lexeme(int type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString(){
            StringBuilder result = new StringBuilder();
            result.append("Type: ").append(type);
            result.append(" Value: ").append(value);
            return  result.toString();
        }
    }

    static class RulerBuffer{

        private int counter;

        private List<Lexeme> lexemes;

        public RulerBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme nextDigit(){
            return lexemes.get(counter++);
        }

        public void back(){
            counter--;
        }

    }

    static class CalcException extends Throwable{

        public String meExp(){
            return null;
        }

    }
    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement){
        try {
            analysExpression(statement);
            if (countDot>0){
                float floatResult = (float) (Math.round(expression(new RulerBuffer(lexemes)) * Math.pow(10, 4)) / Math.pow(10, 4));
                lexemes.clear();
                return String.valueOf(floatResult);
            }
            else {
                int result = (int) expression(new RulerBuffer(lexemes));
                lexemes.clear();
                return String.valueOf(result);
            }
        }
        catch (CalcException | NullPointerException exception){
            return null;
        }

    }
}

