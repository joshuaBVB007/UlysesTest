package com.septeo.ulyses.technical.test.config;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.septeo.ulyses.technical.test.controller.BrandController;

import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;

@Configuration
@EnableCaching
@Service
public class CacheCustomConfig {

	private static final Logger logger = LoggerFactory.getLogger(CacheCustomConfig.class);

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager manager = new CaffeineCacheManager("CachingApp");
		// los valores en cache expiran luego de 10 segundos
		manager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS));
		logger.info("CacheManager configurado correctamente");
		return manager;
	}
}