package com.egy.clubtalk.filters;

import com.egy.clubtalk.exceptions.RateLimitExceededException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.moki.ratelimitj.core.limiter.request.RequestLimitRule;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import es.moki.ratelimitj.redis.request.RedisRateLimiterFactory;
import java.io.IOException;
import java.security.Principal;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RoomsCreateFilter implements Filter {

    RequestRateLimiter rateLimiter;

    public RoomsCreateFilter(RedisRateLimiterFactory factory) {
        Set<RequestLimitRule> rules = Collections.singleton(RequestLimitRule.of(Duration.ofMinutes(1), 3));
        this.rateLimiter = factory.getInstance(rules);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if(!req.getMethod().equals("POST")) {
            chain.doFilter(request, response);
            return;
        }

        Principal principal = req.getUserPrincipal();
        if(rateLimiter.overLimitWhenIncremented(String.format("user:%s", principal.getName()))) {
            ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
            ((HttpServletResponse) response).setStatus(429);
            response.getOutputStream().write(getErrorResponse());
            return;
        }

        chain.doFilter(request, response);
    }

    private byte[] getErrorResponse() throws IOException {
        HashMap<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("message", "You are sending too many requests!");
        String serialized = new ObjectMapper().writeValueAsString(body);
        return serialized.getBytes();
    }
}
