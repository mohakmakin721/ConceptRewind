package com.mm.conceptrewind.model;

public class Transcript {

    private String speechText;
    private String topic;

    public Transcript(String speechText, String topic) {
        this.speechText = speechText;
        this.topic = topic;
    }

    public Transcript() {
    }

    public String getSpeechText() {
        return speechText;
    }

    public void setSpeechText(String speechText) {
        this.speechText = speechText;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
