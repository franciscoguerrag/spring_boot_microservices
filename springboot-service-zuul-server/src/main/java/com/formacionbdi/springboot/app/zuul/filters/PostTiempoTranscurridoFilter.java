package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter {
	
	private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;  //que ejecute siempre no se va a validar en este caso
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info("Entrando a post filter");

		Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		Long tiempoFinal = System.currentTimeMillis();
		Long tiempoTrancurrido = tiempoFinal-tiempoInicio;
		log.info(String.format("Tiempo transcurrido en miliseg %s seg.", tiempoTrancurrido.doubleValue()/1000.00));
		log.info(String.format("Tiempo transcurrido en miliseg %s ms.", tiempoTrancurrido));
		return null;
	}

	@Override
	public String filterType() {
		return "post";  //antes de 
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
