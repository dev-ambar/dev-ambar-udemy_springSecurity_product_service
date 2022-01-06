package com.learningpath.productservice.controller;

import com.learningpath.productservice.CouponService;
import com.learningpath.productservice.dto.Coupon;
import com.learningpath.productservice.model.Product;
import com.learningpath.productservice.repos.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CouponService couponService;


    @GetMapping("/index")
    public String index()
    {
        return "index";
    }

    @GetMapping("/showCreateProductPage")
    public String showCreateProductPage()
    {
        return "createProduct";
    }

    @GetMapping("/showProduct")
    public String showProduct()
    {
        return "showProduct";
    }


    @PostMapping("/saveProduct")
    public ModelAndView addProduct(Product product)
    {
        if(product.getDiscountCode()!=null && product.getDiscountCode().length()>0 ) {
             Coupon coupon = couponService.getCoupon(product.getDiscountCode());
            if(coupon!=null && coupon.getDiscount()!=null)
                product.setPrice(product.getPrice().subtract(BigDecimal.valueOf(coupon.getDiscount())));
            else
                logger.info("discount {} on product is not applied",product.getDiscountCode());
        }
        Product saveProduct = productRepository.save(product);

        if (saveProduct!=null)
        {
        return  new ModelAndView("createProductResponse").addObject(saveProduct);
        }
        else
            return new ModelAndView("createProductResponse").addObject("have some issue to create product");
    }

    @GetMapping("/productDetails")
    public ModelAndView productDetails(String name)
    {
        Product productDetail = productRepository.findByName(name);
        if (productDetail!=null)
        {
            return  new ModelAndView("productDetails").addObject(productDetail);
        }
        else
            return new ModelAndView("showProduct").addObject("have some issue to find product details");
    }
}
