package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The FAQ class is used to store FAQ sections.
 * It has an attribute called sections.
 * It has a constructor, a method to add sections, and getters and setters for the attributes.
 */
public class FAQ {
    private List<FAQSection> sections; // List to store FAQ sections

    /**
     * Constructs a new FAQ object with an empty list of sections.
     */
    public FAQ() {
        sections = new ArrayList<>();
    }

    /**
     * Adds a section to the FAQ.
     *
     * @param section the FAQSection to be added
     */
    public void addSectionItems(FAQSection section) {
        sections.add(section);
    }

    /**
     * Returns the list of sections in the FAQ.
     *
     * @return the list of sections
     */
    public List<FAQSection> getSections() {
        return sections;
    }

    /**
     * Sets the list of sections in the FAQ.
     *
     * @param sections the new list of sections
     */
    public void setSections(List<FAQSection> sections) {
        this.sections = sections;
    }
}