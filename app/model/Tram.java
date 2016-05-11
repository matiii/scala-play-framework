package model;


import play.data.format.Formats;

import java.util.Date;

/**
 * Created by Mateusz on 2015-06-22.
 */
public class Tram {
    private int _line;
    private double _latitude;
    private double _longitude;
    private Date _lastUpdate;

    public Tram(int line, double longitude, double latitude, Date lastUpdate)
    {
        _line = line;
        _latitude = latitude;
        _longitude= longitude;
        _lastUpdate = lastUpdate;
    }

    public int getLine(){
        return _line;
    }

    public double getLatitude(){
        return _latitude;
    }

    public double getLongitude(){
        return _longitude;
    }

    public Date getLastUpdate(){
        return _lastUpdate;
    }
}
