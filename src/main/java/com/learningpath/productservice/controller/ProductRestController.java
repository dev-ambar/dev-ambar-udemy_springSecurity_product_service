package com.learningpath.productservice.controller;

import com.learningpath.productservice.CouponService;
import com.learningpath.productservice.dto.Coupon;
import com.learningpath.productservice.model.Product;
import com.learningpath.productservice.repos.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productapi")
public class ProductRestController {

    private Logger logger = LoggerFactory.getLogger(Product.class);

    @Autowired
    private ProductRepository productRepository;
    @ Autowired
    private CouponService couponService;

    @PostMapping("/products")
    public ResponseEntity createProduct(@RequestBody Product product)
    {
        if(product.getDiscountCode()!=null) {
            Coupon coupon = couponService.getCoupon(product.getDiscountCode());
            if(coupon!=null && coupon.getDiscount()!=null)
             product.setPrice(product.getPrice().subtract(BigDecimal.valueOf(coupon.getDiscount())));
            else
                logger.info("discount {} on product is not applied",product.getDiscountCode());
        }
        Product p = productRepository.save(product);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity getProduct(@PathVariable Long id)
    {
        Optional<Product> p = productRepository.findById(id);
        ResponseEntity<Product> responseEntity ;
         if(p.isPresent())
            responseEntity = new ResponseEntity(p,HttpStatus.FOUND);
         else
             responseEntity = new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @GetMapping("/products")
    public ResponseEntity getProducts()
    {
        List<Product> p = productRepository.findAll();
        ResponseEntity<Product> responseEntity ;
        if(!p.isEmpty())
            responseEntity = new ResponseEntity(p,HttpStatus.FOUND);
        else
            responseEntity = new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        return responseEntity;
    }
}
