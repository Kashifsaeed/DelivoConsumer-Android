package models.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by attribe on 8/24/16.
 */
public class GoogleAPiByText implements Serializable {
    private List<Object> htmlAttributions = new ArrayList<Object>();

    private String nextPageToken;

    private List<Result> results = new ArrayList<Result>();

    private String status;


    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
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


    public class Result implements Serializable{
        private String formatted_address;

        private Geometry geometry;

        private String icon;

        private String id;

        private String name;

        private String placeId;

        private String reference;

        private List<String> types = new ArrayList<String>();

        private List<Photo> photos = new ArrayList<Photo>();

        private Double rating;

        private OpeningHours openingHours;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

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

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public OpeningHours getOpeningHours() {
            return openingHours;
        }

        public void setOpeningHours(OpeningHours openingHours) {
            this.openingHours = openingHours;
        }










    }
    public class Geometry implements Serializable{


        private Location location;

        private Viewport viewport;

        /**
         *
         * @return
         * The location
         */

        public Location getLocation() {
            return location;
        }

        /**
         *
         * @param location
         * The location
         */

        public void setLocation(Location location) {
            this.location = location;
        }

        /**
         *
         * @return
         * The viewport
         */

        public Viewport getViewport() {
            return viewport;
        }

        /**
         *
         * @param viewport
         * The viewport
         */

        public void setViewport(Viewport viewport) {
            this.viewport = viewport;
        }


  public class Viewport implements Serializable{


      private Northeast northeast;

      private Southwest southwest;

      /**
       *
       * @return
       * The northeast
       */

      public Northeast getNortheast() {
          return northeast;
      }

      /**
       *
       * @param northeast
       * The northeast
       */

      public void setNortheast(Northeast northeast) {
          this.northeast = northeast;
      }

      /**
       *
       * @return
       * The southwest
       */

      public Southwest getSouthwest() {
          return southwest;
      }

      /**
       *
       * @param southwest
       * The southwest
       */

      public void setSouthwest(Southwest southwest) {
          this.southwest = southwest;
      }
      public class Southwest implements Serializable{

          private Double lat;

          private Double lng;

          /**
           *
           * @return
           * The lat
           */

          public Double getLat() {
              return lat;
          }

          /**
           *
           * @param lat
           * The lat
           */

          public void setLat(Double lat) {
              this.lat = lat;
          }

          /**
           *
           * @return
           * The lng
           */

          public Double getLng() {
              return lng;
          }

          /**
           *
           * @param lng
           * The lng
           */

          public void setLng(Double lng) {
              this.lng = lng;
          }


      }
      public class Northeast implements Serializable{

          private Double lat;

          private Double lng;

          /**
           *
           * @return
           * The lat
           */

          public Double getLat() {
              return lat;
          }

          /**
           *
           * @param lat
           * The lat
           */

          public void setLat(Double lat) {
              this.lat = lat;
          }

          /**
           *
           * @return
           * The lng
           */

          public Double getLng() {
              return lng;
          }

          /**
           *
           * @param lng
           * The lng
           */

          public void setLng(Double lng) {
              this.lng = lng;
          }


      }

  }


   public class Location implements Serializable{

       private Double lat;

       private Double lng;

       /**
        *
        * @return
        * The lat
        */

       public Double getLat() {
           return lat;
       }

       /**
        *
        * @param lat
        * The lat
        */

       public void setLat(Double lat) {
           this.lat = lat;
       }

       /**
        *
        * @return
        * The lng
        */

       public Double getLng() {
           return lng;
       }

       /**
        *
        * @param lng
        * The lng
        */

       public void setLng(Double lng) {
           this.lng = lng;
       }



   }

    }

    public class Photo implements Serializable{


        private Integer height;

        private List<String> htmlAttributions = new ArrayList<String>();

        private String photoReference;

        private Integer width;

        /**
         *
         * @return
         * The height
         */

        public Integer getHeight() {
            return height;
        }

        /**
         *
         * @param height
         * The height
         */

        public void setHeight(Integer height) {
            this.height = height;
        }

        /**
         *
         * @return
         * The htmlAttributions
         */

        public List<String> getHtmlAttributions() {
            return htmlAttributions;
        }

        /**
         *
         * @param htmlAttributions
         * The html_attributions
         */

        public void setHtmlAttributions(List<String> htmlAttributions) {
            this.htmlAttributions = htmlAttributions;
        }

        /**
         *
         * @return
         * The photoReference
         */

        public String getPhotoReference() {
            return photoReference;
        }

        /**
         *
         * @param photoReference
         * The photo_reference
         */

        public void setPhotoReference(String photoReference) {
            this.photoReference = photoReference;
        }

        /**
         *
         * @return
         * The width
         */

        public Integer getWidth() {
            return width;
        }

        /**
         *
         * @param width
         * The width
         */

        public void setWidth(Integer width) {
            this.width = width;
        }

    }



    public class OpeningHours{

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





}
