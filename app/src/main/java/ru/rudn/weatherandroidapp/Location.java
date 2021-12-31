package ru.rudn.weatherandroidapp;

public class Location {
    private String localtime;
    private String country;
    private int localtimeEpoch;
    private String name;
    private double lon;
    private String region;
    private double lat;
    private String tzId;

    public String getLocaltime(){
        return localtime;
    }

    public String getCountry(){
        return country;
    }

    public int getLocaltimeEpoch(){
        return localtimeEpoch;
    }

    public String getName(){
        return name;
    }

    public double getLon(){
        return lon;
    }

    public String getRegion(){
        return region;
    }

    public double getLat(){
        return lat;
    }

    public String getTzId(){
        return tzId;
    }
}
