
import java.util.*;
import java.util.stream.Collectors;
import static java.util.Arrays.asList;

public class Lecture2 {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };

    public static void main(String[] args) {
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();
        
        //TASK1
        task1();

        //TASK2
        System.out.println("===Task2===");
        System.out.println(task2(asList(3, 4, 2, 7), 10)); // [3, 7]

        //tests
        // System.out.println("============");
        // System.out.println("Input: [1, 2, 3, 4, 5], 6");
        // System.out.println("Expected output: [1, 5]");
        // System.out.print("My output: ");
        // System.out.println(task2(asList(1, 2, 3, 4, 5), 6));

        // System.out.println("============");
        // System.out.println("Input: null, 6");
        // System.out.println("Expected output: null");
        // System.out.print("My output: ");
        // System.out.println(task2(null, 6));

        //TASK3
        System.out.println("===Task3===");
        System.out.println(fuzzySearch("asd", "a1s1d1")); //true

        //tests
        // System.out.println(fuzzySearch("car", "ca6$$#_rtwheel")); // true
        // System.out.println(fuzzySearch("cwhl", "cartwheel")); // true
        // System.out.println(fuzzySearch("cwhee", "cartwheel")); // true
        // System.out.println(fuzzySearch("cartwheel", "cartwheel")); // true
        // System.out.println(fuzzySearch("", "cartwheel")); // true
        // System.out.println(fuzzySearch("", "")); // true
        // System.out.println(fuzzySearch("cwheeel", "cartwheel")); // false
        // System.out.println(fuzzySearch("lw", "cartwheel")); // false
        // System.out.println(fuzzySearch("lw", "")); // false

    }

    static void task1 () {
        
        if (RAW_DATA == null || RAW_DATA.length == 0)
            return;

        Map<String, Long> result = Arrays.asList(RAW_DATA).stream()
            .filter(Objects::nonNull)
            .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
            .sorted((o1, o2) -> (o1.getName().equals(o2.getName())) ? o2.getId() - o1.getId() : 1)
            .distinct()
            .collect(Collectors.groupingBy(Person::getName, LinkedHashMap::new, Collectors.counting()));       

        for (var entry : result.entrySet()) {
            System.out.println("Key: " + entry.getKey());
            System.out.println("Value: " + entry.getValue());
        }
        
    }

    static List<Integer> task2 (List<Integer> input, int target) {

        if (input == null || input.size() == 0)
            return null;

        List<Integer> result = new ArrayList<>(2);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < input.size(); i++) {
            map.put(target - input.get(i), input.get(i));
        }
        for (Integer number : input) {
            if (map.containsKey(number)) {
                result.add(number);
                result.add(map.get(number));
                return result;
            }
        }

        return null;
    }

    static boolean fuzzySearch(String s1, String s2) {

        int i = 0;
        int j = 0;

        while (i < s1.length() && j < s2.length()) {
            if (s1.charAt(i) == s2.charAt(j)) {
                i++; 
                j++;
            } else {
                j++;
            }
        }

        if (j == s2.length() && i < s1.length())
            return false;
        else 
            return true;

    }
}
