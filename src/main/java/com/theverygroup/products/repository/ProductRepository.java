package com.theverygroup.products.repository;

import com.theverygroup.products.dto.Price;
import com.theverygroup.products.dto.Product;
import com.theverygroup.products.dto.Type;
import com.theverygroup.products.util.ProductDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class ProductRepository {
     private List<Product> products;
    @Autowired
    public ProductRepository() {
        products = ProductDataUtils.loadAll();
    }

    public List<Product> findAll() {
        return products;
    }
    public List<Product> findByProductId(String id){return products.stream().filter(p ->p.getId().equals(id)).collect(Collectors.toList());}
    public List<Product> findByProductIdOrType(String kind , String value) {
        if(kind.toLowerCase().equals("i")){
             return products.stream().filter(p ->p.getId().equals(value)).collect(Collectors.toList());
        } else if(kind.toLowerCase().equals("t")){
            return products.stream().filter(p ->p.getType().name().toLowerCase().equals(value.toLowerCase())).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public String deleteByProductId(String id){
        List<Product> p = (List<Product>) products.stream().filter(t -> t.getId().equals(id)).collect(Collectors.toList());
        if(p.isEmpty()){
            return "No Product with this ID.";
        }
        else{
            products.removeAll(p);
            return "Deleted Successfully.";
        }
    }
    public void save(Product product) throws IOException {
        Price price = Price.builder()
                .value(product.getPrice().getValue())
                .currency(product.getPrice().getCurrency())
                .build();
        Product product1 = Product.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(price)
                .type(product.getType())
                .department(product.getDepartment())
                .weight(product.getWeight())
                .build();
    }
}