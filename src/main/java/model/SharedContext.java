package model;

import java.util.*;

public class SharedContext {
    public static final String ADMIN_STAFF_EMAIL = "admin@example.com";
    private Map<String, Collection<String>> faqTopicUpdateSubscribers;
    private User currentUser;
    private List<Inquiry> inquiries;
    private FAQ faq;
    private List<Page> pages;


    public SharedContext() {
        faqTopicUpdateSubscribers = new HashMap<>();
    }

    public void addPage(Page page) {
        pages.add(page);
    }

    public boolean registerForFAQUpdates(String topic, String email) {
        Collection<String> subscribers = faqTopicUpdateSubscribers.getOrDefault(topic, new HashSet<>());
        boolean isAdded = subscribers.add(email);
        faqTopicUpdateSubscribers.put(topic, subscribers);
        return isAdded;
    }

    public boolean unregisterForFAQUpdates(String topic, String email) {
        if (faqTopicUpdateSubscribers.containsKey(topic)) {
            Collection<String> subscribers = faqTopicUpdateSubscribers.get(topic);
            boolean isRemoved = subscribers.remove(email);
            if (subscribers.isEmpty()) {
                faqTopicUpdateSubscribers.remove(topic);
            }
            return isRemoved;
        }
        return false;
    }

    public Collection<String> usersSubscribedToFAQTopic(String topic) {
        return faqTopicUpdateSubscribers.getOrDefault(topic, new HashSet<>());
    }

    public Map<String, Collection<String>> getFaqTopicUpdateSubscribers() {
        return faqTopicUpdateSubscribers;
    }

    public void setFaqTopicUpdateSubscribers(Map<String, Collection<String>> faqTopicUpdateSubscribers) {
        this.faqTopicUpdateSubscribers = faqTopicUpdateSubscribers;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<Inquiry> getInquiries() {
        return inquiries;
    }

    public void setInquiries(List<Inquiry> inquiries) {
        this.inquiries = inquiries;
    }

    public FAQ getFaq() {
        return faq;
    }

    public void setFaq(FAQ faq) {
        this.faq = faq;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
