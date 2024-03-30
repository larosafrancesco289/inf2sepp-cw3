package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a section in the FAQ.
 * A section can have subsections and items.
 * A section can be private or public.
 */
public class FAQSection {
    private String topic; // The topic of the FAQ section
    private boolean isPrivate; // Indicates whether the section is private
    private FAQSection parent; // The parent section of this section, if any
    private List<FAQSection> subsections; // List of subsections within this section
    private List<FAQItem> items; // List of items within this section

    /**
     * Constructs a new FAQSection object with the specified topic.
     *
     * @param topic the topic of the FAQ section
     */
    public FAQSection(String topic) {
        this.topic = topic;
        this.subsections = new ArrayList<>();
        this.items = new ArrayList<>();
        // isPrivate is not necessary for this implementation
    }

    /**
     * Adds a subsection to the section.
     *
     * @param faqSection the subsection to be added
     */
    public void addSubsection(FAQSection faqSection) {
        subsections.add(faqSection);
    }

    /**
     * Adds an item to the section.
     *
     * @param question the question of the FAQ item
     * @param answer   the answer to the FAQ item
     */
    public void addItem(String question, String answer) {
        items.add(new FAQItem(question, answer));
    }

    /**
     * Retrieves the topic of this FAQ section.
     *
     * @return the topic of the FAQ section
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Sets the topic of this FAQ section.
     *
     * @param topic the new topic of the FAQ section
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Checks if this FAQ section is private.
     *
     * @return true if the section is private, false otherwise
     */
    public boolean isPrivate() {
        return isPrivate;
    }

    /**
     * Sets the privacy status of this FAQ section.
     *
     * @param aPrivate true to set the section as private, false otherwise
     */
    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    /**
     * Retrieves the parent section of this FAQ section.
     *
     * @return the parent section, or null if there's no parent
     */
    public FAQSection getParent() {
        return parent;
    }

    /**
     * Sets the parent section of this FAQ section.
     *
     * @param parent the new parent section
     */
    public void setParent(FAQSection parent) {
        this.parent = parent;
    }

    /**
     * Retrieves the list of subsections within this FAQ section.
     *
     * @return the list of subsections
     */
    public List<FAQSection> getSubsections() {
        return subsections;
    }

    /**
     * Sets the list of subsections within this FAQ section.
     *
     * @param subsections the new list of subsections
     */
    public void setSubsections(List<FAQSection> subsections) {
        this.subsections = subsections;
    }

    /**
     * Retrieves the list of items within this FAQ section.
     *
     * @return the list of items
     */
    public List<FAQItem> getItems() {
        return items;
    }

    /**
     * Sets the list of items within this FAQ section.
     *
     * @param items the new list of items
     */
    public void setItems(List<FAQItem> items) {
        this.items = items;
    }
}
