package com.lgutierrez.blogrestapi.repository;

import com.lgutierrez.blogrestapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
