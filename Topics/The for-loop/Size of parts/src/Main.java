import java.util.Scanner;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        // put your code here

        var map = new Scanner(System.in)
                .useDelimiter("\n")
                .tokens()
                .skip(1)
                .collect(Collectors.groupingBy(x -> x, Collectors.summingInt(s -> 1)));

        String result = "" + map.getOrDefault("0", 0) + " "
                + map.getOrDefault("1", 0) + " "
                + map.getOrDefault("-1", 0);

        System.out.println(result);
    }
}