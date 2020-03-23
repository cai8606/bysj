package com.cai.item.controller;

import com.cai.item.pojo.Product;
import com.cai.item.service.ProductService;
import com.cai.item.vo.PageResult;
import com.cai.item.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public PageResult<Product> findByPage(String query,Integer page,Integer rows){
        PageResult<Product> result = productService.findByPage(query, page, rows);
        return result;
    }

    @RequestMapping("/product/save")
    public String save(Product product){
        productService.save(product);

        return "redirect:/product/findAll";
    }

    @GetMapping("/product/changeStatus")
    public Result updateProductStatus(Integer id,boolean status){
        Result result = productService.updateProductStatus(id, status);
        return result;
    }
}
