package com.github.bingoohuang.springrest.boot.filter;

import org.apache.commons.io.output.TeeOutputStream;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@Component
public class MultiReadFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // use wrapper to read multiple times the content
        MultiReadHttpServletRequest req = new MultiReadHttpServletRequest((HttpServletRequest) request);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final StringWriter sw = new StringWriter();
        req.setAttribute("_log_baos", baos);
        req.setAttribute("_log_sw", sw);

        chain.doFilter(req, new HttpServletResponseWrapper((HttpServletResponse) response) {
            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                ServletOutputStream outputStream = super.getOutputStream();
                TeeOutputStream targetStream = new TeeOutputStream(outputStream, baos);
                return new DelegatingServletOutputStream(targetStream);
            }

            @Override
            public PrintWriter getWriter() throws IOException {
                PrintWriter writer = super.getWriter();
                TeeWriter teeWriter = new TeeWriter(writer, sw);
                return new PrintWriter(teeWriter);
            }
        });
    }

    @Override
    public void destroy() {

    }
}
