package vn.edu.hcmuaf.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.service.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/catalog")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] brands = request.getParameterValues("brand");
        String priceRange = request.getParameter("price");
        String sort = request.getParameter("sort");

        List<Product> products = ProductService.getInstance().getProducts(brands, priceRange, sort);

        request.setAttribute("products", products);
        request.setAttribute("selectedBrands", brands != null ? List.of(brands) : List.of());
        request.setAttribute("selectedPrice", priceRange);
        request.setAttribute("selectedSort", sort);

        request.getRequestDispatcher("/Catalog/catalog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
