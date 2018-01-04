package com.example.demo.dao.mapper;

import com.example.demo.dao.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by lumen on 18-1-4.
 */
@Mapper
public interface ProductMapper {
    Product select(@Param("id") long id);
    void update(Product product);
}
