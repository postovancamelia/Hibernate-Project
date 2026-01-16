package org.example;

import org.example.hibernate.dao.WeatherDao;
import org.example.hibernate.model.Weather;
import org.example.hibernate.model.WeatherRecord;
import org.example.hibernate.util.HibernateUtil;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        WeatherDao dao = new WeatherDao(HibernateUtil.getSessionFactory());

        // CREATE (domain -> entity)
        Weather today = new Weather(
                LocalDateTime.now(),
                false,       // raining
                2,           // wind m/s
                21,          // temperature °C
                20,          // clouds %
                6000L,       // visibility m
                1015         // pressure hPa
        );

        WeatherRecord rec = WeatherRecord.from(today);
        dao.save(rec);
        Long id = rec.getId();
        System.out.println("Inserted Weather ID = " + id);

        // READ (entity -> domain)
        WeatherRecord loadedRec = dao.findById(id).orElseThrow();
        Weather loaded = loadedRec.toDomain();
        System.out.println("Loaded: " + loaded);
        System.out.println("Good for walk? " + loaded.isDayGoodForWalk());

        // UPDATE (update entity fields, then dao.update)
        loadedRec.setRaining(true);
        loadedRec.setWindSpeed(5);
        loadedRec.setTemperature(13);
        loadedRec.setCloudsPercentage(80);
        loadedRec.setDistanceVisible(3000L);
        loadedRec.setPressure(1008);

        dao.update(loadedRec);

        Weather afterUpdate = dao.findById(id).orElseThrow().toDomain();
        System.out.println("Updated record -> good for walk? " + afterUpdate.isDayGoodForWalk());

        // LIST
        System.out.println("All weather rows: " + dao.findAll().size());

        // DELETE
        dao.deleteById(id);
        System.out.println("Deleted record. Remaining: " + dao.findAll().size());
    }
}
