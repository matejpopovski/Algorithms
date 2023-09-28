import java.util.Scanner;
import java.util.LinkedList;

public class HelloWorld {
    public static void main(String [] args) {
        Scanner input = new Scanner(System.in);
        LinkedList<String> strings = new LinkedList<String>();
        int n = 0;

        try{
            n = Integer.parseInt(input.nextLine());
        }
        catch (NumberFormatException nfe) {
            System.out.println("NumberFormat Exception: invalid input");
            return;
        }

        while (n-- > 0) {
            String s = input.nextLine();
            strings.add(s);
        }

        StringBuilder sb = new StringBuilder();
        for (String i : strings) {
            sb.append("Hello, ").append(i).append("!\n");
        }
        System.out.print(sb);

        input.close();
    }
}