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
        String q = request.getParameter("q");
        String brand = request.getParameter("brand");
        String status = request.getParameter("status");
        String price = request.getParameter("price");

        List<Product> list;

        // If filter params exist, use filterAdmin
        if ((q != null && !q.isEmpty()) ||
                (brand != null && !brand.isEmpty()) ||
                (status != null && !status.isEmpty()) ||
                (price != null && !price.isEmpty())) {
            list = ProductService.getInstance().filterAdmin(q, brand, status, price);
        } else {
            list = ProductService.getInstance().getAllProducts();
        }

        request.setAttribute("listP", list);
        request.setAttribute("msgName", q);
        request.setAttribute("msgBrand", brand);
        request.setAttribute("msgStatus", status);
        request.setAttribute("msgPrice", price);

        request.getRequestDispatcher("/Admin/products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
