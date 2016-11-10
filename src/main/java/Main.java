import com.codecool.shop.controller.OrderController;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.order.implementation.Order;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        populateData();

        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());
        get("/cart", (request, response) -> {
            return new ThymeleafTemplateEngine().render(OrderController.renderCart(request, response));
        });
        get("/filter", ProductController::renderProducts, new ThymeleafTemplateEngine());
        post("/add", (request, response) -> {
            OrderController.addProductToCart(request);
            return ((Order) request.session().attribute("userOrder")).sumProductsQuantity();
        });

        // modify quantities
        post("/remove", (req, res) -> {
            ((Order) req.session().attribute("userOrder")).changeQuantity(req.queryParams("id"), -1);
            res.redirect("/cart");
            return null;
        });

        post("/add_item", (req, res) -> {
            ((Order) req.session().attribute("userOrder")).changeQuantity(req.queryParams("id"), 1);
            res.redirect("/cart");
            return null;
        });

        post("/remove_all", (req, res) -> {
            ((Order) req.session().attribute("userOrder")).removeItem(req.queryParams("id"));
            res.redirect("/cart");
            return null;
        });

        // checkout methods
        get("/checkout", OrderController::renderCheckout, new ThymeleafTemplateEngine());

        post("/checkout/submit", (req, res) -> {
            //TODO: WRITE HERE
            if (OrderController.checkOut(req)) {
                res.redirect("/payment");
            }
            else res.redirect("/checkout");
            return null;
        });

        get("/payment", (req, res) -> "payment");

    }

    private static void populateData() {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "phone");
        productCategoryDataStore.add(phone);
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "laptop");
        productCategoryDataStore.add(phone);
        ProductCategory headset = new ProductCategory("Headset", "Hardware", "headset");
        productCategoryDataStore.add(phone);


        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Lenovo 5520", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", phone, amazon));
        productDataStore.add(new Product("Sony Headset Stuff", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", headset, amazon));
        productDataStore.add(new Product("Apple Ipad", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("HP ProBook", 89, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", laptop, lenovo));
        productDataStore.add(new Product("Nokia 2110", 89,  "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", phone, amazon));
        productDataStore.add(new Product("Asus ZenBook", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptop, lenovo));
    }


}
