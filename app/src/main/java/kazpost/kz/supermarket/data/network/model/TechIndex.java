package kazpost.kz.supermarket.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 5/11/17.
 */

public class TechIndex {

    @SerializedName("schedule")
    @Expose
    private String schedule;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("phone")
    @Expose
    private String phone;


    @SerializedName("street")
    @Expose
    private String street;

    @SerializedName("postcode")
    @Expose
    private String postcode;

    @SerializedName("title")
    @Expose
    private String title;


    @SerializedName("house")
    @Expose
    private String house;

    public String getSchedule() {
        return schedule;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreet() {
        return street;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getTitle() {
        return title;
    }

    public String getHouse() {
        return house;
    }
}
