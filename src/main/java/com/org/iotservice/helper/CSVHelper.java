package com.org.iotservice.helper;

import com.org.iotservice.model.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "DateTime","EventId","ProductId","Latitude","Longitude","Battery","Light","AirplaneMode"};

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Product> csvToProduct(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){

            List<Product> Product = new ArrayList<Product>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                String csv = csvRecord.get(0);
                Product product = new Product(
                        csv.split(";")[0],
                        csv.split(";")[1],
                        csv.split(";")[2],
                        !csv.split(";")[3].isEmpty() ? csv.split(";")[3].replaceAll(",", "."): "",
                        !csv.split(";")[4].isEmpty() ? csv.split(";")[4].replaceAll(",", "."): "",
                        !csv.split(";")[5].isEmpty() ? csv.split(";")[5].replaceAll(",", "."): "",
                        csv.split(";")[6],
                        csv.split(";")[7]
                );
                Product.add(product);
            }
            return Product;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
