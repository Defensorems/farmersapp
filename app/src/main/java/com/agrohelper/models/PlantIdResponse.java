package com.agrohelper.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PlantIdResponse {

    @SerializedName("entities")
    private List<Entity> entities;

    public List<Entity> getEntities() {
        return entities != null ? entities : new ArrayList<>();
    }

    public static class Entity {
        @SerializedName("entity_name")
        private String entityName;

        @SerializedName("matched_in")
        private String matchedIn;

        @SerializedName("matched_in_type")
        private String matchedInType;

        public String getEntityName() {
            return entityName;
        }

        public String getMatchedIn() {
            return matchedIn;
        }

        public String getMatchedInType() {
            return matchedInType;
        }
    }
}