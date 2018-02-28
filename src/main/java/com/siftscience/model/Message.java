package com.siftscience.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {
    @Expose @SerializedName("$body") private String body;
    @Expose @SerializedName("$contact_email") private String contactEmail;
    @Expose @SerializedName("$recipient_user_ids") private List<String> recipientUserIds;
    @Expose @SerializedName("$images") private List<Image> images;

    public String getBody() {
        return body;
    }

    public Message setBody(String body) {
        this.body = body;
        return this;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public Message setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public List<Image> getImages() {
        return images;
    }

    public Message setImages(List<Image> images) {
        this.images = images;
        return this;
    }

    public List<String> getRecipientUserIds() {
        return recipientUserIds;
    }

    public Message setRecipientUserIds(List<String> recipientUserIds) {
        this.recipientUserIds = recipientUserIds;
        return this;
    }
}
