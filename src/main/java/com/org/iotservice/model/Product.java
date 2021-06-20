package com.org.iotservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@Entity
public class
Product {

    @Id
    private String id;
    private String eventId;
    private String dateTime;
    private String latitude;
    private String longitude;
    private String battery;
    private String light;
    private String airplaneMode;

   public Product(String dateTime, String eventId, String productId, String latitude, String longitude, String battery, String light, String airplaneMode) {
        this.dateTime = dateTime;
        this.eventId = eventId;
        this.id = productId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.battery = battery;
        this.light = light;
        this.airplaneMode = airplaneMode;
    }
}
