package com.recreo.config.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.util.Locale;

@Component
public class LocaleFilter implements Filter {

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String language = httpRequest.getParameter("language");

        if (language != null && (language.equals("en") || language.equals("es"))) {
            localeResolver.setLocale(httpRequest, httpResponse, new Locale(language));
        } else {
            localeResolver.setLocale(httpRequest, httpResponse, Locale.getDefault());
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() { }
}