package vn.edu.hcmuaf.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

@WebServlet("/uploads/*")
public class ImageServlet extends HttpServlet {
    // Configure this path to match your local setup (Must match UploadController)
    private static final String UPLOAD_DIR = "C:/data/uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestedImage = request.getPathInfo();

        if (requestedImage == null || requestedImage.isEmpty() || requestedImage.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Remove leading slash
        if (requestedImage.startsWith("/")) {
            requestedImage = requestedImage.substring(1);
        }

        File file = new File(UPLOAD_DIR, URLDecoder.decode(requestedImage, "UTF-8"));

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Get content type by filename.
        String contentType = getServletContext().getMimeType(file.getName());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        response.reset();
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));

        // Stream file to response
        try (FileInputStream in = new FileInputStream(file);
                OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[10240]; // 10KB buffer
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
}
