package org.example;


import org.example.hibernate.dao.WeatherDao;
import org.example.hibernate.model.Weather;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        WeatherDao dao = new WeatherDao();

        // CREATE
        Weather today = new Weather(
                LocalDateTime.now(),
                false,       // raining
                2,           // wind m/s
                21,          // temperature °C
                20,          // clouds %
                6000L,       // visibility m
                1015         // pressure hPa
        );
        Long id = dao.create(today);
        System.out.println("Inserted Weather ID = " + id);

        // READ
        Weather loaded = dao.find(id);
        System.out.println("Loaded: " + loaded);
        System.out.println("Good for walk? " + loaded.isDayGoodForWalk());

        // UPDATE (make it rainy and colder)
        Weather updated = new Weather(
                loaded.getDate(),
                true,
                5,
                13,
                80,
                3000L,
                1008
        );
        dao.update(id, updated);
        System.out.println("Updated record -> good for walk? " + dao.find(id).isDayGoodForWalk());

        // LIST
        System.out.println("All weather rows: " + dao.findAll().size());

        // DELETE
        dao.delete(id);
        System.out.println("Deleted record. Remaining: " + dao.findAll().size());
    }
}









