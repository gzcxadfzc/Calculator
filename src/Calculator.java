import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
public class Calculator {
    private String str;
    private Stack<String> operatorStack = new Stack<String>(); // 연산자 스택
    private ArrayList<String> postfix = new ArrayList<>(); // 후위표기식
    private Stack<Float> calStack = new Stack<Float>(); // 후위표기를 통해 계산위한 스택

    //연산자 우선순위 확인, 연산자가 아니면 -1 반환
    private int opCheck( String op ){
        int precedence = -1;
        switch(op) {
            case "+": case "-":
                precedence = 0;
                break;

            case "*": case "/" :
                precedence = 1;
                break;
        }
        return precedence;
    }
    public void start(){
        System.out.println("계산 식을 입력하세요 : ");
        Scanner in = new Scanner(System.in);
        str = in.nextLine();
        String [] arr = str.split("\\s");

        try{
            //입력값이 공백이거나 연산자로 끝날 때 예외
            if (arr.length == 0 || opCheck(arr[arr.length-1])!= -1) {
                throw new IllegalArgumentException();
            }

            for (int i = 0; i < arr.length; i++) {
                if (i % 2 == 0) {
                    // 숫자로 변환 안될 때 예외
                    try {
                        Float.parseFloat(arr[i]);
                    } catch (NumberFormatException e) {
                        //굳이 illegalargumentexception 안써도 될것같음
                        throw new IllegalArgumentException();
                    }
                    //홀수 번째 값은 숫자이므로 후위식에 바로 푸시
                    postfix.add(arr[i]);

                } else {
                    //짝수 번째에서 연산자가 아니면 예외발생
                    if (opCheck(arr[i]) == -1) {
                        throw new IllegalArgumentException();
                    }
                    // 연산자 우선순위 비교하여 연산자 스택에 푸시하거나 후위식에 저장
                    if (operatorStack.isEmpty()) {
                        operatorStack.push(arr[i]);
                    } else {
                        String tmp = operatorStack.peek();
                        if (opCheck(tmp) >= opCheck(arr[i])) {
                            postfix.add(operatorStack.pop());
                            operatorStack.push(arr[i]);
                        } else {
                            operatorStack.push(arr[i]);
                        }
                    }
                }
            }
            while (!operatorStack.isEmpty()) {
                postfix.add(operatorStack.pop());
            }

            //후위식을 통한 계산. 스택 사용
            for (int i = 0; i < postfix.size(); i++) {
                String current = postfix.get(i);
                if (opCheck(current) == -1) {
                    float f = Float.parseFloat(current);
                    calStack.push(f);
                } else {
                    float tmp1 = calStack.pop();
                    float tmp2 = calStack.pop();
                    switch (current) {
                        case "+":
                            calStack.push(tmp2 + tmp1);
                            break;
                        case "-":
                            calStack.push(tmp2 - tmp1);
                            break;
                        case "*":
                            calStack.push(tmp2 * tmp1);
                            break;
                        case "/":
                            calStack.push(tmp2 / tmp1);
                            break;
                    }
                }
            }
            System.out.println(calStack.peek());
        }catch (IllegalArgumentException e){
            System.out.println("wrong input");
        }
    }


    public static void main(String[] args){
        Calculator cal = new Calculator();
        cal.start();
    }


}
