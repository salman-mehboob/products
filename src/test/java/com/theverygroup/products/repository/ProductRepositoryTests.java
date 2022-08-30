package com.theverygroup.products.repository;

import com.theverygroup.products.dto.Price;
import com.theverygroup.products.dto.Product;
import com.theverygroup.products.dto.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductRepositoryTests {

    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    public void testFindAll_containsElement() {
        // Given

        // When
        List<Product> products = productRepository.findAll();

        // Then
        Price price = Price.builder()
                .value(new BigDecimal("18.99"))
                .currency("GBP")
                .build();
        Product expected = Product.builder()
                .id("CLN-CDE-BOOK")
                .name("Clean Code")
                .description("Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)")
                .price(price)
                .type(Type.BOOKS)
                .department("Books and Stationery")
                .weight("220g")
                .build();
        assertThat(products).contains(expected);
    }

    @Test
    public void findByProductId() {
        List<Product> expected = (List<Product>) productRepository.findByProductId("Flashgun");
        assertNotNull(expected);
    }

    @Test
    public void findByProductIdOrType() {
        List<Product> expected = (List<Product>) productRepository.findByProductIdOrType("t","Electrical");
        assertNotNull(expected);
    }

    @Test
    public void deleteByProductId() {
        productRepository.deleteByProductId("Flashgun");
        List<Product> expected = (List<Product>) productRepository.findByProductId("Flashgun");
        assertFalse(expected.size()>0);
    }

}