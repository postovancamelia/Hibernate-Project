package org.example.hibernate.dao;


import org.example.hibernate.model.Weather;
import org.example.hibernate.model.WeatherRecord;
import org.example.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class WeatherDao {

    public Long save(Weather weather) {
        Transaction tx = null;
        Long id;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            WeatherRecord rec = WeatherRecord.from(weather);
            session.persist(rec);
            tx.commit();
            id = rec.getId();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
        return id;
    }

    public Weather find(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            WeatherRecord rec = session.byId(WeatherRecord.class).load(id);
            return rec != null ? rec.toDomain() : null;
        }
    }

    public List<Weather> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from WeatherRecord", WeatherRecord.class)
                    .list()
                    .stream()
                    .map(WeatherRecord::toDomain)
                    .toList();
        }
    }

    public void update(Long id, Weather newWeather) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            WeatherRecord rec = session.get(WeatherRecord.class, id);
            if (rec == null) throw new IllegalArgumentException("WeatherRecord not found: id=" + id);

            // copy new values
            rec.setDate(newWeather.getDate());
            rec.setRaining(newWeather.isRaining());
            rec.setWindSpeed(newWeather.getWindSpeed());
            rec.setTemperature(newWeather.getTemperature());
            rec.setCloudsPercentage(newWeather.getCloudsPercentage());
            rec.setDistanceVisible(newWeather.getDistanceVisible());
            rec.setPressure(newWeather.getPressure());

            session.merge(rec);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            WeatherRecord rec = session.get(WeatherRecord.class, id);
            if (rec != null) session.remove(rec);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

}