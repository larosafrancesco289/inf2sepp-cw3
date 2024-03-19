package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FAQ {
    private List<FAQSection> sections;

    public FAQ() {
        sections = new ArrayList<>();
    }

    public void addSectionItems(FAQSection section) {
        sections.add(section);
    }

    public List<FAQSection> getSections() {
        return sections;
    }

    public void setSections(List<FAQSection> sections) {
        this.sections = sections;
    }
}
