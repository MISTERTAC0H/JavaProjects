import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * calculator app that can handle any length number
 */

public class Calculator extends JFrame implements ActionListener {
    private JTextField inputField;
    private double num1, num2, result;
    private String operator;

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
    /*
     * math section
     * change how numbers are stored from double to a string
     * parse through strNum1 and strNum2 and add num by num in each string
     * start at end of string working towards the front 
     * tempNum = strNum1.getCharAt(strNum1.length-1) + strNum2.getCharAt(strNum2.length-1)
     */
    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();

        if (input.matches("[0-9]")) {
            inputField.setText(inputField.getText() + input);
        } else if (input.matches("[+\\-*/]")) {
            num1 = Double.parseDouble(inputField.getText());
            operator = input;
            inputField.setText("");
        } else if (input.equals("=")) {
            num2 = Double.parseDouble(inputField.getText());
            switch (operator) {
                case "+": result = num1 + num2; break;
                case "-": result = num1 - num2; break;
                case "*": result = num1 * num2; break;
                case "/": 
                    if (num2 == 0) {
                        inputField.setText("Error");
                        return;
                    }
                    result = num1 / num2; break;
            }
            inputField.setText(String.valueOf(result));
        } else if (input.equals("C")) {
            inputField.setText("");
            num1 = num2 = result = 0;
            operator = null;
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
