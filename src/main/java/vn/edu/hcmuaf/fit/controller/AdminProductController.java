package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminProductController", value = "/admin/products")
public class AdminProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Product> list = ProductService.getInstance().getAllProducts();

        request.setAttribute("listP", list);
        request.getRequestDispatcher("/Admin/products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
