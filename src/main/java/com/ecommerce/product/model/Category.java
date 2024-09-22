package com.ecommerce.product.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Category {

    private String name;
    private String image;
    private Map<String, Object> attributes;

}
