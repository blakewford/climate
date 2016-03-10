package org.starlo.climate;

public class Weather extends WeatherBrief
{
    public Long dt;
    public Long id;

    public Weather(WeatherBrief brief)
    {
        weather = brief.weather;
        main = brief.main;
        name = brief.name;

        dt = 0L;
        id = 0L;
    }
}
