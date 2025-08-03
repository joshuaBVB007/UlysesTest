package com.septeo.ulyses.technical.test.interceptor;

import java.time.LocalDate;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyCustomInterceptor implements HandlerInterceptor {

	// REQUERIMIENTO 12: CREAR UN INTERCEPTOR
	private static final Logger logger = LoggerFactory.getLogger(MyCustomInterceptor.class);
	long startTime = 0;
	long endTime = 0;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// REQUERIMIENTO 14
		startTime = System.nanoTime();
		logger.info("----------- Request -----------");
		logger.info("Request Date: " + LocalDate.now() + " Request time: " + LocalTime.now());
		logger.info("URI: " + request.getRequestURI());
		logger.info("Método HTTP: {}", request.getMethod());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// REQUERIMIENTO 14
		endTime = System.nanoTime();
		long durationNano = endTime - startTime;
		double durationMilli = (double) durationNano / 1_000_000.0;
		logger.info("----------- Response -----------");
		logger.info("Status Response: " + response.getStatus());
		logger.info("Processing time: " + durationMilli + " Milisegundos");
	}

}
