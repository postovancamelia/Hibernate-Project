package org.example.hibernate.dao;

import org.example.hibernate.model.WeatherRecord;
import org.hibernate.SessionFactory;

public class WeatherDao extends AbstractHibernateDao<WeatherRecord, Long> {

    public WeatherDao(SessionFactory sessionFactory) {
        super(sessionFactory, WeatherRecord.class);
    }
}
