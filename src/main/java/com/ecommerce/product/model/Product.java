package com.ecommerce.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Product extends BaseModel {

    private String name;
    private List<String> images;
    private UUID ratingId;

    @Field("category_id")
    @JsonProperty("category_id")
    private UUID categoryId;

    @Field("subcategory_id")
    @JsonProperty("subcategory_id")
    private UUID subcategoryId;

    private Map<String, String> attributes;

    private List<Variant> variants;

}