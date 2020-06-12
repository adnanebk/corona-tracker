package com.adnanbk.coronatracker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Statistic {

    private String country;
    private String province;
    private int confirmed;
    private int recovered;
    private int critical;
    private int deaths;
    private int increased;
    private int increasedDeaths;
    private int increasedRecovered;

    public String getProvince() {
        if(province==null)
            return country;
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Statistic(Statistic moroccoStatBefore, Statistic moroccoStatLatest) {
        country = moroccoStatLatest.country;
        confirmed = moroccoStatLatest.confirmed;
        recovered = moroccoStatLatest.recovered;
        critical = moroccoStatLatest.critical;
        deaths = moroccoStatLatest.deaths;
        increased = moroccoStatLatest.confirmed-moroccoStatBefore.confirmed;
        increasedDeaths = moroccoStatLatest.deaths-moroccoStatBefore.deaths;
        increasedRecovered = moroccoStatLatest.recovered-moroccoStatBefore.recovered;
    }



    public Statistic() {
    }


    public Statistic(Statistic moroccoStatLatest) {
       country = moroccoStatLatest.country;
       confirmed = moroccoStatLatest.confirmed;
       recovered = moroccoStatLatest.recovered;
       critical = moroccoStatLatest.critical;
       deaths = moroccoStatLatest.deaths;
    }

    public int getIncreasedDeaths() {
        return increasedDeaths;
    }

    public void setIncreasedDeaths(int increasedDeaths) {
        this.increasedDeaths = increasedDeaths;
    }

    public int getIncreasedRecovered() {
        return increasedRecovered;
    }

    public void setIncreasedRecovered(int increasedRecovered) {
        this.increasedRecovered = increasedRecovered;
    }

    public int getIncreased() {
        return increased;
    }

    public void setIncreased(int increased) {
        this.increased = increased;
    }



    public void setCountry(String country) {
        this.country = country;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getCountry() {
        if(country==null)
            return province;
        return country;
    }
 public Statistic setIncreasedAndReturn(int increased){
        setIncreased(increased);
        return this;
 }
    public int getConfirmed() {
        return confirmed;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getCritical() {
        return critical;
    }

    public int getDeaths() {
        return deaths;
    }
}
