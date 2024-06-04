package org.example;
import java.sql.SQLException;
import java.util.Scanner;


public class InventorySystem {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductSystem PS = new ProductSystem();
    private static final SalesSystem SS = new SalesSystem();

    static String username;
    static String password;

    public static void main(String[] args) {
        System.out.println("Enter Username");
        username = scanner.nextLine();
        System.out.println("Enter Password");
        password = scanner.nextLine();

        try {
            User user = UserSystem.authenticate(username,password);
            if (user != null){
                System.out.println("Logged into: " + user.getUsername());
                if (user.getRole().equals("admin")){
                    adminMenu();
                } else {
                    adminMenu();
                }
            } else {
                System.out.println("Invalid Username or Password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void adminMenu(){
        while (true){
            System.out.println("""
                    1) Add Product
                    2) Search Product
                    3) View Products
                    4) Update Product
                    5) Delete Product
                    6) Record Sale
                    7) Generate Sale Reports
                    8) Exit
                    """);

            int input = Integer.parseInt(scanner.nextLine());
            switch (input){
                case 1 -> addProduct();
                case 2 -> searchProduct();
                case 3 -> viewProduct();
                case 4 -> updateProduct();
                case 5 -> deleteProduct();
                case 6 -> recordSale();
                case 7 -> generateSaleReport();
                case 8 -> System.exit(0);
                default -> System.out.println("Invalid, Try 1-8");
            }
        }
    }
    private static void userMenu(){
        while (true){
            System.out.println("""
                    1) Search Product
                    2) View Products
                    3) Record Sale
                    4) Exit
                    """);

            int input = Integer.parseInt(scanner.nextLine());
            switch (input){
                case 1 -> searchProduct();
                case 2 -> viewProduct();
                case 3 -> recordSale();
                case 4 -> System.exit(0);
                default -> System.out.println("Invalid, Try 1-4");
            }
        }
    }

    private static void generateSaleReport() {
    }

    private static void recordSale() {
    }

    private static void deleteProduct() {
    }

    private static void updateProduct() {
    }

    private static void viewProduct() {
    }

    private static void searchProduct() {
    }

    private static void addProduct() {
    }


}
