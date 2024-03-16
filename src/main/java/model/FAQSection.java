package model;

import java.util.ArrayList;
import java.util.List;

public class FAQSection {
    private String topic;
    private boolean isPrivate;
    private List<FAQItem> items;
    private List<FAQSection> subsections;

    public FAQSection(String topic) {
        this.topic = topic;
        this.subsections = new ArrayList<>();
        // isPrivate can be set based on some condition or through a setter
    }

    public void addSubsection(FAQSection faqSection) {
        subsections.add(faqSection);
    }

    public void addItem(String question, String answer) {
        items.add(new FAQItem(question, answer));
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public List<FAQItem> getItems() {
        return items;
    }

    public void setItems(List<FAQItem> items) {
        this.items = items;
    }

    public List<FAQSection> getSubsections() {
        return subsections;
    }

    public void setSubsections(List<FAQSection> subsections) {
        this.subsections = subsections;
    }
}
