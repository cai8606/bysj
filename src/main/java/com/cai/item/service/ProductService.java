package com.cai.item.service;

import com.cai.item.mapper.ProductMapper;
import com.cai.item.pojo.Product;
import com.cai.item.vo.Enums;
import com.cai.item.vo.PageResult;
import com.cai.item.vo.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    public List<Product> findAll(){
        List<Product> products = productMapper.selectAll();
        return products;
    }

    public void save(Product product){
        product.setId(null);
        int i = productMapper.insert(product);
        System.out.println(i);
    }

    public PageResult<Product> findByPage(String query, Integer page, Integer rows){
        PageHelper.startPage(page,rows);
        Example example=new Example(Product.class);
        if (StringUtils.isNotBlank(query)){
            example.createCriteria().orLike("productName","%"+query+"%");
        }
        List<Product> products = productMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(products)){
            return new PageResult<>(404);
        }
        PageInfo<Product> pageInfo=new PageInfo(products);
        return new PageResult<>(pageInfo.getTotal(),products,200);
    }

    public Result updateProductStatus(Integer id, boolean status){
        Product product = new Product();
        product.setId(id);
        product.setProductStatus(status);
        int i = productMapper.updateByPrimaryKeySelective(product);
        if (i<1){
            return new Result(Enums.UPDATE_FAIL);
        }
        return new Result(Enums.UPDATE_SUCCESS);
    }
}
