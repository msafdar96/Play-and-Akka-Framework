package models;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Model class defines the structure of a DescriptionReadability
 * @author Sahran Khuwaja
 */

public class DescriptionReadability {
    double averageFleschReadabilityIndex;
    double averageFleschKincaidGradeLevel;
    double overallFleschReadabilityIndex;
    double overallFleschKincaidGradeLevel;
    List<Double> fleschReadabilityIndexOfEachProject;
    List<Double> fleschKincaidGradeLevelOfEachProject;
    List<String> educationLevelOfEachProject;
    List<Integer> numberOfSentencesOfEachProject;
    List<Integer> numberOfWordsOfEachProject;
    List<Integer> numberOfSyllablesOfEachProject;

    public HashMap <String, DescriptionReadability> readability = new HashMap<String, DescriptionReadability>();
    public DescriptionReadability descriptionReadability;
    public String previewDescription;
    /**
     * This is a default constructor
     */
    public DescriptionReadability(){

    }

    public DescriptionReadability(String previewDescription){
        this.previewDescription = previewDescription;
    }

    /**
     * This method calculates Description Readability Of AllProjects
     * all this is done asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method
     * @author Sahran Khuwaja
     * @param keyword
     * @param projects
     * @return returns the <code>CompletionStage boolean </code> value true on completion
     */

    public CompletionStage<Boolean> calculateDescriptionReadabilityOfAllProjects(String keyword, JsonNode projects){
        AtomicInteger numberOfSentences = new AtomicInteger();
        AtomicInteger numberOfWords = new AtomicInteger();
        AtomicInteger numberOfSyllables = new AtomicInteger();
        descriptionReadability = new DescriptionReadability();
        CompletionStage <List<String>> sentences = this.getAllSentences(projects)
                .thenApplyAsync(e->{
                    numberOfSentences.set(e.size());
                    return e;
                });
        CompletionStage<List<String>> words =  sentences
                .thenApplyAsync(e->this.getAllWords(e))
                .thenComposeAsync(e->e.thenApplyAsync(el->{
                    numberOfWords.set(el.size());
                    return el;
                }));

        CompletionStage<Integer> syllables =  words
                .thenApplyAsync(e-> this.getAllSyllables(e))
                .thenComposeAsync(e->e.thenApplyAsync(el->{
                    numberOfSyllables.set(el);
                    return el;
                }));

        syllables.thenApplyAsync(e->{
            double fleschReadabilityIndex = this.calculateFleschReadabilityIndex(
                    Double.valueOf(numberOfSentences.get()),
                    Double.valueOf(numberOfWords.get()),
                    Double.valueOf(numberOfSyllables.get()));
            double fleschKincaidGradeLevel = this.calculateFleschKincaidGradeLevel(
                    Double.valueOf(numberOfSentences.get()),
                    Double.valueOf(numberOfWords.get()),
                    Double.valueOf(numberOfSyllables.get()));

            descriptionReadability.setOverallFleschReadabilityIndex(fleschReadabilityIndex);
            descriptionReadability.setOverallFleschKincaidGradeLevel(fleschKincaidGradeLevel);
            this.setOverallFleschReadabilityIndex(fleschReadabilityIndex);
            this.setOverallFleschKincaidGradeLevel(fleschKincaidGradeLevel);
            return e;
        });

        return syllables.thenApplyAsync(e->true);
    }

    /**
     * This method calculates Description Readability Of Each Projects
     * all this is done asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method
     * @author Sahran Khuwaja
     * @param keyword
     * @param projects
     * @return returns the <code>CompletionStage boolean </code> value true on completion
     */
    public CompletionStage<Boolean> calculateDescriptionReadabilityOfEachProject(String keyword, JsonNode projects){
        AtomicReference<List<Integer>> numberOfSentencesForEachProject = new AtomicReference<List<Integer>>();
        AtomicReference<List<Integer>> numberOfWordsForEachProject = new AtomicReference<List<Integer>>();
        AtomicReference<List<Integer>> numberOfSyllablesForEachProject = new AtomicReference<List<Integer>>();
        CompletionStage <List<List<String>>> sentencesOfEachProject =
                this.getAllSentencesFromEachProject(projects);

        CompletionStage<List<Integer>> sentencesCountOfEachProject =
                sentencesOfEachProject.thenApplyAsync(e->this.countNumberOfSentencesForEachProject(e))
                        .thenComposeAsync(e->e.thenApplyAsync(el->{
                            numberOfSentencesForEachProject.set(el);
                            return  el;
                        }));
        CompletionStage <List<List<String>>> wordsOfEachProject =  sentencesOfEachProject
                .thenApplyAsync(e->this.getAllWordsFromEachProject(e))
                .thenComposeAsync(e->e.thenApplyAsync(el->el));

        CompletionStage<List<Integer>> wordsCountOfEachProject =
                wordsOfEachProject.thenApplyAsync(e->this.countNumberOfWordsForEachProject(e))
                        .thenComposeAsync(e->e.thenApplyAsync(el->{
                            numberOfWordsForEachProject.set(el);
                            return  el;
                        }));
        CompletionStage<List<Integer>> syllablesOfEachProject =  wordsOfEachProject
                .thenApplyAsync(e-> this.getAllSyllablesFromEachProject(e))
                .thenComposeAsync(e->e.thenApplyAsync(el->{
                    numberOfSyllablesForEachProject.set(el);
                    return el;
                }));
        if(descriptionReadability == null){
            descriptionReadability = new DescriptionReadability();
        }
        CompletionStage<List<Double>> fleschReadabilityIndexOfEachProject =
                syllablesOfEachProject.thenApplyAsync(e->
                                this.calculateFleschReadabilityIndexForEachProject(
                                        numberOfSentencesForEachProject.get(),
                                        numberOfWordsForEachProject.get(),
                                        numberOfSyllablesForEachProject.get()))
                        .thenComposeAsync(e->e.thenApplyAsync(el->{
                            descriptionReadability.setFleschReadabilityIndexOfEachProject(el);
                            return el;
                        }));

        CompletionStage<List<Double>> fleschKincaidGradeLevelOfEachProject =
                syllablesOfEachProject.thenApplyAsync(e->
                                this.calculateFleschKincaidGradeLevelForEachProject(
                                        numberOfSentencesForEachProject.get(),
                                        numberOfWordsForEachProject.get(),
                                        numberOfSyllablesForEachProject.get()))
                        .thenComposeAsync(e->e.thenApplyAsync(el->{
                            descriptionReadability.setFleschKincaidGradeLevelOfEachProject(el);
                            return el;
                        }));

        CompletionStage<Double> averageFleschReadabilityIndexOfAllProjects =
                fleschReadabilityIndexOfEachProject.thenApplyAsync(e->
                                this.calculateAverage(e))
                        .thenComposeAsync(e->e.thenApplyAsync(el->{
                            descriptionReadability.setAverageFleschReadabilityIndex(el);
                            this.setAverageFleschReadabilityIndex(el);
                            return el;
                        }));

        CompletionStage<Double> averageFleschKincaidGradeLevelOfAllProjects =
                fleschKincaidGradeLevelOfEachProject.thenApplyAsync(e->
                                this.calculateAverage(e))
                        .thenComposeAsync(e->e.thenApplyAsync(el->{
                            descriptionReadability.setAverageFleschKincaidGradeLevel(el);
                            this.setAverageFleschKincaidGradeLevel(el);
                            readability.put(keyword.toLowerCase(),descriptionReadability);
                            return el;
                        }));
        CompletionStage<List<String>> educationalLevelOfEachProject = fleschReadabilityIndexOfEachProject
                .thenApplyAsync(e->this.calculateEducationalLevelForEachProject(e))
                .thenComposeAsync(e->e.thenApplyAsync(el->{
                    descriptionReadability.setEducationLevelOfEachProject(el);
                    return el;
                }));

        return averageFleschReadabilityIndexOfAllProjects
                .thenApplyAsync(e->averageFleschKincaidGradeLevelOfAllProjects)
                .thenComposeAsync(e->e.thenApplyAsync(el->educationalLevelOfEachProject))
                .thenComposeAsync(e->e.thenApplyAsync(el->true));

    }

    /**
     * This method fetches all the sentences
     * all this is done asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method
     * @author Sahran Khuwaja
     * @param projects
     * @return returns the <code>CompletionStage list of sentences </code> on completion
     */
    public CompletionStage<List<String>> getAllSentences(JsonNode projects){
        CompletionStage<List<String>> result = CompletableFuture.supplyAsync(()->{
            List<String> sentences = StreamSupport
                    .stream(projects.spliterator(), true)
                    .parallel()
                    .map(e -> e.get("previewDescription").textValue())
                    .map(e-> Arrays.asList(this.getSentences(e)))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            return  sentences;
        });
        return result;
    }

    /**
     * This method fetches all words from the sentences
     * all this is done asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method
     * @author Sahran Khuwaja
     * @param sentences
     * @return returns the <code>CompletionStage list words </code> on completion
     */
    public CompletionStage<List<String>> getAllWords(List<String> sentences){
        CompletionStage<List<String>> result = CompletableFuture.supplyAsync(()->{
            List<String> words = sentences.stream()
                    .parallel()
                    .map(e -> Arrays.asList(this.getWords(e)))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            return  words;
        });
        return result;
    }

    /**
     * This method fetches all the syllables
     * all this is done asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method
     * @author Sahran Khuwaja
     * @param words
     * @return returns the <code>CompletionStage count</code>on completion
     */
    public CompletionStage<Integer> getAllSyllables(List<String> words){
        CompletionStage<Integer> result = CompletableFuture.supplyAsync(()->{
            AtomicInteger count = new AtomicInteger(0);
            words.stream()
                    .parallel()
                    .forEach(e-> count.getAndAdd(this.getSyllablesCount(e.toLowerCase())));
            return count.get();
        });
        return result;
    }

    /**
     * This method fetches all the sentences from all the projects
     * all this is done asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method
     * @author Sahran Khuwaja
     * @param projects
     * @return returns the <code>CompletionStage list of sentences of all the projects </code>on completion
     */
    public CompletionStage<List<List<String>>> getAllSentencesFromEachProject(JsonNode projects){
        CompletionStage<List<List<String>>> result = CompletableFuture.supplyAsync(()->{
            List<List<String>> sentences = StreamSupport
                    .stream(projects.spliterator(), true)
                    .parallel()
                    .map(e -> e.get("previewDescription").textValue())
                    .map(e-> Arrays.asList(this.getSentences(e)))
                    .collect(Collectors.toList());
            return  sentences;
        });
        return result;
    }

    /**
     * This method fetches all the words of sentences from all the projects
     * all this is done asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method
     * @author Sahran Khuwaja
     * @param sentences
     * @return returns the <code>CompletionStage list of words of all the sentences </code>on completion
     */
    public CompletionStage<List<List<String>>> getAllWordsFromEachProject(List<List<String>> sentences){
        CompletionStage<List<List<String>>> result = CompletableFuture.supplyAsync(()->{
            List<List<String>> words = sentences.stream()
                    .parallel()
                    .map(e -> {
                        return e.stream()
                                .parallel()
                                .map(el->Arrays.asList(this.getWords(el)))
                                .flatMap(List::stream)
                                .collect(Collectors.toList());
                    })
                    .collect(Collectors.toList());
            return  words;
        });
        return result;
    }

    /**
     * This method fetches all the syllables count from all the projects
     * all this is done asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method
     * @author Sahran Khuwaja
     * @param words
     * @return returns the <code>CompletionStage list of syllables count of all the projects </code>on completion
     */
    public CompletionStage<List<Integer>> getAllSyllablesFromEachProject(List<List<String>> words){
        CompletionStage<List<Integer>> result = CompletableFuture.supplyAsync(()->{
            return words.stream()
                    .parallel()
                    .map(e-> {
                        AtomicInteger count = new AtomicInteger(0);
                        e.stream()
                                .parallel()
                                .forEach(el->{
                                    count.getAndAdd(this.getSyllablesCount(el.toLowerCase()));
                                });
                        return count.get();
                    })
                    .collect(Collectors.toList());
        });
        return result;
    }

    /**
     * to fetch all the sentences
     * @author Sahran Khuwaja
     * @param sentence
     * @return array of sentences
     */
    public String[] getSentences(String sentence){
        String[] sentences = Arrays.stream(sentence.split("\\.|!|\\?|:|;"))
                .parallel()
                .map(e->e.replaceAll("(?m)^[ \t]*\r?\n", "").trim())
                .toArray(String[]::new);
        return sentences;
    }

    /**
     *to fetch all the words from sentences
     * @author Sahran Khuwaja
     * @param sentence
     * @return array of words
     */
    public String[] getWords(String sentence){
        String[] words = Arrays.stream(sentence.split("\\W+"))
                .parallel()
                .map(e->e.replaceAll("\\d|_",""))
                .filter(e->!e.isEmpty())
                .toArray(String[]::new);
        return words;
    }

    /**
     * to get the syllables count of a word
     *@author Sahran Khuwaja
     * @param word
     * @return return count
     */
    public int getSyllablesCount(String word) {
        char lastChar = word.charAt(word.length()-1);
        char secondLastChar = !(word.length()<=2)?word.charAt(word.length()-2):lastChar;
        if((lastChar == 'e' && secondLastChar != 'l' && !(word.length()<=3)) ){
            word = word.substring(0,word.length()-1);
        }
        if(secondLastChar=='e' && (lastChar == 's' || lastChar == 'd') && !(word.length()<=2) ){
            word = word.substring(0,word.length()-2);
        }
        final String finalString = word;
        AtomicInteger count = new AtomicInteger();
        AtomicBoolean prevCharVowel = new AtomicBoolean(false);
        AtomicReference<Character> prevChar = new AtomicReference<>('\0');
        AtomicBoolean found = new AtomicBoolean(false);
        Arrays.stream(finalString.split(""))
                //.parallel()
                .forEach(e->{
                    char character = e.charAt(0);
                    boolean vowel = isVowel(character);
                    if(vowel && !prevCharVowel.get() && !found.get()){
                        if(finalString.length()<=3){
                            found.set(true);
                        }
                        prevCharVowel.set(true);
                        count.getAndIncrement();
                    }else {
                        if(prevCharVowel.get()){
                            prevCharVowel.set(false);
                        }
                    }
                    prevChar.set(character);
                });
        return count.get();
    }
    public boolean isVowel(char c) {
        return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')?true:false;
    }

    /**
     * counts the number of sentences of all the projects
     * @author Sahran Khuwaja
     * @param sentences
     * @return count of sentences
     */
    public CompletionStage<List<Integer>> countNumberOfSentencesForEachProject(List<List<String>> sentences){
        CompletionStage<List<Integer>> result = CompletableFuture.supplyAsync(()->{
            List<Integer> numberOfSentencesForEachProject = sentences.stream()
                    .parallel()
                    .map(e->e.size())
                    .collect(Collectors.toList());
            return numberOfSentencesForEachProject;
        });
        return  result;
    }

    /**
     *counts the number of words of all the projects
     * @author Sahran Khuwaja
     * @param words
     * @return list of count
     */
    public CompletionStage<List<Integer>> countNumberOfWordsForEachProject(List<List<String>> words){
        CompletionStage<List<Integer>> result = CompletableFuture.supplyAsync(()->{
            List<Integer> numberOfWordsForEachProject = words.stream()
                    .parallel()
                    .map(e->e.size())
                    .collect(Collectors.toList());
            return numberOfWordsForEachProject;
        });
        return  result;
    }

    /**
     *calculate Flesch Readability Index For Each Project
     * @author Sahran Khuwaja
     * @param numberOfSentences
     * @param numberOfWords
     * @param numberOfSyllables
     * @return the list of Flesch Readability Index
     */
    public CompletionStage<List<Double>> calculateFleschReadabilityIndexForEachProject
    (List<Integer> numberOfSentences, List<Integer> numberOfWords,
     List<Integer> numberOfSyllables){
        CompletionStage<List<Double>> result = CompletableFuture.supplyAsync(()-> {
            List<Double> fleschReadabilityIndex = new ArrayList<Double>();
            IntStream
                    .range(0, numberOfSentences.size())
                    .forEach(index ->
                            fleschReadabilityIndex.add(this.calculateFleschReadabilityIndex(
                                    Double.valueOf(numberOfSentences.get(index)),
                                    Double.valueOf(numberOfWords.get(index)),
                                    Double.valueOf(numberOfSyllables.get(index))
                            ))
                    );
            return fleschReadabilityIndex;
        });
        return result;
    }

    /**
     * @author Sahran Khuwaja
     * calculate Flesch Kincaid Grade Level For Each Project
     * @param numberOfSentences
     * @param numberOfWords
     * @param numberOfSyllables
     * @return list of Flesch Kincaid Grade Level For Each Project
     */
    public CompletionStage<List<Double>> calculateFleschKincaidGradeLevelForEachProject
    (List<Integer> numberOfSentences, List<Integer> numberOfWords,
     List<Integer> numberOfSyllables){
        CompletionStage<List<Double>> result = CompletableFuture.supplyAsync(()-> {
            List<Double> fleschKincaidGradeLevel = new ArrayList<Double>();
            IntStream
                    .range(0, numberOfSentences.size())
                    .forEach(index ->
                            fleschKincaidGradeLevel.add(this.calculateFleschKincaidGradeLevel(
                                    Double.valueOf(numberOfSentences.get(index)),
                                    Double.valueOf(numberOfWords.get(index)),
                                    Double.valueOf(numberOfSyllables.get(index))
                            ))
                    );
            return fleschKincaidGradeLevel;
        });
        return result;
    }

    /**
     * calculate Flesch Readability Index
     * @param numberOfSentences
     * @param numberOfWords
     * @param numberOfSyllables
     * @return FleschReadabilityIndex
     */
    public double calculateFleschReadabilityIndex(double numberOfSentences,
                                                  double numberOfWords, double numberOfSyllables){
        double fleschReadabilityIndex  = 206.835 -
                1.015 * (numberOfWords / numberOfSentences) -
                84.6 * ( numberOfSyllables / numberOfWords);
        return  fleschReadabilityIndex;
    }

    /**
     * calculate Flesch Kincaid Grade Level
     * @author Sahran Khuwaja
     * @param numberOfSentences
     * @param numberOfWords
     * @param numberOfSyllables
     * @return FleschKincaidGradeLevel
     */
    public double calculateFleschKincaidGradeLevel(double numberOfSentences,
                                                   double numberOfWords, double numberOfSyllables){
        double fleschKincaidGradeLevel  = 0.39 * (numberOfWords / numberOfSentences) +
                11.8 * (numberOfSyllables / numberOfWords) -
                15.59;
        return  fleschKincaidGradeLevel;
    }

    /**
     * calculate Educational Level For Each Project
     * @author Sahran Khuwaja
     * @param fleschReadabilityIndexOfEachProject
     * @return List of EducationalLevelForEachProject
     */
    public CompletionStage<List<String>> calculateEducationalLevelForEachProject(
            List<Double> fleschReadabilityIndexOfEachProject){
        CompletionStage<List<String>> result = CompletableFuture.supplyAsync(()->{
            List<String> educationalLevelOfEachProject = fleschReadabilityIndexOfEachProject
                    .stream()
                    .parallel()
                    .map(e->this.calculateEducationalLevel(e))
                    .collect(Collectors.toList());
            return educationalLevelOfEachProject;
        });
        return result;
    }

    /**
     * calculate Educational Level
     * @author Sahran Khuwaja
     * @param fleschReadabilityIndex
     * @return Educational Level
     */
    public String calculateEducationalLevel(double fleschReadabilityIndex){
        String educationalLevel = null;
        if(fleschReadabilityIndex>100){
            educationalLevel = "Early";
        }else if(fleschReadabilityIndex>90 && fleschReadabilityIndex<=100){
            educationalLevel = "5th grade";
        }else if(fleschReadabilityIndex>80 && fleschReadabilityIndex<=90){
            educationalLevel = "6th grade";
        }else if(fleschReadabilityIndex>70 && fleschReadabilityIndex<=80){
            educationalLevel = "7th grade";
        }else if(fleschReadabilityIndex>65 && fleschReadabilityIndex<=70){
            educationalLevel = "8th grade";
        }else if(fleschReadabilityIndex>60 && fleschReadabilityIndex<=65){
            educationalLevel = "9th grade";
        }else if(fleschReadabilityIndex>50 && fleschReadabilityIndex<=60){
            educationalLevel = "High School (10th to 12th grade)";
        } else if(fleschReadabilityIndex>30 && fleschReadabilityIndex<=50){
            educationalLevel = "Some College";
        }else if(fleschReadabilityIndex>10 && fleschReadabilityIndex<=30){
            educationalLevel = "College Graduate";
        }else{
            educationalLevel = "Law School Graduate / Professional";
        }
        return educationalLevel;
    }

    /**
     * calculate average
     * @author Sahran Khuwaja
     * @param data
     * @return average
     */
    public CompletionStage<Double> calculateAverage(List<Double> data){
        CompletionStage<Double> result = CompletableFuture.supplyAsync(()->{
            double average = data.stream()
                    .parallel()
                    .filter(e->!e.isNaN())
                    .mapToDouble(e->e)
                    .average()
                    .orElse(0.0);
            return average;
        });
        return result;
    }

    /**
     * getter Readability
     * @author Sahran Khuwaja
     * @param keyword
     * @return Readability
     */
    public CompletionStage<DescriptionReadability> getReadability(String keyword){
        CompletionStage<DescriptionReadability> result = CompletableFuture.supplyAsync(()->{
            return readability.get(keyword.toLowerCase());
        });
        return result;
    }





    /**
     *
     * @return averageFleschReadabilityIndex value
     */

    public double getAverageFleschReadabilityIndex() {
        return averageFleschReadabilityIndex;
    }

    /**
     * sets value for averageFleschReadabilityIndex
     * @param averageFleschReadabilityIndex
     *
     */

    public void setAverageFleschReadabilityIndex(double averageFleschReadabilityIndex) {
        this.averageFleschReadabilityIndex = averageFleschReadabilityIndex;
    }

    /**
     *
     * @return averageFleschKincaidGradeLevel value
     */

    public double getAverageFleschKincaidGradeLevel() {
        return averageFleschKincaidGradeLevel;
    }

    /**
     * sets the value of averageFleschKincaidGradeLevel
     * @param averageFleschKincaidGradeLevel
     */

    public void setAverageFleschKincaidGradeLevel(double averageFleschKincaidGradeLevel) {
        this.averageFleschKincaidGradeLevel = averageFleschKincaidGradeLevel;
    }

    /**
     *
     * @return
     */
    public double getOverallFleschReadabilityIndex() {
        return overallFleschReadabilityIndex;
    }

    /**
     * sets the value
     * @param overallFleschReadabilityIndex
     */
    public void setOverallFleschReadabilityIndex(double overallFleschReadabilityIndex) {
        this.overallFleschReadabilityIndex = overallFleschReadabilityIndex;
    }

    /**
     *
     * @return
     */
    public double getOverallFleschKincaidGradeLevel() {
        return overallFleschKincaidGradeLevel;
    }

    /**
     * sets the value
     * @param overallFleschKincaidGradeLevel
     */
    public void setOverallFleschKincaidGradeLevel(double overallFleschKincaidGradeLevel) {
        this.overallFleschKincaidGradeLevel = overallFleschKincaidGradeLevel;
    }

    /**
     *
     * @return
     */
    public List<Double> getFleschReadabilityIndexOfEachProject() {
        return fleschReadabilityIndexOfEachProject;
    }

    /**
     *
     * @param index
     * @return
     */
    public CompletionStage<Double> getFleschReadabilityIndexOfEachProject(int index) {
        CompletionStage<Double> result = CompletableFuture.supplyAsync(()->{
            return fleschReadabilityIndexOfEachProject.get(index);
        });
        return result;
    }

    /**
     * sets the value
     * @param fleschReadabilityIndexOfEachProject
     */
    public void setFleschReadabilityIndexOfEachProject(List<Double> fleschReadabilityIndexOfEachProject) {
        this.fleschReadabilityIndexOfEachProject = fleschReadabilityIndexOfEachProject;
    }

    /**
     *
     * @return FleschKincaidGradeLevelOfEachProject
     */
    public List<Double> getFleschKincaidGradeLevelOfEachProject() {
        return fleschKincaidGradeLevelOfEachProject;
    }

    /**
     *
     * @param index
     * @return
     */
    public CompletionStage<Double> getFleschKincaidGradeLevelOfEachProject(int index) {
        CompletionStage<Double> result = CompletableFuture.supplyAsync(()->{
            return fleschKincaidGradeLevelOfEachProject.get(index);
        });
        return result;
    }

    /**
     * sets the value
     * @param fleschKincaidGradeLevelOfEachProject
     */
    public void setFleschKincaidGradeLevelOfEachProject(List<Double> fleschKincaidGradeLevelOfEachProject) {
        this.fleschKincaidGradeLevelOfEachProject = fleschKincaidGradeLevelOfEachProject;
    }

    /**
     *
     * @return EducationLevelOfEachProject
     */
    public List<String> getEducationLevelOfEachProject() {
        return educationLevelOfEachProject;
    }

    /**
     *
     * @param index
     * @return
     */
    public CompletionStage<String> getEducationLevelOfEachProject(int index) {
        CompletionStage<String> result = CompletableFuture.supplyAsync(()->{
            return educationLevelOfEachProject.get(index);
        });
        return result;
    }

    /**
     * sets the value
     * @param educationLevelOfEachProject
     */
    public void setEducationLevelOfEachProject(List<String> educationLevelOfEachProject) {
        this.educationLevelOfEachProject = educationLevelOfEachProject;
    }

    /**
     *
     * @return getNumberOfSentencesOfEachProject
     */
    public List<Integer> getNumberOfSentencesOfEachProject() {
        return numberOfSentencesOfEachProject;
    }

    /**
     * sets the value
     * @param numberOfSentencesOfEachProject
     */
    public void setNumberOfSentencesOfEachProject(List<Integer> numberOfSentencesOfEachProject) {
        this.numberOfSentencesOfEachProject = numberOfSentencesOfEachProject;
    }

    /**
     *
     * @return getNumberOfWordsOfEachProject
     */
    public List<Integer> getNumberOfWordsOfEachProject() {
        return numberOfWordsOfEachProject;
    }

    /**
     * sets the value
     * @param numberOfWordsOfEachProject
     */
    public void setNumberOfWordsOfEachProject(List<Integer> numberOfWordsOfEachProject) {
        this.numberOfWordsOfEachProject = numberOfWordsOfEachProject;
    }

    /**
     *
     * @return getNumberOfSyllablesOfEachProject
     */
    public List<Integer> getNumberOfSyllablesOfEachProject() {
        return numberOfSyllablesOfEachProject;
    }

    /**
     * sets the value
     * @param numberOfSyllablesOfEachProject
     */
    public void setNumberOfSyllablesOfEachProject(List<Integer> numberOfSyllablesOfEachProject) {
        this.numberOfSyllablesOfEachProject = numberOfSyllablesOfEachProject;
    }

    /**
     *@author Sahran Khuwaja
     * @return map of list of DescriptionReadability of each keyword
     */
    public HashMap<String, DescriptionReadability> getDescriptionReadability(){
        return readability;
    }

    public String getPreviewDescription() {
        return previewDescription;
    }

    public void setPreviewDescription(String previewDescription) {
        this.previewDescription = previewDescription;
    }
}
