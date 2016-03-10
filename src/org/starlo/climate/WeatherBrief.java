package org.starlo.climate;

public class WeatherBrief
{
    public ConditionReport[] weather;
    public MainReport main;
    public String name;

    //Set after JSON parsing, works as reverse pointer
    public String zipcode;
    public boolean placeholder = false;
}
