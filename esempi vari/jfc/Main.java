package jfc;

import pizza.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int primo = 1;

//        Coppia<String, Integer> coppia = new Coppia<>("ciao", 3);
        Coppia<Boscaiola, String> coppia = new Coppia<>(new Boscaiola(false), "ciao");

        stampaCoppia(coppia);

        // ---------------------------------------------------------

//        Collection<String> strings = new;
//        List<String>, Set<String>, Queue<String>, Deque<String>, SortedSet<String>;

        List<String> strings = new ArrayList<>();
        Set<Integer> integers = new HashSet<>();
        String[] array = new String[10];

        /*Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
        }

        for (String s : strings) {

        }*/

        SortedMap<String, Boolean> map = new TreeMap<>();
        map.put("key", true);
        map.remove("key");

        SortedSet<String> set = new TreeSet<>();
    }

    public static void stampaCoppia(Coppia<?, String> coppia) {
        System.out.println(coppia.getPrimo() + coppia.getSecondo());
    }
}
