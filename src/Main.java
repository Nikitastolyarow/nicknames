import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {

        AtomicInteger countThreeWords = new AtomicInteger(0);
        AtomicInteger countFourWords = new AtomicInteger(0);
        AtomicInteger countFiveWords = new AtomicInteger(0);

        Random random = new Random();
        String[] texts = new String[100_000];

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread polindrome = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    switch (text.length()) {
                        case 3 -> countThreeWords.incrementAndGet();
                        case 4 -> countFourWords.incrementAndGet();
                        case 5 -> countFiveWords.incrementAndGet();
                    }
                }
            }
        });
        polindrome.start();

        Thread identical = new Thread(() -> {
            for (String text : texts) {
                if (isIdentical(text)) {
                    switch (text.length()) {
                        case 3 -> countThreeWords.incrementAndGet();
                        case 4 -> countFourWords.incrementAndGet();
                        case 5 -> countFiveWords.incrementAndGet();
                    }
                }
            }
        });
        identical.start();

        Thread ascending = new Thread(() -> {
            for (String text : texts) {
                if (isAscending(text)) {
                    switch (text.length()) {
                        case 3 -> countThreeWords.incrementAndGet();
                        case 4 -> countFourWords.incrementAndGet();
                        case 5 -> countFiveWords.incrementAndGet();
                    }
                }
            }
        });
        ascending.start();

        try {
            polindrome.join();
            identical.join();
            ascending.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Красивых никнеймов состоящих из 3 букв: " + countThreeWords.get());
        System.out.println("Красивых никнеймов состоящих из 4 букв: " + countFourWords.get());
        System.out.println("Красивых никнеймов состоящих из 5 букв: " + countFiveWords.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        int length = clean.length();
        int forward = 0;
        int backward = length - 1;
        while (backward > forward) {
            char forwardChar = clean.charAt(forward++);
            char backwardChar = clean.charAt(backward--);
            if (forwardChar != backwardChar)
                return false;
        }
        return true;
    }

    public static boolean isIdentical(String text) {
        char firstChar = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAscending(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
