import java.util.HashMap;
import java.util.Random;

public class URLShortener {
    private HashMap<String, String> urlMap;
    private HashMap<String, String> shortToLongMap;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();

    public URLShortener() {
        urlMap = new HashMap<>();
        shortToLongMap = new HashMap<>();
    }

    public String shortenURL(String longURL) {
        if (longURL == null || longURL.isEmpty()) {
            throw new IllegalArgumentException("Invalid URL");
        }

        if (urlMap.containsKey(longURL)) {
            return urlMap.get(longURL);
        }

        String shortURL;
        do {
            shortURL = generateShortURL();
        } while (shortToLongMap.containsKey(shortURL));

        urlMap.put(longURL, shortURL);
        shortToLongMap.put(shortURL, longURL);
        return shortURL;
    }

    public String expandURL(String shortURL) {
        if (shortURL == null || shortURL.isEmpty() || !shortToLongMap.containsKey(shortURL)) {
            return "URL not found";
        }
        return shortToLongMap.get(shortURL);
    }

    private String generateShortURL() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(ALPHABET.charAt(rand.nextInt(BASE)));
        }
        return sb.toString();
    }
}
