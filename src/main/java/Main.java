import com.codecool.shop.controller.CustomerController;
import com.codecool.shop.controller.DbPopulator;
import com.codecool.shop.controller.OrderController;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.implementation.CustomerDaoJdbc;
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

        get("/registration", CustomerController::renderRegistration, new ThymeleafTemplateEngine());

        post("/add", (request, response) -> {
            OrderController.addProductToCart(request);
            return ((Order) request.session().attribute("userOrder")).sumProductsQuantity();
        });

        // modify quantities
        post("/remove_one_item", (req, res) -> {
            OrderController.changeQuantity(req, res, -1);
            res.redirect("/cart");
            return null;
        });

        post("/add_one_item", (req, res) -> {
            OrderController.changeQuantity(req, res, 1);
            res.redirect("/cart");
            return null;
        });

        post("/remove_all", (req, res) -> {
            OrderController.removeProductFromOrder(req, res);
            res.redirect("/cart");
            return null;
        });

        post("/register", (req, res) -> {
            CustomerController.registration(req);
            res.redirect("/");
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

        get("/payment", OrderController::renderPayment, new ThymeleafTemplateEngine());

        get("/api/checkemail", (request, response) -> {
            CustomerDaoJdbc customerDao = CustomerDaoJdbc.getInstance();
            return customerDao.checkEmail(request.queryParams("email"));
        });

        //TODO: place these into a service!
        get("/api/checkuser", (request, response) -> {
            CustomerDaoJdbc customerDao = CustomerDaoJdbc.getInstance();
            return customerDao.checkUserName(request.queryParams("user_name"));
        });
        get("/api/checkuser", (request, response) -> {
            CustomerDaoJdbc customerDao = CustomerDaoJdbc.getInstance();
            return customerDao.checkUserName(request.queryParams("user_name"));
        });
        get("/validate-user", CustomerController::loginValidation);
        get("/logout-user", CustomerController::logout);
        get("/admin", OrderController::renderAdminPage, new ThymeleafTemplateEngine());

    }

}
