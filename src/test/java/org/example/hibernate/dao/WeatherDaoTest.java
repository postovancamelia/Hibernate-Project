package org.example.hibernate.dao;

import org.example.hibernate.model.WeatherRecord;
import org.example.hibernate.util.HibernateUtilTest;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeatherDaoTest {

    private WeatherDao dao;

    @BeforeAll
    void setup() {
        dao = new WeatherDao(HibernateUtilTest.getSessionFactory());
    }

    @AfterAll
    void shutdown() {
        HibernateUtilTest.shutdown();
    }

    @Test
    void save_and_find() {
        WeatherRecord rec = new WeatherRecord(
                LocalDateTime.now(),
                false,
                2,
                20,
                20,
                6000L,
                1015
        );


        dao.save(rec);
        assertNotNull(rec.getId());

        WeatherRecord fromDb = dao.findById(rec.getId()).orElseThrow();
        assertEquals(20, fromDb.getTemperature());
        assertFalse(fromDb.isRaining());
    }

    @Test
    void update_works() {
        WeatherRecord rec = new WeatherRecord(
                LocalDateTime.now(),
                false,
                2,
                20,
                20,
                6000L,
                1015
        );

        dao.save(rec);

        rec.setTemperature(15);
        dao.update(rec);

        WeatherRecord updated = dao.findById(rec.getId()).orElseThrow();
        assertEquals(15, updated.getTemperature());
    }

    @Test
    void delete_works() {
        WeatherRecord rec = new WeatherRecord(
                LocalDateTime.now(),
                false,
                2,
                20,
                20,
                6000L,
                1015
        );

        dao.save(rec);

        Long id = rec.getId();
        dao.deleteById(id);

        assertTrue(dao.findById(id).isEmpty());
    }
}
