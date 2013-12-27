package com.u2ware.springfield.support.i18n;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

public class LocaleChangeFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(LocaleChangeFilter.class);

	public static final String DEFAULT_PARAM_NAME = "locale";
	private String paramName = DEFAULT_PARAM_NAME;
	private LocaleResolver localeResolver;
	private Locale defaultLocale;

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamName() {
		return this.paramName;
	}
    public Locale getDefaultLocale() {
		return defaultLocale;
	}
	public void setDefaultLocale(Locale defaultLocale) {
		this.defaultLocale = defaultLocale;
	}
	protected void initFilterBean() throws ServletException {
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        Map<String,LocaleResolver> resolvers = wac.getBeansOfType(LocaleResolver.class);
        if (resolvers.size()==1) {
            localeResolver = resolvers.values().iterator().next();
        }
    }

    
    
    
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

    	//logger.debug(localeResolver);
    	
		String newLocale = request.getParameter(LocaleChangeInterceptor.DEFAULT_PARAM_NAME);
		if(localeResolver != null && newLocale != null){
			Locale locale = StringUtils.parseLocaleString(newLocale);
	    	logger.debug(locale.toString());
			localeResolver.setLocale(request, response, locale);
            LocaleContextHolder.setLocale(locale);
		}
		
        chain.doFilter(request, response);
        
        if (localeResolver != null) {
        	LocaleContextHolder.resetLocaleContext();
        }
    }
}