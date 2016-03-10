package org.starlo.climate;

public class Weather extends WeatherBrief
{
    public Coordinates coord;
    public WindReport wind;
    public CloudReport clouds;
    public RainReport rain;
    public SnowReport snow;
    public Long dt;
    public Long id;

    public Weather(WeatherBrief brief)
    {
        weather = brief.weather;
        main = brief.main;
        name = brief.name;
        zipcode = brief.zipcode;

        dt = 0L;
        id = 0L;
    }
}
