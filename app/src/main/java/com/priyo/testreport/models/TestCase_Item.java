package com.priyo.testreport.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONArray;

public class TestCase_Item extends BaseObservable {

    String tcid;

    String tcname;

    String tcstatus;

    String date;

    JSONArray steps;

    @Bindable
    public JSONArray getSteps() {
        return steps;
    }

    public void setSteps(JSONArray steps) {
        this.steps = steps;
        notifyPropertyChanged(BR.steps);
    }

    @Bindable
    public String getTcid() {
        return tcid;
    }

    public void setTcid(String tcid) {
        this.tcid = tcid;
        notifyPropertyChanged(BR.tcid);
    }
    @Bindable
    public String getTcname() {
        return tcname;
    }

    public void setTcname(String tcname) {
        this.tcname = tcname;
        notifyPropertyChanged(BR.tcname);
    }
    @Bindable
    public String getTcstatus() {
        return tcstatus;

    }

    public void setTcstatus(String tcstatus) {
        this.tcstatus = tcstatus;
        notifyPropertyChanged(BR.tcstatus);
    }
    @Bindable
    public String getDate() {
        return date;

    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }
}
