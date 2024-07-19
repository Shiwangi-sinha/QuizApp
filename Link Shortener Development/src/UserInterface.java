import java.util.Scanner;

public class UserInterface {
    private URLShortener urlShortener;
    private Scanner scanner;

    public UserInterface(URLShortener urlShortener) {
        this.urlShortener = urlShortener;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.println("Enter the long URL:");
                        String longURL = scanner.nextLine();
                        String shortURL = urlShortener.shortenURL(longURL);
                        System.out.println("Short URL: " + shortURL);
                        break;
                    case 2:
                        System.out.println("Enter the short URL:");
                        String url = scanner.nextLine();
                        String originalURL = urlShortener.expandURL(url);
                        System.out.println("Original URL: " + originalURL);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
