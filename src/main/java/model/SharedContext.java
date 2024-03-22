package model;

import java.util.*;

/**
 * The SharedContext class represents a shared context for managing various aspects of the system.
 * It includes functionality related to users, inquiries, FAQ updates, and pages.
 */
public class SharedContext {
    public static final String ADMIN_STAFF_EMAIL = "admin@example.com"; // Email for admin staff
    private Map<String, Collection<String>> faqTopicUpdateSubscribers; // Map of FAQ topic subscribers
    private User currentUser; // Current user in the shared context
    private List<Inquiry> inquiries; // List of inquiries
    private FAQ faq; // FAQ object
    private HashMap<String, Page> pages; // HashMap of pages



    /**
     * Constructs a new SharedContext object with default values.
     */
    public SharedContext() {
        currentUser = new Guest();
        inquiries = new ArrayList<>();
        pages = new HashMap<>();
        faq = new FAQ();
        faqTopicUpdateSubscribers = new HashMap<>();
    }

    /**
     * Adds a page to the shared context.
     *
     * @param page the page to add
     */
    public void addPage(Page page) {
        pages.put(page.getTitle(), page);
    }

    /**
     * Registers a user for updates on a specific FAQ topic.
     *
     * @param email the user's email
     * @param topic the FAQ topic
     * @return true if the user is successfully registered, false otherwise
     */
    public boolean registerForFAQUpdates(String email, String topic) {
        Collection<String> subscribers = faqTopicUpdateSubscribers.getOrDefault(topic, new HashSet<>());
        boolean isAdded = subscribers.add(email);
        faqTopicUpdateSubscribers.put(topic, subscribers);
        return isAdded;
    }

    /**
     * Unregisters a user from updates on a specific FAQ topic.
     *
     * @param email the user's email
     * @param topic the FAQ topic
     * @return true if the user is successfully unregistered, false otherwise
     */
    public boolean unregisterForFAQUpdates(String email, String topic) {
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

    /**
     * Retrieves the users subscribed to updates on a specific FAQ topic.
     *
     * @param topic the FAQ topic
     * @return a collection of email addresses of subscribed users
     */
    public Collection<String> usersSubscribedToFAQTopic(String topic) {
        return faqTopicUpdateSubscribers.getOrDefault(topic, new HashSet<>());
    }

    /**
     * Retrieves the map of FAQ topic subscribers.
     *
     * @return the map of FAQ topic subscribers
     */
    public Map<String, Collection<String>> getFaqTopicUpdateSubscribers() {
        return faqTopicUpdateSubscribers;
    }

    /**
     * Sets the map of FAQ topic subscribers.
     *
     * @param faqTopicUpdateSubscribers the map of FAQ topic subscribers to set
     */
    public void setFaqTopicUpdateSubscribers(Map<String, Collection<String>> faqTopicUpdateSubscribers) {
        this.faqTopicUpdateSubscribers = faqTopicUpdateSubscribers;
    }

    /**
     * Retrieves the current user in the shared context.
     *
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user in the shared context.
     *
     * @param currentUser the current user to set
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Retrieves the list of inquiries in the shared context.
     *
     * @return the list of inquiries
     */
    public List<Inquiry> getInquiries() {
        return inquiries;
    }

    /**
     * Sets the list of inquiries in the shared context.
     *
     * @param inquiries the list of inquiries to set
     */
    public void setInquiries(List<Inquiry> inquiries) {
        this.inquiries = inquiries;
    }

    /**
     * Retrieves the FAQ object in the shared context.
     *
     * @return the FAQ object
     */
    public FAQ getFaq() {
        return faq;
    }

    /**
     * Sets the FAQ object in the shared context.
     *
     * @param faq the FAQ object to set
     */
    public void setFaq(FAQ faq) {
        this.faq = faq;
    }

    /**
     * Retrieves the HashMap of pages in the shared context.
     *
     * @return the HashMap of pages
     */
    public HashMap<String, Page> getPages() {
        return pages;
    }

    /**
    * Sets the HashMap of pages in
    * the shared context.
    * @param pages the HashMap of pages to set
    */
    public void setPages(HashMap<String, Page> pages) {
        this.pages = pages;
    }
}
