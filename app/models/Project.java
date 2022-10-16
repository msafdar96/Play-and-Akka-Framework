package models;

import java.util.Date;
import java.util.List;
import java.util.*;

/**
 * Model class for storing the Project details and Project words stats for each search
 * @author Group
 */

public class Project {

    private String id;
    private String ownerID;
    private String previewDescription;
    private Date timeSubmitted;
    private String title;
    private String type;
    private List<String> skills;

    //    Misbah Edit
    private Integer twords;
    private Integer uwords;
    private Integer charecters;
    private Integer sentences;
    private float charsPerWord;
    private float wordsPerSent;
    private float charsPerSent;
    private Map<String, Integer> wordStats;



    public Project(){

    }

    /**
     * constructor used to store only some details of the project
     * @param id
     * @param ownerID
     * @param timeSubmitted
     * @param title
     * @param type
     * @param skills
     */
    public Project(String id, String ownerID, Date timeSubmitted, String title, String type, List<String> skills) {
        this.id = id;
        this.ownerID = ownerID;
        this.timeSubmitted = timeSubmitted;
        this.title = title;
        this.type = type;
        this.skills = skills;
    }

    /**
     * This constructor sets value for all the variable of Project model class
     * @param id
     * @param ownerID
     * @param previewDescription
     * @param timeSubmitted
     * @param title
     * @param type
     * @param skills
     * @param twords
     * @param uwords
     * @param charecters
     * @param sentences
     * @param charsPerWord
     * @param wordsPerSent
     * @param charsPerSent
     * @param wordStats
     */
    public Project(String id, String ownerID, String previewDescription, Date timeSubmitted, String title, String type, List<String> skills, Integer twords, Integer uwords, Integer charecters, Integer sentences, float charsPerWord, float wordsPerSent, float charsPerSent, Map<String, Integer> wordStats) {
        this.ownerID = ownerID;
        this.previewDescription = previewDescription;
        this.timeSubmitted = timeSubmitted;
        this.title = title;
        this.type = type;
        this.skills = skills;
        this.twords = twords;
        this.uwords = uwords;
        this.charecters = charecters;
        this.sentences = sentences;
        this.charsPerWord = charsPerWord;
        this.wordsPerSent = wordsPerSent;
        this.charsPerSent = charsPerSent;
        this.wordStats = wordStats;
    }

    public Project(String id, String ownerID, String previewDescription, Date timeSubmitted, String title, String type, List<String> skills) {
        this.id = id;
        this.ownerID = ownerID;
        this.previewDescription = previewDescription;
        this.timeSubmitted = timeSubmitted;
        this.title = title;
        this.type = type;
        this.skills = skills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * getter method
     * @return return the owner id variable
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * This setter method sets value for ownerid variable
     * @param ownerID
     */
    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * getter method
     * @return return the preview description variable
     */
    public String getPreviewDescription() {
        return previewDescription;
    }

    /**
     * This setter method sets value for preview description variable
     * @param previewDescription
     */
    public void setPreviewDescription(String previewDescription) {
        this.previewDescription = previewDescription;
    }

    /**
     * getter method
     * @return return the time variable
     */
    public Date getTimeSubmitted() {
        return timeSubmitted;
    }

    /**
     * This setter method sets value for time variable
     * @param timeSubmitted
     */
    public void setTimeSubmitted(Date timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    /**
     * getter method
     * @return return the title variable
     */
    public String getTitle() {
        return title;
    }

    /**
     * This setter method sets value for title variable
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter method
     * @return return the type variable
     */
    public String getType() {
        return type;
    }

    /**
     * This setter method sets value for type variable
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter method
     * @return return the skills variable
     */
    public List<String> getSkills() {
        return skills;
    }

    /**
     * This setter method sets value for skills variable
     * @param skills
     */
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    /**
     * getter method
     * @return return the tword variable
     */
    public Integer getTwords() {
        return twords;
    }

    /**
     * This setter method sets value for twords variable
     * @param twords
     */
    public void setTwords(Integer twords) {
        this.twords = twords;
    }

    /**
     * getter method
     * @return the value of uword variable
     */
    public Integer getUwords() {
        return uwords;
    }

    /**
     * This setter method sets value for uword variable
     * @param uwords
     */
    public void setUwords(Integer uwords) {
        this.uwords = uwords;
    }

    /**
     * getter method
     * @return the value of character variable
     */
    public Integer getCharecters() {
        return charecters;
    }

    /**
     * This setter method sets value for character variable
     * @param charecters
     */
    public void setCharecters(Integer charecters) {
        this.charecters = charecters;
    }

    /**
     * getter method
     * @return the value of sentences variable
     */
    public Integer getSentences() {
        return sentences;
    }

    /**
     * This setter method sets value for sentences variable
     * @param sentences
     */
    public void setSentences(Integer sentences) {
        this.sentences = sentences;
    }

    /**
     * getter method
     * @return the value of charsperword variable
     */
    public float getCharsPerWord() {
        return charsPerWord;
    }

    /**
     * This setter method sets value for charsperword variable
     * @param charsPerWord
     */
    public void setCharsPerWord(float charsPerWord) {
        this.charsPerWord = charsPerWord;
    }

    /**
     * getter method
     * @return the value of wordspersent variable
     */
    public float getWordsPerSent() {
        return wordsPerSent;
    }

    /**
     * This setter method sets value for wordspersent variable
     * @param wordsPerSent
     */
    public void setWordsPerSent(float wordsPerSent) {
        this.wordsPerSent = wordsPerSent;
    }

    /**
     * getter method
     * @return the value of charspersent variable
     */
    public float getCharsPerSent() {
        return charsPerSent;
    }

    /**
     * This setter method sets value for charspersent variable
     * @param charsPerSent
     */
    public void setCharsPerSent(float charsPerSent) {
        this.charsPerSent = charsPerSent;
    }

    /**
     * getter method
     * @return the value of wordstats variable
     */
    public Map<String, Integer> getWordStats() {
        return wordStats;
    }

    /**
     * This setter method sets value for word stats variable
     * @param wordStats
     */
    public void setWordStats(Map<String, Integer> wordStats) {
        this.wordStats = wordStats;
    }
}
