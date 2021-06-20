package com.org.iotservice.service;

import com.org.iotservice.helper.CSVHelper;
import com.org.iotservice.repository.ProductRepo;
import com.org.iotservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CSVService {

    @Autowired
    ProductRepo repository;

    public void save(MultipartFile file) {
        try {
            List<Product> Product = CSVHelper.csvToProduct(file.getInputStream());
            repository.saveAll(Product);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<Product> getProductByProductIdAndTimeStamp(String productId, String dateTime) {
        return repository.findAll().stream()
                .filter(s->s.getId().contentEquals(productId) && s.getDateTime().contentEquals(dateTime))
                .collect(Collectors.toList());
    }

    public List<Product> getProductByProductIdAndTimeStamp(String productId) {
        return repository.findAll().stream()
                .filter(s->s.getId().contentEquals(productId))
                .collect(Collectors.toList());
    }
    public List<Product> getProduct() {
        return repository.findAll().stream()
                .collect(Collectors.toList());
    }
}