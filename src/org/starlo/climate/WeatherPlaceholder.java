package org.starlo.climate;

public class WeatherPlaceholder extends Weather
{
    private static final String FILLER = "NOT AVAILABLE";

    public WeatherPlaceholder(String defaultName)
    {
        super(new WeatherBrief());
        weather = new ConditionReport[1];
        main = new MainReport();
        weather[0] = new ConditionReport();

        weather[0].main = FILLER;
        main.temp = Float.valueOf(0.0f);
        name = defaultName;
        placeholder = true;
    }
}
