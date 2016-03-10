package org.starlo.climate;

public class WeatherPlaceholder extends Weather
{
    private static final String FILLER = "NOT AVAILABLE";

    public WeatherPlaceholder(String defaultName)
    {
        super(new WeatherBrief());
        coord = new Coordinates();
        wind = new WindReport();
        clouds = new CloudReport();
        weather = new ConditionReport[1];
        main = new MainReport();
        weather[0] = new ConditionReport();

        weather[0].main = FILLER;
        weather[0].description = FILLER;
        main.temp = Float.valueOf(0.0f);
        main.temp_min = Float.valueOf(0.0f);
        main.temp_max = Float.valueOf(0.0f);
        main.pressure = Float.valueOf(0.0f);
        main.humidity = Integer.valueOf(0);
        name = defaultName;
        coord.lat = Float.valueOf(0.0f);
        coord.lon = Float.valueOf(0.0f);
        wind.speed = Float.valueOf(0.0f);
        wind.deg = Float.valueOf(0.0f);
        clouds.all = Integer.valueOf(0);
        placeholder = true;
    }
}
