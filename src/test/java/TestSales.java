import org.example.Product;
import org.example.ProductSystem;
import org.example.Sales;
import org.example.SalesSystem;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class TestSales {

    @Test
    public void testRecordSale(){
        ProductSystem ps = new ProductSystem();
        SalesSystem s = new SalesSystem();
        Product p1 = ps.getProduct("Laptop");
        assertEquals(200,p1.getQuantity());
        s.RecordSale(p1.getId(),20);
        p1 = ps.getProduct("Laptop");
        assertEquals(180,p1.getQuantity());
        ps.updateProduct(1,"Laptop",200,499.99);
    }
    @Test
    public void testSalesReport(){
        SalesSystem sale = new SalesSystem();
        ArrayList<Sales> products = sale.getSalesReport();
        assertEquals(1,products.get(0).getProductId());
        assertEquals(5,products.get(0).getQuantity());
        assertEquals(6,products.get(1).getProductId());
        assertEquals(20,products.get(1).getQuantity());
    }
}
