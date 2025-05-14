package com.agrohelper.models;

public class ReferenceItem {
    private String name;
    private String type;
    private String description;
    private String careInstructions;
    private int imageResId;

    public ReferenceItem(String name, String type, String description, String careInstructions, int imageResId) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.careInstructions = careInstructions;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCareInstructions() {
        return careInstructions;
    }

    public void setCareInstructions(String careInstructions) {
        this.careInstructions = careInstructions;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
