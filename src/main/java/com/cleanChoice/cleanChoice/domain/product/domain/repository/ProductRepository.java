package com.cleanChoice.cleanChoice.domain.product.domain.repository;

import com.cleanChoice.cleanChoice.domain.product.domain.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    List<Product> findByDsldId(Long dsldId);

    @NotNull Page<Product> findAll(@NotNull Pageable pageable);

    List<Product> findAllByBrandName(String brandName);
}
