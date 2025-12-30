package vn.edu.hcmuaf.fit.filter;

import vn.edu.hcmuaf.fit.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = { "/admin/*", "/Admin/*" })
public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        User user = (session != null) ? (User) session.getAttribute("auth") : null;

        if (user == null) {
            // Not logged in -> Redirect to Login
            // Save the requested URL to redirect back after login if needed (Optional for
            // now)
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            // Logged in, check Role
            // Assume Role is "Admin" (from UserDAO insertions: 'Customer')
            String role = user.getRole();
            if (role != null && (role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("Manager"))) {
                // Authorized
                chain.doFilter(request, response);
            } else {
                // Not Admin -> Access Denied -> Redirect to Home or specific error page
                res.sendRedirect(req.getContextPath() + "/home");
            }
        }
    }

    @Override
    public void destroy() {
    }
}
