package com.example.krishnapatil.driver;

public class DriverInformation {

    String driversname, vehicleno, travellingfrom, travellingto;
    Double longitude, latitude;
    boolean accident_flag;

    public DriverInformation(String driversname, String vehicleno, String travellingfrom, String travellingto, Double longitude, Double latitude, boolean accident_flag) {
        this.driversname = driversname;
        this.vehicleno = vehicleno;
        this.travellingfrom = travellingfrom;
        this.travellingto = travellingto;
        this.longitude = longitude;
        this.latitude = latitude;
        this.accident_flag = accident_flag;
    }

    public String getDriversname() {
        return driversname;
    }

    public void setDriversname(String driversname) {
        this.driversname = driversname;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getTravellingfrom() {
        return travellingfrom;
    }

    public void setTravellingfrom(String travellingfrom) {
        this.travellingfrom = travellingfrom;
    }

    public String getTravellingto() {
        return travellingto;
    }

    public void setTravellingto(String travellingto) {
        this.travellingto = travellingto;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public boolean isAccident_flag() {
        return accident_flag;
    }

    public void setAccident_flag(boolean accident_flag) {
        this.accident_flag = accident_flag;
    }
}
