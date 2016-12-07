import com.codecool.shop.controller.OrderController;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.controller.DbPopulator;
import com.codecool.shop.order.implementation.Order;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        DbPopulator.populateData();

        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());

        get("/cart", ProductController::renderCart, new ThymeleafTemplateEngine());

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
            OrderController.removeProductFromOrder(req, res);
            res.redirect("/cart");
            return null;
        });

        // checkout methods
        get("/checkout", OrderController::renderCheckout, new ThymeleafTemplateEngine());

        post("/checkout/submit", (req, res) -> {
            if (OrderController.checkOut(req)) {
                res.redirect("/payment");
            }
            else res.redirect("/checkout");
            return null;
        });

        get("/payment", (req, res) -> "payment");

    }

}
