package com.ecommerce.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rating {

    @JsonProperty("avg_ratings")
    private Double avgRatings;

    @JsonProperty("rating_count")
    private Integer ratingCount;

}
