package cc.doublez.platform.filter;

import cc.doublez.platform.jms.Publisher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yz on 2016/7/25
 */
public class LoginFilter implements Filter{

    private String id;
    private Publisher publisher;

    public void setId(String id) {
        this.id = id;
    }
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        //System.out.println("filter id:"+id);
        //System.out.println("publisher:"+publisher);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //System.out.println(request.getRequestURI());
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
