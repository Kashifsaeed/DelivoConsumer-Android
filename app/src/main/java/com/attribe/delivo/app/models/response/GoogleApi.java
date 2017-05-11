package com.attribe.delivo.app.models.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaz on 8/22/2016.
 */
public class GoogleApi {

    private List<Object> htmlAttributions = new ArrayList<Object>();
    private List<Result> results = new ArrayList<Result>();
    private String status;


    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }


    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class Geometry {

        private Location location;

        public Location getLocation() {
            return location;
        }


        public void setLocation(Location location) {
            this.location = location;
        }

    }

    public class Location {

        private Double lat;
        private Double lng;

        public Location(Double lat, Double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }


        public void setLng(Double lng) {
            this.lng = lng;
        }

    }

    public class OpeningHours {

        private Boolean openNow;
        private List<Object> weekdayText = new ArrayList<Object>();


        public Boolean getOpenNow() {
            return openNow;
        }


        public void setOpenNow(Boolean openNow) {
            this.openNow = openNow;
        }


        public List<Object> getWeekdayText() {
            return weekdayText;
        }


        public void setWeekdayText(List<Object> weekdayText) {
            this.weekdayText = weekdayText;
        }

    }

    public class Photo {

        private Integer height;
        private List<String> htmlAttributions = new ArrayList<String>();
        private String photoReference;
        private Integer width;

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public List<String> getHtmlAttributions() {
            return htmlAttributions;
        }

        public void setHtmlAttributions(List<String> htmlAttributions) {
            this.htmlAttributions = htmlAttributions;
        }


        public String getPhotoReference() {
            return photoReference;
        }


        public void setPhotoReference(String photoReference) {
            this.photoReference = photoReference;
        }

        public Integer getWidth() {
            return width;
        }


        public void setWidth(Integer width) {
            this.width = width;
        }

    }

    public class Result {

        private Geometry geometry;
        private String icon;
        private String id;
        private String name;
        private OpeningHours openingHours;
        private List<Photo> photos = new ArrayList<Photo>();
        private String placeId;
        private String reference;
        private String scope;
        private List<String> types = new ArrayList<String>();
        private String vicinity;
        private Double rating;


        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }


        public String getId() {
            return id;
        }


        public void setId(String id) {
            this.id = id;
        }


        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }


        public OpeningHours getOpeningHours() {
            return openingHours;
        }


        public void setOpeningHours(OpeningHours openingHours) {
            this.openingHours = openingHours;
        }


        public List<Photo> getPhotos() {
            return photos;
        }


        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }


        public String getPlaceId() {
            return placeId;
        }


        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }


        public String getReference() {
            return reference;
        }


        public void setReference(String reference) {
            this.reference = reference;
        }


        public String getScope() {
            return scope;
        }


        public void setScope(String scope) {
            this.scope = scope;
        }


        public List<String> getTypes() {
            return types;
        }


        public void setTypes(List<String> types) {
            this.types = types;
        }


        public String getVicinity() {
            return vicinity;
        }

        public void setVicinity(String vicinity) {
            this.vicinity = vicinity;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

    }
}
