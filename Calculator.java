import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField inputField;
    private String num1 = "", num2 = "", operator = "";

    public Calculator() {
        setTitle("Simple Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inputField = new JTextField();
        inputField.setEditable(false);
        inputField.setFont(new Font("Arial", Font.BOLD, 24));
        add(inputField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();

        if (input.matches("[0-9]")) {
            inputField.setText(inputField.getText() + input);
        } else if (input.matches("[+\\-*/]")) {
            num1 = inputField.getText();
            operator = input;
            inputField.setText("");
        } else if (input.equals("=")) {
            num2 = inputField.getText();
            String result = "";

            switch (operator) {
                case "+":
                    result = addition(num1, num2);
                    break;
                case "-":
                    if (isSmaller(num1, num2)) {
                        result = "-" + subtractStrings(num2, num1);
                    } else {
                        result = subtractStrings(num1, num2);
                    }
                    break;
                case "*":
                    result = multiplication(num1, num2);
                    break;
                case "/":
                    result = divideStrings(num1, num2);
                    break;
            }
            inputField.setText(result);
        } else if (input.equals("C")) {
            inputField.setText("");
            num1 = num2 = operator = "";
        }
    }

    // Perform digit-by-digit string addition
    private String addition(String num1, String num2) {
        StringBuilder sb = new StringBuilder();

        int carry = 0;
        int i = num1.length() - 1, j = num2.length() - 1;

        while (i >= 0 || j >= 0 || carry != 0) {
            int digit1 = (i >= 0) ? num1.charAt(i--) - '0' : 0;
            int digit2 = (j >= 0) ? num2.charAt(j--) - '0' : 0;

            int sum = digit1 + digit2 + carry;
            sb.append(sum % 10);
            carry = sum / 10;
        }

        return sb.reverse().toString();
    }

    private String subtractStrings(String num1, String num2) {
        // Pad num2 with leading zeros if needed
        while (num2.length() < num1.length()) {
            num2 = "0" + num2;
        }
    
        StringBuilder sb = new StringBuilder();
        int borrow = 0;
    
        for (int i = num1.length() - 1; i >= 0; i--) {
            int digit1 = num1.charAt(i) - '0';
            int digit2 = num2.charAt(i) - '0' + borrow;
    
            if (digit1 < digit2) {
                digit1 += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
    
            sb.append(digit1 - digit2);
        }
    
        // Remove leading zeros
        while (sb.length() > 1 && sb.charAt(sb.length() - 1) == '0') {
            sb.deleteCharAt(sb.length() - 1);
        }
    
        return sb.reverse().toString();
    }    

    private boolean isSmaller(String a, String b) {
        if (a.length() != b.length()) {
            return a.length() < b.length();
        }
        return a.compareTo(b) < 0;
    }
    
    private String multiplication(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
    
        int len1 = num1.length();
        int len2 = num2.length();
        int[] result = new int[len1 + len2];
    
        for (int i = len1 - 1; i >= 0; i--) {
            int digit1 = num1.charAt(i) - '0';
            for (int j = len2 - 1; j >= 0; j--) {
                int digit2 = num2.charAt(j) - '0';
                int mul = digit1 * digit2;
                int sum = mul + result[i + j + 1];
    
                result[i + j + 1] = sum % 10;
                result[i + j] += sum / 10;
            }
        }
    
        StringBuilder sb = new StringBuilder();
        for (int val : result) {
            if (sb.length() == 0 && val == 0) continue; // Skip leading zero
            sb.append(val);
        }
    
        return sb.toString();
    }
    private String divideStrings(String num1, String num2) {
        if (num2.equals("0")) return "Error";
        if (compareStrings(num1, num2) < 0) return "0";
        if (num2.equals("1")) return num1;
    
        StringBuilder result = new StringBuilder();
        String dividend = "";
        
        for (int i = 0; i < num1.length(); i++) {
            dividend += num1.charAt(i);
            dividend = removeLeadingZeros(dividend);
            
            int quotientDigit = 0;
            while (compareStrings(dividend, num2) >= 0) {
                dividend = subtractStrings(dividend, num2);
                quotientDigit++;
            }
    
            result.append(quotientDigit);
        }
    
        return removeLeadingZeros(result.toString());
    }
    private int compareStrings(String a, String b) {
        if (a.length() != b.length()) {
            return a.length() - b.length();
        }
        return a.compareTo(b);
    }
    private String removeLeadingZeros(String s) {
        int i = 0;
        while (i < s.length() - 1 && s.charAt(i) == '0') {
            i++;
        }
        return s.substring(i);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
