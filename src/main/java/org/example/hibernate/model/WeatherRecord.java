package org.example.hibernate.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "WEATHER")
public class WeatherRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Surrogate key for persistence

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private boolean raining;

    @Column(nullable = false)
    private int windSpeed;

    @Column(nullable = false)
    private int temperature;

    @Column(nullable = false)
    private int cloudsPercentage;

    @Column(nullable = false)
    private long distanceVisible;

    @Column(nullable = false)
    private int pressure;

    // JPA requires a no-arg constructor
    protected WeatherRecord() {}

    // Convenience constructor for manual creation if needed
    public WeatherRecord(LocalDateTime date, boolean raining, int windSpeed, int temperature,
                         int cloudsPercentage, long distanceVisible, int pressure) {
        this.date = date;
        this.raining = raining;
        this.windSpeed = windSpeed;
        this.temperature = temperature;
        this.cloudsPercentage = cloudsPercentage;
        this.distanceVisible = distanceVisible;
        this.pressure = pressure;
    }

    // Mapping helpers between domain and entity
    public static WeatherRecord from(Weather w) {
        return new WeatherRecord(
                w.getDate(),
                w.isRaining(),
                w.getWindSpeed(),
                w.getTemperature(),
                w.getCloudsPercentage(),
                w.getDistanceVisible(),
                w.getPressure()
        );
    }

    public Weather toDomain() {
        return new Weather(date, raining, windSpeed, temperature, cloudsPercentage, distanceVisible, pressure);
    }

    // Getters & setters for JPA (only where needed)
    public Long getId() { return id; }
    public LocalDateTime getDate() { return date; }
    public boolean isRaining() { return raining; }
    public int getWindSpeed() { return windSpeed; }
    public int getTemperature() { return temperature; }
    public int getCloudsPercentage() { return cloudsPercentage; }
    public long getDistanceVisible() { return distanceVisible; }
    public int getPressure() { return pressure; }

    public void setDate(LocalDateTime date) { this.date = date; }
    public void setRaining(boolean raining) { this.raining = raining; }
    public void setWindSpeed(int windSpeed) { this.windSpeed = windSpeed; }
    public void setTemperature(int temperature) { this.temperature = temperature; }
    public void setCloudsPercentage(int cloudsPercentage) { this.cloudsPercentage = cloudsPercentage; }
    public void setDistanceVisible(long distanceVisible) { this.distanceVisible = distanceVisible; }
    public void setPressure(int pressure) { this.pressure = pressure; }
}
