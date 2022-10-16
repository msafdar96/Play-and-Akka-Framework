package models;


import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Model class for storing the GlobalStats data for each search
 * @author Mohammed Misbah Uddin Shareef
 */
public class GlobalStats {

    private Integer twords;
    private Integer uwords;
    private Integer charecters;
    private Integer sentences;
    private float charsPerWord;
    private float wordsPerSent;
    private float charsPerSent;
    private Map<String, Integer> wordStats;
    private LinkedHashMap<String, GlobalStats> gStats = new LinkedHashMap<String, GlobalStats>();
    public GlobalStats globalStats;

    public GlobalStats(){

    }

    /**
     * This constructor sets value for all the variable of Global stats model class
     * @param twords
     * @param uwords
     * @param charecters
     * @param sentences
     * @param charsPerWord
     * @param wordsPerSent
     * @param charsPerSent
     * @param wordStats
     */
    public GlobalStats(Integer twords, Integer uwords, Integer charecters, Integer sentences, float charsPerWord, float wordsPerSent, float charsPerSent, Map<String, Integer> wordStats) {
        this.twords = twords;
        this.uwords = uwords;
        this.charecters = charecters;
        this.sentences = sentences;
        this.charsPerWord = charsPerWord;
        this.wordsPerSent = wordsPerSent;
        this.charsPerSent = charsPerSent;
        this.wordStats = wordStats;
    }

    /**
     * @author Mohammed Misbah Uddin Shareef
     * This method takes the sentence and derive all the unique words from it
     * and calculate the count of each unique word and sort them.
     * @param text
     * @return returns the map of words and their count ordered in descending order.
     */

    public Map<String, Integer> getWStats(String text){

        List<String> words = Arrays.stream(text.split("[^a-zA-Z]+"))
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        Map<String, Integer> wc = words.stream()
                .collect(Collectors.toMap(w -> w, w -> 1, Integer::sum));
        Map<String, Integer> sortedWords = new LinkedHashMap<>();
        wc.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> sortedWords.put(x.getKey(), x.getValue()));

        if(sortedWords.containsKey(" ")){
            sortedWords.remove(" ");
        }
        return sortedWords;

    }

    /**
     * This method parse takes parameters key word and json projects
     * Parse through the json to get all the projects and reads preview description
     * Then gets all the sentences, words, and count unique words and sort them for all the projects.
     * all this is done asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method
     * @author Mohammed Misbah Uddin Shareef
     * @param keyword
     * @param projects
     * @return returns the <code>CompletionStage boolean </code> value true on completion
     */
    public CompletionStage<Boolean> globalWStats(String keyword, JsonNode projects){
        CompletionStage<Boolean> result = CompletableFuture.supplyAsync(()-> {
            DescriptionReadability descriptionReadability = new DescriptionReadability();
            Integer twords;
            Integer uwords;
            Integer charecters;
            Integer sentences;
            float charsPerWord = 0;
            float wordsPerSent = 0;
            float charsPerSent = 0;
            Map<String, Integer> wordStats = new LinkedHashMap<>();


            List<String> sen = StreamSupport
                    .stream(projects.spliterator(), true)
                    .parallel()
                    .map(e ->  e.get("preview_description").textValue() )
                    .map(e-> Arrays.asList(descriptionReadability.getSentences(e)))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            sentences = sen.size();


            List<String> wor = sen.stream()
                    .map(s->s.trim())
                    .map(s -> s.split("[^a-zA-Z]+"))
                    .flatMap(Arrays::stream)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
            twords = wor.size();

            charecters = wor.stream()
                    .mapToInt(String::length)
                    .sum();


            if(twords!=0){
                charsPerWord = (float) charecters/twords;
            }
            if(sentences!=0){
                wordsPerSent = (float) twords/sentences;
                charsPerSent = (float) charecters/sentences;
            }

            Map<String, Integer> wordCount = wor.stream()
                    .collect(Collectors.toMap(w -> w, w -> 1, Integer::sum));

            wordCount.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .forEachOrdered(x -> wordStats.put(x.getKey(), x.getValue()));

            uwords = wordStats.size();

            if(wordStats.containsKey(" ")){
                wordStats.remove(" ");
            }
            globalStats = new GlobalStats(twords,uwords,charecters,sentences,charsPerWord,wordsPerSent,charsPerSent,wordStats);
            gStats.put(keyword, globalStats);

            return true;
        });
        return result;


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

    /**
     * @author Mohammed Misbah Uddin Shareef
     * @return the map of keyword, Global Stats object
     */
    public LinkedHashMap<String, GlobalStats> getGStats() {
        return gStats;
    }

}