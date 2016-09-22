package models.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by attribe on 9/16/16.
 */
public class AutoCompleteResponse implements Serializable {
    public List<Prediction> predictions;
    public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }




    public class Prediction implements Serializable
    {
        public String id;
        public List<MatchedSubstring> matched_substrings;
        public String place_id;
        public String reference;
        public List<Term> terms;
        public List<String> types;
        public String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<MatchedSubstring> getMatched_substrings() {
            return matched_substrings;
        }

        public void setMatched_substrings(List<MatchedSubstring> matched_substrings) {
            this.matched_substrings = matched_substrings;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public List<Term> getTerms() {
            return terms;
        }

        public void setTerms(List<Term> terms) {
            this.terms = terms;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }


    }
    public class Term implements Serializable
    {
        public int offset;
        public String value;
        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }


    }
    public class MatchedSubstring implements Serializable
    {
        public int length;
        public int offset;
        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }


    }
}
