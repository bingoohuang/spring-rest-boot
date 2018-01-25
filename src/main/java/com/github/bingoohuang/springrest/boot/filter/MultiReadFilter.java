package com.github.bingoohuang.springrest.boot.filter;

import lombok.val;
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
        val req = new BufferedRequestWrapper((HttpServletRequest) request);

        val baos = new ByteArrayOutputStream();
        val sw = new StringWriter();
        req.setAttribute("_log_baos", baos);
        req.setAttribute("_log_sw", sw);
        req.setAttribute("_log_req", req);

        chain.doFilter(req, new HttpServletResponseWrapper((HttpServletResponse) response) {
            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                val outputStream = super.getOutputStream();
                val targetStream = new TeeOutputStream(outputStream, baos);
                return new DelegatingServletOutputStream(targetStream);
            }

            @Override
            public PrintWriter getWriter() throws IOException {
                val writer = super.getWriter();
                val teeWriter = new TeeWriter(writer, sw);
                return new PrintWriter(teeWriter);
            }
        });
    }

    @Override
    public void destroy() {

    }
}
