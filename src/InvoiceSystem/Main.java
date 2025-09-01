package InvoiceSystem;

import java.util.*;


class Tax {
    private String name;
    private double percentage;
    
    public Tax(String name, double percentage) {
        this.name = name;
        this.percentage = percentage;
    }
    
    public String getName() { return name; }
    public double getPercentage() { return percentage; }
}


class TaxGroup {
    private String name;
    private List<Tax> taxes;
    
    public TaxGroup(String name) {
        this.name = name;
        this.taxes = new ArrayList<>();
    }
    
    public void addTax(Tax tax) {
        taxes.add(tax);
    }
    
    public double calculateTotalTax(double price) {
        double totalTax = 0;
        for (Tax tax : taxes) {
            totalTax += (price * tax.getPercentage()) / 100;
        }
        return totalTax;
    }
    
    public String getName() { return name; }
    public List<Tax> getTaxes() { return taxes; }
}

// Product class with price and tax group
class Product {
    private String name;
    private double price;
    private TaxGroup taxGroup;
    
    public Product(String name, double price, TaxGroup taxGroup) {
        this.name = name;
        this.price = price;
        this.taxGroup = taxGroup;
    }
    
    public String getName() { return name; }
    public double getPrice() { return price; }
    public TaxGroup getTaxGroup() { return taxGroup; }
    
    public double getTaxAmount() {
        return taxGroup.calculateTotalTax(price);
    }
    
    public double getTotalPrice() {
        return price + getTaxAmount();
    }
}

// Invoice class to store products and generate invoice
class Invoice {
    private List<Product> products;
    
    public Invoice() {
        this.products = new ArrayList<>();
    }
    
    public void addProduct(Product product) {
        products.add(product);
    }
    
    public void generateInvoice() {
        System.out.println("-----------------------------");
        System.out.println("Product\t\tPrice\tTax");
        System.out.println("-----------------------------");
        
        double totalPrice = 0;
        double totalTax = 0;
        
        for (Product product : products) {
            double price = product.getPrice();
            double tax = product.getTaxAmount();
            
            System.out.printf("%-12s\t%.2f\t%.2f%n", 
                product.getName(), price, tax);
            
            totalPrice += price;
            totalTax += tax;
        }
        
        System.out.println("-----------------------------");
        System.out.printf("Total\t\t%.2f\t%.2f%n", totalPrice, totalTax);
        System.out.printf("Grand Total\t: %.2f%n", totalPrice + totalTax);
        System.out.println("-----------------------------");
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        // Task 1: Create individual taxes
        Tax noTax = new Tax("No Tax", 0);
        Tax vat = new Tax("VAT", 20);
        Tax sgst = new Tax("SGST", 9);
        Tax cgst = new Tax("CGST", 9);
        
        // Task 2: Create tax groups
        TaxGroup noTaxGroup = new TaxGroup("No Tax");
        noTaxGroup.addTax(noTax);
        
        TaxGroup vatOnlyGroup = new TaxGroup("VAT Only");
        vatOnlyGroup.addTax(vat);
        
        TaxGroup gstGroup = new TaxGroup("GST");
        gstGroup.addTax(sgst);
        gstGroup.addTax(cgst);
        
        // Task 3: Create products with tax groups
        Product rice = new Product("Rice", 50, noTaxGroup);
        Product petrol = new Product("Petrol", 65, vatOnlyGroup);
        Product soap = new Product("Soap", 10, gstGroup);
        
        // Task 4: Create invoice and add products
        Invoice invoice = new Invoice();
        invoice.addProduct(soap);
        invoice.addProduct(petrol);
        
        // Generate the invoice
        invoice.generateInvoice();
    }
} 