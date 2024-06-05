package org.example;
import java.sql.SQLException;
import java.util.ArrayList;
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
                    userMenu();
                }
            } else {
                System.out.println("Invalid Username or Password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void adminMenu(){
        while(true){
            try {
                while (true){
                    System.out.println("""
                    
                    1) Add Product
                    2) Search Product
                    3) View Products
                    4) Update Product
                    5) Delete Product
                    6) Record Sale
                    7) Generate Sale Reports
                    8) Generate Inventory Reports
                    9) Exit
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
                        case 8 -> generateInventorySaleReport();
                        case 9 -> System.exit(0);
                        default -> System.out.println("Invalid, Try 1-9");
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid, Try number 1-9");
            }
        }
    }
    private static void userMenu(){
        while (true){
            try {
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
            } catch (NumberFormatException e){
                System.out.println("Invalid, Try number 1-4");
            }
        }
    }
    private static void generateInventorySaleReport() {
        ArrayList<String> saleProducts = SS.getSaleInventoryReport();
        System.out.println("\n--== Currently Viewing All Sales of Products==--");
        for (String saleProduct : saleProducts){
            System.out.println(saleProduct);
        }
    }

    private static void generateSaleReport() {
        ArrayList<Sales> sales = SS.getSalesReport();
        System.out.println("\n--== Currently Viewing All Sales ==--");
        for (Sales sale : sales){
            System.out.println(sale);
        }
    }

    private static void recordSale() {
        System.out.println("\n--== Currently Recording Sale ==--");
        System.out.println("Enter Product Id");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter Product Quantity");
        int quantity = Integer.parseInt(scanner.nextLine());
        SS.RecordSale(id,quantity);
    }

    private static void deleteProduct() {
        System.out.println("\n--== Currently Deleting Product ==--");
        System.out.println("Enter Product Name");
        String name = scanner.nextLine();
        PS.deleteProduct(name);
    }

    private static void updateProduct() {
        System.out.println("\n--== Currently Updating Product ==--");
        System.out.println("Enter Product Id");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter Product Name");
        String name = scanner.nextLine();
        System.out.println("Enter Product Quantity");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter Product Price");
        double price = Double.parseDouble(scanner.nextLine());
        PS.updateProduct(id,name,quantity,price);
        System.out.println(PS.getProduct(name));
    }

    private static void viewProduct() {
        ArrayList<Product> products = PS.getAllProduct();
        System.out.println("\n--== Currently Viewing All Products ==--");
        for (Product product : products){
            System.out.println(product);
        }
    }

    private static void searchProduct() {
        System.out.println("\n--== Currently Searching Product ==--");
        System.out.println("Enter Product Name");
        String name = scanner.nextLine();
        if (PS.getProduct(name) != null){
            System.out.println(PS.getProduct(name));
        }
    }

    private static void addProduct() {
        System.out.println("\n--== Currently Adding Product ==--");
        System.out.println("Enter Product Name");
        String name = scanner.nextLine();
        System.out.println("Enter Product Quantity");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter Product Price");
        double price = Double.parseDouble(scanner.nextLine());
        PS.addProduct(new Product(0,name,quantity,price));
    }


}
