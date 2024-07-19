public class Main {
    public static void main(String[] args) {
        URLShortener urlShortener = new URLShortener();
        UserInterface ui = new UserInterface(urlShortener);
        ui.start();
    }
}
