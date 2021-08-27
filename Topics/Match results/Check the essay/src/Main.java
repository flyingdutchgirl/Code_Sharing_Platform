import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class FindTheKeys {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String text = scanner.nextLine();

        // write your code here
        findTheKeys(text).forEach(System.out::println);

    }

    public static List<String> findTheKeys(String text) {
        List<String> keys = new ArrayList<>();

        String boundary = "\\b&&\\S";

        String startPattern = "the\\s+key\\s+is\\s+";
        String vowelsAndSymbols = "([?!#aeiou])" + boundary;
        String consonantsAndDigits = "([0-9a-z&&[^aeiou]]+)" + boundary;
        String typeOne = startPattern + consonantsAndDigits;
        String typeTwo = startPattern + vowelsAndSymbols;

        Pattern patternOne = Pattern.compile(typeOne, Pattern.CASE_INSENSITIVE);
        Pattern patternTwo = Pattern.compile(typeTwo, Pattern.CASE_INSENSITIVE);

        Matcher matcherOne = patternOne.matcher(text);
        Matcher matcherTwo = patternTwo.matcher(text);

        while (matcherOne.find()) {
            keys.add(matcherOne.group(1));
        }

        while (matcherTwo.find()) {
            keys.add(matcherTwo.group(1));
        }

        return keys;
    }

}