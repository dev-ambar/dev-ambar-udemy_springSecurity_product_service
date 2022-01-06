package com.learningpath.productservice.service;

import com.learningpath.productservice.dto.Coupon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CouponService {

    @Value("${coupons-service.url}")
    private String couponServiceURL;

    public Coupon getCoupon(String couponCode) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("doug@bailey.com", "doug");
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<Coupon> response = new RestTemplate().exchange(couponServiceURL + couponCode,
                HttpMethod.GET, request, Coupon.class);

        return response.getBody();
    }
}
