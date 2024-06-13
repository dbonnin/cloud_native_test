package com.diegobonnin.cloudnative.products.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.diegobonnin.cloudnative.products.domain.Product;


@Repository 
public interface ProductsRepository extends  ReactiveCrudRepository<Product, Long> {

}