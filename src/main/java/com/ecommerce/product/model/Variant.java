package com.ecommerce.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Variant extends BaseModel {

    private String description;
    private List<String> images;
    private Double price;

    @JsonProperty("is_primary")
    @Field("is_primary")
    private boolean isPrimary;

    private Map<String, Object> attributes;

}
