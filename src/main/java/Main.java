import java.util.*;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Thread thread1 = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int j = 0;
                for (char c : route.toCharArray()) {
                    if (c == 'R') {
                        j++;
                    }
                }
                System.out.println("Количество поворотов направо: " + j);
                synchronized (sizeToFreq) {
                    sizeToFreq.put(j, sizeToFreq.computeIfAbsent(j, k -> 0) + 1);
                }
            });
            thread1.start();
            threads.add(thread1);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int maxValue = 0;
        Map.Entry<Integer, Integer> maxEntry = null;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxEntry = entry;
            }
        }

        System.out.println("Самое частое количество повторений " + maxEntry.getKey() + " (встретилось " + maxEntry.getValue() + " раз)");
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
        }

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
