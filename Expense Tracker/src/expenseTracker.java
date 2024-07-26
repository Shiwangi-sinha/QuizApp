import java.io.*;
import java.util.*;

public class expenseTracker {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String USER_DATA_FILE = "users.txt";
    private static final String EXPENSE_DATA_FILE = "expenses.txt";

    private static Map<String, String> users = new HashMap<>();
    private static List<Expense> expenses = new ArrayList<>();
    private static String loggedInUser = null;

    public static void main(String[] args) {
        loadUserData();
        loadExpenseData();

        while (true) {
            if (loggedInUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                registerUser();
                break;
            case 2:
                loginUser();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void showMainMenu() {
        System.out.println("\nWelcome, " + loggedInUser);
        System.out.println("1. Add Expense");
        System.out.println("2. View Expenses");
        System.out.println("3. View Category-wise Summary");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                addExpense();
                break;
            case 2:
                viewExpenses();
                break;
            case 3:
                viewCategorySummary();
                break;
            case 4:
                loggedInUser = null;
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please try again.");
        } else {
            users.put(username, password);
            saveUserData();
            System.out.println("Registration successful. Please login.");
        }
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            loggedInUser = username;
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void addExpense() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        expenses.add(new Expense(loggedInUser, date, category, amount));
        saveExpenseData();
        System.out.println("Expense added successfully.");
    }

    private static void viewExpenses() {
        System.out.println("\nExpenses:");
        for (Expense expense : expenses) {
            if (expense.getUsername().equals(loggedInUser)) {
                System.out.println(expense);
            }
        }
    }

    private static void viewCategorySummary() {
        Map<String, Double> categorySummary = new HashMap<>();
        for (Expense expense : expenses) {
            if (expense.getUsername().equals(loggedInUser)) {
                categorySummary.put(expense.getCategory(),
                        categorySummary.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
            }
        }

        System.out.println("\nCategory-wise Summary:");
        for (Map.Entry<String, Double> entry : categorySummary.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                users.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println("No user data found. Starting fresh.");
        }
    }

    private static void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving user data.");
        }
    }

    private static void loadExpenseData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(EXPENSE_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                expenses.add(new Expense(parts[0], parts[1], parts[2], Double.parseDouble(parts[3])));
            }
        } catch (IOException e) {
            System.out.println("No expense data found. Starting fresh.");
        }
    }

    private static void saveExpenseData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EXPENSE_DATA_FILE))) {
            for (Expense expense : expenses) {
                writer.write(expense.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expense data.");
        }
    }
}

class Expense {
    private String username;
    private String date;
    private String category;
    private double amount;

    public Expense(String username, String date, String category, double amount) {
        this.username = username;
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return date + " | " + category + " | " + amount;
    }

    public String toCSV() {
        return username + "," + date + "," + category + "," + amount;
    }
}
