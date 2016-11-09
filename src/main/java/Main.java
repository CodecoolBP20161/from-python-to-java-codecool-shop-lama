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
        get("/cart", ProductController::renderCart, new ThymeleafTemplateEngine());
        get("/filter", ProductController::renderProducts, new ThymeleafTemplateEngine());
        post("/add", (request, response) -> {
            // available session check
            if (request.session().attribute("userOrder") == null) {
                request.session().attribute("userOrder", new Order());
            }
            Order userOrder = request.session().attribute("userOrder");
            userOrder.addLineItem(Integer.parseInt(request.queryParams("id")));
            return userOrder.sumProductsQuantity();
        });

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

    }

    public static void populateData() {

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
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", phone, amazon));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", phone, amazon));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));

    }


}
