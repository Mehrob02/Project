package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class HelloController implements Initializable {
    @FXML
    public Button btn;
    public Button btn2;
    public Button btn_1;
    public Button btn_0;
    public Button btn_2;
    public Button btn_3;
    public Button btn_4;
    public Button btn_5;
    public Button btn_6;
    public Button btn_7;
    public Button btn_8;
    public Button btn_9;
    public Button deleteBtn;
    public Button clearBtn;
    public TextField mahmulatorField;
    public Button btn_plus,btn_minus,btn_mul,btn_div,btn_equal,btn_dot;
    public Button [] btnNumbers = {btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9};
    public BorderPane mahmulator_BP;
    public CheckBox pin;
    public boolean is_pinned =true;
    public HelloApplication helloApplication = new HelloApplication();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnNumbersClicked(ActionEvent actionEvent) {
            if (actionEvent.getSource().equals(btn_0)) {
                mahmulatorField.setText(mahmulatorField.getText().concat(btn_0.getText()));
            } else if (actionEvent.getSource().equals(btn_1)) {
                mahmulatorField.setText(mahmulatorField.getText().concat(btn_1.getText()));
            }else if (actionEvent.getSource().equals(btn_2)) {
                mahmulatorField.setText(mahmulatorField.getText().concat(btn_2.getText()));
            }else if (actionEvent.getSource().equals(btn_3)) {
                mahmulatorField.setText(mahmulatorField.getText().concat(btn_3.getText()));
            }else if (actionEvent.getSource().equals(btn_4)) {
                mahmulatorField.setText(mahmulatorField.getText().concat(btn_4.getText()));
            }else if (actionEvent.getSource().equals(btn_5)) {
                mahmulatorField.setText(mahmulatorField.getText().concat(btn_5.getText()));
            }else if (actionEvent.getSource().equals(btn_6)) {
                mahmulatorField.setText(mahmulatorField.getText().concat(btn_6.getText()));
            }else if (actionEvent.getSource().equals(btn_7)) {
                mahmulatorField.setText(mahmulatorField.getText().concat(btn_7.getText()));
            }else if (actionEvent.getSource().equals(btn_8)) {
                mahmulatorField.setText(mahmulatorField.getText().concat(btn_8.getText()));
            }else if (actionEvent.getSource().equals(btn_9)) {
                mahmulatorField.setText(mahmulatorField.getText().concat(btn_9.getText()));
            }
    }
    public void btnOperationClicked(ActionEvent actionEvent){
        if (actionEvent.getSource().equals(deleteBtn)) {
            String string = mahmulatorField.getText();
            mahmulatorField.setText("");
            for (int i = 0; i < string.length() - 1; i++) {
                mahmulatorField.setText(mahmulatorField.getText() + string.charAt(i));
            }
        } else if (actionEvent.getSource().equals(btn_equal)) {
            try {
                mahmulatorField.setText(solve(mahmulatorField.getText()));
            }catch (Exception exception){
                System.out.println("error");
            }
        }else if (mahmulatorField.getText().isEmpty()) {
            System.out.println("empty");
        } else if (mahmulatorField.getText().endsWith(".")) {
            System.out.println("no");
        } else if (mahmulatorField.getText().endsWith("/") || mahmulatorField.getText().endsWith("+") || mahmulatorField.getText().endsWith("-") || mahmulatorField.getText().endsWith("*")) {
            System.out.println("no");
        }
        else{
        if (actionEvent.getSource().equals(btn_plus)) {
            mahmulatorField.setText(mahmulatorField.getText().concat(btn_plus.getText()));
        }else if (actionEvent.getSource().equals(btn_minus)) {
             mahmulatorField.setText(mahmulatorField.getText().concat(btn_minus.getText()));
         }else if (actionEvent.getSource().equals(btn_mul)) {
             mahmulatorField.setText(mahmulatorField.getText().concat(btn_mul.getText()));
         }else if (actionEvent.getSource().equals(btn_div)) {
             mahmulatorField.setText(mahmulatorField.getText().concat(btn_div.getText()));
         }else if (actionEvent.getSource().equals(btn_dot)) {
             mahmulatorField.setText(mahmulatorField.getText().concat(btn_dot.getText()));
         }else if (actionEvent.getSource().equals(clearBtn)) {
             mahmulatorField.setText("");
         }}

    }
    public void pinClicked(ActionEvent actionEvent) {
        helloApplication.stagePin(is_pinned);
    }
    public static String solve(String expression) {
        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        StringBuilder number = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                number.append(c);
                if (i == expression.length() - 1) {
                    operands.push(Double.parseDouble(number.toString()));
                }
            } else {
                if (!number.isEmpty()) {
                    operands.push(Double.parseDouble(number.toString()));
                    number.setLength(0);
                }
                if (c == '+' || c == '-' || c == '*' || c == '/') {
                    if (c == '-' && (i == 0 || expression.charAt(i - 1) == '(' || isOperator(expression.charAt(i - 1)))) {
                        number.append(c);
                    } else {
                        while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                            processOperator(operands, operators);
                        }
                        operators.push(c);
                    }
                }
            }
        }

        while (!operators.isEmpty()) {
            processOperator(operands, operators);
        }
        double result = operands.pop();
        if (result == (int) result) {
            return Integer.toString((int) result);
        } else {
            return Double.toString(result);
        }
    }

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }


    public static void processOperator(Stack<Double> operands, Stack<Character> operators) {
        double b = operands.pop();
        double a = operands.pop();
        char operator = operators.pop();
        double result = applyOperator(a, b, operator);
        operands.push(result);
    }

    public static double applyOperator(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new IllegalArgumentException("Деление на 0");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Ошибка");
        }
    }

    public static boolean hasPrecedence(char op1, char op2) {
        return (op2 != '(' && op2 != ')') && ((op1 == '*' || op1 == '/') || (op2 == '+' || op2 == '-'));
    }
}