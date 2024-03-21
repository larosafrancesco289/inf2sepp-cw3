package model;

/**
 * The FAQItem class is used to store FAQ items.
 * It has two attributes: question and answer.
 * It has a constructor and getters and setters for the attributes.
 */
public class FAQItem {
    private String question; // The question of the FAQ item
    private String answer; // The answer to the FAQ item

    /**
     * Constructs a new FAQItem object with the specified question and answer.
     *
     * @param question the question of the FAQ item
     * @param answer   the answer to the FAQ item
     */
    public FAQItem(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Returns the question of the FAQ item.
     *
     * @return the question of the FAQ item
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the question of the FAQ item.
     *
     * @param question the new question of the FAQ item
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Returns the answer to the FAQ item.
     *
     * @return the answer to the FAQ item
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets the answer to the FAQ item.
     *
     * @param answer the new answer to the FAQ item
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
