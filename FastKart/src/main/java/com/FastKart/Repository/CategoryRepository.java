package com.FastKart.Repository;

import org.springframework.data.repository.CrudRepository;

import com.FastKart.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
