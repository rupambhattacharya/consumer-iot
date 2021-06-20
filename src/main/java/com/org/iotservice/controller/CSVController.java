package com.org.iotservice.controller;

import com.org.iotservice.helper.CSVHelper;
import com.org.iotservice.model.Product;
import com.org.iotservice.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@CrossOrigin("http://localhost:8080")
@Controller

public class CSVController {

        @Autowired
        CSVService fileService;

    @RequestMapping(value = "iot/event/v1/", method = RequestMethod.POST)
        public ResponseEntity<String> uploadFile(@RequestParam("filepath") MultipartFile file) {
            String message = "";

            if (CSVHelper.hasCSVFormat(file)) {
                try {
                    fileService.save(file);
                    message = "data refreshed";
                    return ResponseEntity.status(HttpStatus.OK).body( "\" message \": \" "+ message +" \"");
                } catch (Exception e) {
                    message = "ERROR: A technical exception occurred & could not upload the file: " + file.getOriginalFilename() + "!";
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"description\": \""+ message +"\"");
                }
            }
            message = "ERROR: no data file found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("\"description\": \""+message+"\"");
        }

    @RequestMapping(value = "iot/event/v1", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getProduct () {
        List<Product> product;
        System.out.println("hey");
        String message = "";
        try {
            product = fileService.getProduct();
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "iot/event/v1/product", method = RequestMethod.GET)
        public ResponseEntity<List<Product>> getProductByProductIdAndTimeStamp (@RequestParam String ProductId, @RequestParam String DateTime) {
            List<Product> product;
            System.out.println("hay");
            String message = "";
            try {
            if(DateTime != null && !DateTime.isEmpty()){
                product = fileService.getProductByProductIdAndTimeStamp(ProductId, DateTime);
            }
            else{
                product = fileService.getProductByProductIdAndTimeStamp(ProductId);
            }
            if (product.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
