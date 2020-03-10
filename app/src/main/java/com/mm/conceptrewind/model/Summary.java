package com.mm.conceptrewind.model;

public class Summary {
    private String summary;
    private String topic;

    public Summary(String summary, String topic) {
        this.summary = summary;
        this.topic = topic;
    }

    public Summary() {
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
