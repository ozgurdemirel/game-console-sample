package com.company.util;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ChoiceScanner {

    private String prompt;
    private Pattern pattern;
    private String warning;
    private Scanner scanner = new Scanner(System.in);

    public ChoiceScanner(String prompt, String regex, String warning) {
        this.prompt = prompt;
        this.pattern = Pattern.compile(regex);
        this.warning = warning;
    }

    public ChoiceScanner(String prompt, String regex) {
        this(prompt, regex, "\t!Invalid command please try again...!!!");
    }

    public String askUserForNumberInput() {
        boolean validChoice = false;
        String value = "";
        System.out.print(this.prompt);
        while (!validChoice) {
            if (!scanner.hasNextLine()) {
                break;
            }
            value = scanner.nextLine();
            if (!value.isEmpty()) {//dirty fix, lack of time
                validChoice = pattern.matcher(value).matches();
            }else  {
                System.out.println(warning);
            }

            if (!value.isEmpty() && !validChoice) {//dirty fix
                System.out.println(warning);
            }

        }
        return value.trim();
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public static void p(String text) {
        System.out.println(text);
    }


}
