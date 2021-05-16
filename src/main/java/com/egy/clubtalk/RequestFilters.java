package com.egy.clubtalk;

import com.egy.clubtalk.filters.RoomsCreateFilter;
import es.moki.ratelimitj.redis.request.RedisRateLimiterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestFilters {

    @Autowired
    RedisRateLimiterFactory factory;

    @Bean
    public FilterRegistrationBean<RoomsCreateFilter> getRoomsCreateEndpointFilter(){
        FilterRegistrationBean<RoomsCreateFilter> registrationBean  = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RoomsCreateFilter(factory));
        registrationBean.addUrlPatterns("/rooms/");

        return registrationBean;
    }

}
