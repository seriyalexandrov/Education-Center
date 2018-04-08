import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.out.println();


        System.out.println("Count of strings which contain 'C': " +
                newCollection()
                        .stream()
                        .filter((s) -> s.contains("C"))
                        .count()
        );

        System.out.println("Find first string, which contain more than 3 characters: " +
                newCollection()
                        .stream()
                        .filter((s) -> s.length() > 3)
                        .findFirst()
                        .orElse("Not Found")
        );

        System.out.println("Get average string length (Map-Reduce): " +
                newCollection()
                        .stream()
                        .mapToInt(String::length)
                        .average().orElseThrow(() -> new IllegalArgumentException("Collection is empty"))
        );

        System.out.println("Get average string length (Map-Reduce): " +
                newCollection()
                        .stream()
                        .map((s) -> (s.length() > 2) ? s.substring(0, 3) : s)
                        .collect(Collectors.toSet())
        );
    }

    private static List<String> newCollection() {
        List<String> collection = new ArrayList<>();
        collection.add("A");
        collection.add("AB");
        collection.add("ABC");
        collection.add("ABCD");
        collection.add("ABCDE");
        collection.add("ABCDEF");
        return collection;
    }
}
