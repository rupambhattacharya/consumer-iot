package com.org.iotservice.controller;

import com.org.iotservice.helper.CSVHelper;
import com.org.iotservice.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("iot/event")
public class CSVController {

        @Autowired
        CSVService fileService;

        @PostMapping("/v1")
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
}
