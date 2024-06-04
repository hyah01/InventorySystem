import org.example.Product;
import org.example.ProductSystem;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestProduct {


    @Test
    public void testLookUpProduct(){
        ProductSystem test = new ProductSystem();
        Product p1 = new Product(1,"Laptop",200,499.99);
        Product p2 = test.getProduct("Laptop");
        assertEquals(p1.getName(),p2.getName());
        assertEquals(p1.getId(),p2.getId());
    }
    @Test
    public void testGetProducts(){
        ProductSystem test = new ProductSystem();
        ArrayList<Product> products = test.getAllProduct();
        assertEquals("Laptop",products.get(0).getName());
        assertEquals(1,products.get(0).getId());
        assertEquals("Iphone",products.get(1).getName());
        assertEquals(2,products.get(1).getId());
        assertEquals("Charger",products.get(2).getName());
        assertEquals(3,products.get(2).getId());

    }
    @Test
    public void testUpdateProduct(){
        ProductSystem test = new ProductSystem();
        test.updateProduct(1,"Laptop",100,499.99);
        Product p1 = test.getProduct("Laptop");
        assertEquals("Laptop",p1.getName());
        assertEquals(1,p1.getId());
        assertEquals(100,p1.getQuantity());
        test.updateProduct(1,"Laptop",200,499.99);
        Product p2 = test.getProduct("Laptop");
        assertEquals("Laptop",p1.getName());
        assertEquals(1,p2.getId());
        assertEquals(200,p2.getQuantity());

    }

}
