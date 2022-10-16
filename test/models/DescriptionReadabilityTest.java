package models;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.SearchController;
import helper.HttpApiCall;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http;

import java.util.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.*;


/**
 * Test cases for model class DescriptionReadability
 * @author Sahran Khuwaja
 */
public class DescriptionReadabilityTest {

    @Mock
    private FormFactory formFactory;
    @Mock
    private MessagesApi messagesApi;
    @Mock
    private WSClient wsClient;
    @InjectMocks
    private HttpApiCall httpApiCall;
    @Mock
    private Session session;
    @Mock
    WSResponse wsResponse;
    @InjectMocks
    private SearchController searchController;

    private Http.Request request;
    List<String> test = Arrays.asList("A","b");
    private String userID = null;
    @Mock
    private CompletionStage<List<String>> mockSearch;
    private JsonNode skills = null;
    private JsonNode projects = null;
    private JsonNode project1 = null;
    private JsonNode project2 = null;
    @Mock
    private DescriptionReadability descriptionReadability;
    @Mock
    private Search search;
    @Mock
    private User user;
    @Mock
    private String keyword;

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

    @Before
    public void setup(){
        keyword = "react";
        descriptionReadability = new DescriptionReadability();
        descriptionReadability = new DescriptionReadability(keyword);
        search = new Search();
        search = new Search(keyword);
        wsClient = play.test.WSTestClient.newClient(9000);
        httpApiCall = new HttpApiCall(wsClient);
        this.setupMockData();
        mockSearch = CompletableFuture.completedStage(test);
        mockSearch.thenApplyAsync(e->e);

        userID = "987351";

    }

    public void setupMockData(){
        skills = Json.newArray()
                .add(Json.newObject().put("name","React.js"))
                .add(Json.newObject().put("name","Node.js"))
                .add(Json.newObject().put("name","JavaScript"));
        project1 = Json.newObject()
                .put("id",332392)
                .put("owner_id", 2487292)
                .put("time_submitted", 1647732665)
                .put("title", "NFT App Install for NFT minting website")
                .put("type", "Fixed")
                .put("previewDescription","I need to install a landing page and minting app (React) to create an NFT minting website.")
                .set("jobs",skills);
        project2 = Json.newObject()
                .put("id",332393)
                .put("owner_id", 2487293)
                .put("time_submitted", 1647732665)
                .put("title", "This is a test")
                .put("type", "Fixed")
                .put("previewDescription","This is a test. Need a developer urgently! " +
                        "Answer following questions before bidding: 1) Do you have experience in this field?" +
                        "2) How good you are in coding?")
                .set("jobs",skills);

        projects = Json.newArray()
                .add(project1)
                .add(project2);
        averageFleschReadabilityIndex = 61.57;
        averageFleschKincaidGradeLevel = 7.24;
        overallFleschReadabilityIndex = 66.95;
        overallFleschKincaidGradeLevel = 6.11;
        fleschReadabilityIndexOfEachProject = Arrays.asList(50.61, 82.47, 78.87, 76.23);
        fleschKincaidGradeLevelOfEachProject = Arrays.asList(9.92, 4.24, 3.99, 7.34);
        educationLevelOfEachProject = Arrays.asList("High School (10th to 12th grade)",
                "6th grade", "7th grade", "7th grade");
        numberOfSentencesOfEachProject = Arrays.asList(3, 4, 2, 5);
        numberOfWordsOfEachProject = Arrays.asList(20, 30, 13, 15, 43);
        numberOfSyllablesOfEachProject = Arrays.asList(50, 45, 25, 63);
    }

    /**
     * used to test the calculateDescriptionReadabilityOfAllProjects method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void calculateDescriptionReadabilityOfAllProjects() throws Exception{
        CompletionStage<Boolean> calculateReadability = descriptionReadability.calculateDescriptionReadabilityOfAllProjects(keyword, projects);
        boolean result = calculateReadability.toCompletableFuture().get();
        assertTrue(result);
        assertTrue(descriptionReadability.descriptionReadability!=null);
        assertEquals(descriptionReadability.descriptionReadability.getOverallFleschReadabilityIndex(),81.51432170542637,0);
        assertEquals(descriptionReadability.descriptionReadability.getOverallFleschKincaidGradeLevel(),3.67011627906977,0);
        assertTrue(descriptionReadability.descriptionReadability.getOverallFleschReadabilityIndex()!=0.0);
        assertTrue(descriptionReadability.descriptionReadability.getOverallFleschKincaidGradeLevel()!=0.0);
    }

    /**
     * used to test calculateDescriptionReadabilityOfEachProject
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    @After
    public void calculateDescriptionReadabilityOfEachProject() throws Exception {
        descriptionReadability.descriptionReadability = new DescriptionReadability();
        CompletionStage<Boolean> calculateReadability = descriptionReadability.calculateDescriptionReadabilityOfEachProject(keyword, projects);
        boolean result = calculateReadability.toCompletableFuture().get();
        DescriptionReadability readability = descriptionReadability.getReadability(keyword).toCompletableFuture().get();
        assertTrue(result);
        assertTrue(descriptionReadability.getDescriptionReadability().containsKey(keyword));
        assertTrue(descriptionReadability.getDescriptionReadability().size() > 0);
        assertEquals(readability.getAverageFleschReadabilityIndex(),79.86555882352943, 0);
        assertEquals(readability.getAverageFleschKincaidGradeLevel(), 4.877235294117648,0);
        assertEquals(readability.getFleschReadabilityIndexOfEachProject().size(), 2);
        assertEquals(readability.getFleschKincaidGradeLevelOfEachProject().size(), 2);
        assertEquals(readability.getFleschReadabilityIndexOfEachProject().get(0),85.07411764705884,0);
        assertEquals(readability.getFleschReadabilityIndexOfEachProject().get(1),74.65700000000002,0);
        assertEquals(readability.getFleschKincaidGradeLevelOfEachProject().get(0),5.616470588235295,0);
        assertEquals(readability.getFleschKincaidGradeLevelOfEachProject().get(1),4.138000000000002,0);
    }
    /**
     * used to test getAllSentences
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getAllSentences() throws Exception{
        CompletionStage<List<String>> getSentences = descriptionReadability.getAllSentences(projects);
        List<String> sentences = getSentences.toCompletableFuture().get();
        assertEquals(sentences.size(),6);
        assertEquals(sentences.get(2),"Need a developer urgently");
        assertEquals(sentences.get(sentences.size()-1),"2) How good you are in coding");
        assertNotEquals(sentences.get(5),"Answer following questions before bidding");
        assertEquals(sentences.get(3),"Answer following questions before bidding");
        assertEquals(sentences.get(0),"I need to install a landing page and minting app (React) to create an NFT minting website");
        assertTrue(sentences.size()>0);
    }
    /**
     * used to test getAllWords
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getAllWords() throws Exception{
        CompletionStage<List<String>> getSentences = descriptionReadability.getAllSentences(projects);
        List<String> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<String>> getWords = descriptionReadability.getAllWords(sentences);
        List<String> words = getWords.toCompletableFuture().get();
        assertEquals(words.size(),43);
        assertEquals(words.get(3),"install");
        assertEquals(words.get(0), "I");
        assertEquals(words.get(23),"developer");
        assertEquals(words.get(29),"bidding");
        assertEquals(words.get(words.size()-1),"coding");
        assertTrue(words.size()!=0);
    }

    /**
     * used to test getAllSyllables
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getAllSyllables() throws Exception{
        CompletionStage<List<String>> getSentences = descriptionReadability.getAllSentences(projects);
        List<String> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<String>> getWords = descriptionReadability.getAllWords(sentences);
        List<String> words = getWords.toCompletableFuture().get();
        CompletionStage<Integer> getSyllables = descriptionReadability.getAllSyllables(words);
        int syllables = getSyllables.toCompletableFuture().get();
        assertEquals(syllables,60);
        assertNotEquals(syllables,65);
        assertTrue(syllables!=63);
        assertFalse(syllables==62);
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getAllSentencesFromEachProject() throws Exception{
        CompletionStage<List<List<String>>> getSentences = descriptionReadability.getAllSentencesFromEachProject(projects);
        List<List<String>> sentences = getSentences.toCompletableFuture().get();
        assertTrue(sentences.size()>0);
        assertTrue(sentences.get(0).size()!=0);
        assertTrue(sentences.get(1).size()!=0);
        assertEquals(sentences.get(0).size(),1);
        assertEquals(sentences.get(1).size(),5);
        assertEquals(sentences.get(0).get(0),"I need to install a landing page and minting app (React) to create an NFT minting website");
        assertEquals(sentences.get(1).get(1),"Need a developer urgently");
        assertEquals(sentences.get(1).get(sentences.get(1).size()-1),"2) How good you are in coding");
        assertNotEquals(sentences.get(1).get(4),"Answer following questions before bidding");
        assertEquals(sentences.get(1).get(2),"Answer following questions before bidding");

    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getAllWordsFromEachProject() throws Exception{
        CompletionStage<List<List<String>>> getSentences = descriptionReadability.getAllSentencesFromEachProject(projects);
        List<List<String>> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<List<String>>> getWords = descriptionReadability.getAllWordsFromEachProject(sentences);
        List<List<String>> words = getWords.toCompletableFuture().get();
        assertTrue(words.size()>0);
        assertTrue(words.get(0).size()!=0);
        assertTrue(words.get(1).size()!=0);
        assertEquals(words.get(0).size(),17);
        assertEquals(words.get(1).size(),26);
        assertEquals(words.get(0).get(3),"install");
        assertEquals(words.get(0).get(0), "I");
        assertEquals(words.get(1).get(6),"developer");
        assertEquals(words.get(1).get(12),"bidding");
        assertEquals(words.get(1).get(words.get(1).size()-1),"coding");
        assertEquals(words.get(0).get(words.get(0).size()-1),"website");
        assertNotEquals(words.get(1).get(4),"Answer");
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getAllSyllablesFromEachProject() throws Exception{
        CompletionStage<List<List<String>>> getSentences = descriptionReadability.getAllSentencesFromEachProject(projects);
        List<List<String>> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<List<String>>> getWords = descriptionReadability.getAllWordsFromEachProject(sentences);
        List<List<String>> words = getWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getSyllables = descriptionReadability.getAllSyllablesFromEachProject(words);
        List<Integer> syllables = getSyllables.toCompletableFuture().get();
        assertTrue(syllables.size()>0);
        assertEquals(syllables.get(0).intValue(),21);
        assertEquals(syllables.get(1).intValue(),39);
        assertEquals(syllables.get(0) + syllables.get(1),60);
        assertNotEquals(syllables.get(0).intValue(),24);
        assertNotEquals(syllables.get(1).intValue(),43);
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getSentences() throws Exception{
        String[] sentences = descriptionReadability.getSentences(projects.get(1).findPath("previewDescription").asText());
        assertTrue(sentences.length>0);
        assertEquals(sentences.length,5);
        assertEquals(sentences[1],"Need a developer urgently");
        assertEquals(sentences[sentences.length-1],"2) How good you are in coding");
        assertNotEquals(sentences[4],"Answer following questions before bidding");
        assertEquals(sentences[2],"Answer following questions before bidding");
        assertEquals(sentences[3],"1) Do you have experience in this field");
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getWords() throws Exception{
        String[] sentences = descriptionReadability.getSentences(projects.get(1).findPath("previewDescription").asText());
        String[] words = descriptionReadability.getWords(sentences[2]);
        assertTrue(words.length>0);
        assertTrue(words.length!=0);
        assertEquals(words.length,5);
        assertEquals(words[0],"Answer");
        assertEquals(words[2],"questions");
        assertEquals(words[words.length-1],"bidding");
        words = descriptionReadability.getWords(sentences[1]);
        assertEquals(words.length,4);
        assertEquals(words[2],"developer");
        assertEquals(words[0],"Need");
        assertEquals(words[words.length-1],"urgently");
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getSyllablesCount() throws Exception{
        String[] sentences = descriptionReadability.getSentences(projects.get(1).findPath("previewDescription").asText());
        String[] words = descriptionReadability.getWords(sentences[2]);
        int syllable = descriptionReadability.getSyllablesCount(words[1].toLowerCase());
        assertTrue(syllable!=0);
        assertEquals(syllable,3);
        syllable = descriptionReadability.getSyllablesCount(words[2].toLowerCase());
        assertEquals(syllable,2);
        syllable = descriptionReadability.getSyllablesCount(words[words.length-1].toLowerCase());
        assertEquals(syllable,2);
        words = descriptionReadability.getWords(sentences[1].toLowerCase());
        syllable = descriptionReadability.getSyllablesCount(words[0]);
        assertEquals(syllable,1);
        syllable = descriptionReadability.getSyllablesCount(words[2].toLowerCase());
        assertEquals(syllable,4);
        syllable = descriptionReadability.getSyllablesCount("a");
        assertEquals(syllable,1);
        syllable = descriptionReadability.getSyllablesCount("tool");
        assertEquals(syllable,1);
        syllable = descriptionReadability.getSyllablesCount("leave");
        assertEquals(syllable,1);
        syllable = descriptionReadability.getSyllablesCount("simple");
        assertEquals(syllable,2);
        syllable = descriptionReadability.getSyllablesCount("played");
        assertEquals(syllable,1);
        syllable = descriptionReadability.getSyllablesCount("loves");
        assertEquals(syllable,1);
        syllable = descriptionReadability.getSyllablesCount("is");
        assertEquals(syllable,1);
        syllable = descriptionReadability.getSyllablesCount("the");
        assertEquals(syllable,1);
        syllable = descriptionReadability.getSyllablesCount("ad");
        assertEquals(syllable,1);

    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void isVowel() throws Exception{
        String[] sentences = descriptionReadability.getSentences(projects.get(1).findPath("previewDescription").asText());
        String[] words = descriptionReadability.getWords(sentences[2]);
        String word = words[0];
        boolean vowel = descriptionReadability.isVowel(word.toLowerCase().charAt(0));
        assertTrue(vowel);
        vowel = descriptionReadability.isVowel(word.toLowerCase().charAt(1));
        assertFalse(vowel);
        vowel = descriptionReadability.isVowel(word.toLowerCase().charAt(word.length()-2));
        assertTrue(vowel);
        word = words[1];
        vowel = descriptionReadability.isVowel(word.toLowerCase().charAt(1));
        assertTrue(vowel);
        vowel = descriptionReadability.isVowel(word.toLowerCase().charAt(word.length()-2));
        assertFalse(vowel);
        assertTrue(descriptionReadability.isVowel('a'));
        assertTrue(descriptionReadability.isVowel('e'));
        assertTrue(descriptionReadability.isVowel('i'));
        assertTrue(descriptionReadability.isVowel('o'));
        assertTrue(descriptionReadability.isVowel('u'));
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void countNumberOfSentencesForEachProject() throws Exception{
        CompletionStage<List<List<String>>> getSentences = descriptionReadability.getAllSentencesFromEachProject(projects);
        List<List<String>> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfSentences = descriptionReadability.countNumberOfSentencesForEachProject(sentences);
        List<Integer> numberOfSentences = getNumberOfSentences.toCompletableFuture().get();
        assertTrue(numberOfSentences.size()>0);
        assertEquals(numberOfSentences.get(0).intValue(),1);
        assertEquals(numberOfSentences.get(1).intValue(),5);
        assertNotEquals(numberOfSentences.get(0).intValue(),2);
        assertNotEquals(numberOfSentences.get(1).intValue(),4);
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void countNumberOfWordsForEachProject() throws Exception{
        CompletionStage<List<List<String>>> getSentences = descriptionReadability.getAllSentencesFromEachProject(projects);
        List<List<String>> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfSentences = descriptionReadability.countNumberOfSentencesForEachProject(sentences);
        List<Integer> numberOfSentences = getNumberOfSentences.toCompletableFuture().get();
        CompletionStage<List<List<String>>> getWords = descriptionReadability.getAllWordsFromEachProject(sentences);
        List<List<String>> words = getWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfWords = descriptionReadability.countNumberOfWordsForEachProject(words);
        List<Integer> numberOfWords = getNumberOfWords.toCompletableFuture().get();
        assertTrue(numberOfWords.size()>0);
        assertEquals(numberOfWords.get(0).intValue(),17);
        assertEquals(numberOfWords.get(1).intValue(),26);
        assertNotEquals(numberOfWords.get(0).intValue(),19);
        assertNotEquals(numberOfWords.get(1).intValue(),24);
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    @After
    public void calculateFleschReadabilityIndexForEachProject() throws Exception {
        CompletionStage<List<List<String>>> getSentences = descriptionReadability.getAllSentencesFromEachProject(projects);
        List<List<String>> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfSentences = descriptionReadability.countNumberOfSentencesForEachProject(sentences);
        List<Integer> numberOfSentences = getNumberOfSentences.toCompletableFuture().get();
        CompletionStage<List<List<String>>> getWords = descriptionReadability.getAllWordsFromEachProject(sentences);
        List<List<String>> words = getWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfWords = descriptionReadability.countNumberOfWordsForEachProject(words);
        List<Integer> numberOfWords = getNumberOfWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getSyllables = descriptionReadability.getAllSyllablesFromEachProject(words);
        List<Integer> numberOfSyllables = getSyllables.toCompletableFuture().get();
        CompletionStage<List<Double>> getFleschReadabilityIndexForEachProject = descriptionReadability.calculateFleschReadabilityIndexForEachProject(numberOfSentences, numberOfWords, numberOfSyllables);
        List<Double> fleschReadabilityIndexForEachProject = getFleschReadabilityIndexForEachProject.toCompletableFuture().get();
        assertTrue(fleschReadabilityIndexForEachProject.size()>0);
        assertEquals(fleschReadabilityIndexForEachProject .get(0).doubleValue(),85.07411764705884,0);
        assertEquals(fleschReadabilityIndexForEachProject .get(1).doubleValue(),74.65700000000002,0);
        assertNotEquals(fleschReadabilityIndexForEachProject .get(0).doubleValue(),84.77411764705884,0);
        assertNotEquals(fleschReadabilityIndexForEachProject .get(1).doubleValue(),73.85700000000002,0);
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void calculateFleschKincaidGradeLevelForEachProject() throws Exception {
        CompletionStage<List<List<String>>> getSentences = descriptionReadability.getAllSentencesFromEachProject(projects);
        List<List<String>> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfSentences = descriptionReadability.countNumberOfSentencesForEachProject(sentences);
        List<Integer> numberOfSentences = getNumberOfSentences.toCompletableFuture().get();
        CompletionStage<List<List<String>>> getWords = descriptionReadability.getAllWordsFromEachProject(sentences);
        List<List<String>> words = getWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfWords = descriptionReadability.countNumberOfWordsForEachProject(words);
        List<Integer> numberOfWords = getNumberOfWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getSyllables = descriptionReadability.getAllSyllablesFromEachProject(words);
        List<Integer> numberOfSyllables = getSyllables.toCompletableFuture().get();
        CompletionStage<List<Double>> getFleschKincaidGradeLevelForEachProject = descriptionReadability.calculateFleschKincaidGradeLevelForEachProject(numberOfSentences, numberOfWords, numberOfSyllables);
        List<Double> fleschKincaidGradeLevelForEachProject = getFleschKincaidGradeLevelForEachProject.toCompletableFuture().get();
        assertTrue(fleschKincaidGradeLevelForEachProject.size()>0);
        assertEquals(fleschKincaidGradeLevelForEachProject.get(0).doubleValue(),5.616470588235295,0);
        assertEquals(fleschKincaidGradeLevelForEachProject.get(1).doubleValue(),4.138000000000002,0);
        assertNotEquals(fleschKincaidGradeLevelForEachProject.get(0).doubleValue(),6.77411764705884,0);
        assertNotEquals(fleschKincaidGradeLevelForEachProject.get(1).doubleValue(),5.85700000000002,0);
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    @After
    public void calculateFleschReadabilityIndex() throws Exception{
        CompletionStage<List<List<String>>> getSentences = descriptionReadability.getAllSentencesFromEachProject(projects);
        List<List<String>> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfSentences = descriptionReadability.countNumberOfSentencesForEachProject(sentences);
        List<Integer> numberOfSentences = getNumberOfSentences.toCompletableFuture().get();
        CompletionStage<List<List<String>>> getWords = descriptionReadability.getAllWordsFromEachProject(sentences);
        List<List<String>> words = getWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfWords = descriptionReadability.countNumberOfWordsForEachProject(words);
        List<Integer> numberOfWords = getNumberOfWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getSyllables = descriptionReadability.getAllSyllablesFromEachProject(words);
        List<Integer> numberOfSyllables = getSyllables.toCompletableFuture().get();
        double fleschReadabilityIndex = descriptionReadability.calculateFleschReadabilityIndex(numberOfSentences.get(1).doubleValue(), numberOfWords.get(1).doubleValue(), numberOfSyllables.get(1).doubleValue());
        assertTrue(fleschReadabilityIndex!=0);
        assertEquals(fleschReadabilityIndex,74.65700000000002,0);
        assertNotEquals(fleschReadabilityIndex,68.235700000000002,0);
        assertNotEquals(fleschReadabilityIndex,84.77411764705884,0);
        assertNotEquals(fleschReadabilityIndex,73.85700000000002,0);
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void calculateFleschKincaidGradeLevel() throws Exception{
        CompletionStage<List<List<String>>> getSentences = descriptionReadability.getAllSentencesFromEachProject(projects);
        List<List<String>> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfSentences = descriptionReadability.countNumberOfSentencesForEachProject(sentences);
        List<Integer> numberOfSentences = getNumberOfSentences.toCompletableFuture().get();
        CompletionStage<List<List<String>>> getWords = descriptionReadability.getAllWordsFromEachProject(sentences);
        List<List<String>> words = getWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfWords = descriptionReadability.countNumberOfWordsForEachProject(words);
        List<Integer> numberOfWords = getNumberOfWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getSyllables = descriptionReadability.getAllSyllablesFromEachProject(words);
        List<Integer> numberOfSyllables = getSyllables.toCompletableFuture().get();
        double fleschKincaidGradeLevel = descriptionReadability.calculateFleschKincaidGradeLevel(numberOfSentences.get(1).doubleValue(), numberOfWords.get(1).doubleValue(), numberOfSyllables.get(1).doubleValue());
        assertTrue(fleschKincaidGradeLevel!=0);
        assertEquals(fleschKincaidGradeLevel,4.138000000000002,0);
        assertNotEquals(fleschKincaidGradeLevel,2.158000000000002,0);
        assertNotEquals(fleschKincaidGradeLevel,3.180000000000022,0);
        assertNotEquals(fleschKincaidGradeLevel,7.85700000000002,0);
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void calculateEducationalLevelForEachProject() throws Exception{
        CompletionStage<List<List<String>>> getSentences = descriptionReadability.getAllSentencesFromEachProject(projects);
        List<List<String>> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfSentences = descriptionReadability.countNumberOfSentencesForEachProject(sentences);
        List<Integer> numberOfSentences = getNumberOfSentences.toCompletableFuture().get();
        CompletionStage<List<List<String>>> getWords = descriptionReadability.getAllWordsFromEachProject(sentences);
        List<List<String>> words = getWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfWords = descriptionReadability.countNumberOfWordsForEachProject(words);
        List<Integer> numberOfWords = getNumberOfWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getSyllables = descriptionReadability.getAllSyllablesFromEachProject(words);
        List<Integer> numberOfSyllables = getSyllables.toCompletableFuture().get();
        CompletionStage<List<Double>> getFleschReadabilityIndexForEachProject = descriptionReadability.calculateFleschReadabilityIndexForEachProject(numberOfSentences, numberOfWords, numberOfSyllables);
        List<Double> fleschReadabilityIndexForEachProject = getFleschReadabilityIndexForEachProject.toCompletableFuture().get();
        CompletionStage<List<String>> getEducationalLevelForEachProject = descriptionReadability.calculateEducationalLevelForEachProject(fleschReadabilityIndexForEachProject);
        List<String> educationalLevelForEachProject = getEducationalLevelForEachProject.toCompletableFuture().get();
        assertTrue(educationalLevelForEachProject.size()>0);
        assertEquals(educationalLevelForEachProject.get(0),"6th grade");
        assertEquals(educationalLevelForEachProject.get(1),"7th grade");
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void calculateEducationalLevel() throws Exception{
        CompletionStage<List<List<String>>> getSentences = descriptionReadability.getAllSentencesFromEachProject(projects);
        List<List<String>> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfSentences = descriptionReadability.countNumberOfSentencesForEachProject(sentences);
        List<Integer> numberOfSentences = getNumberOfSentences.toCompletableFuture().get();
        CompletionStage<List<List<String>>> getWords = descriptionReadability.getAllWordsFromEachProject(sentences);
        List<List<String>> words = getWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfWords = descriptionReadability.countNumberOfWordsForEachProject(words);
        List<Integer> numberOfWords = getNumberOfWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getSyllables = descriptionReadability.getAllSyllablesFromEachProject(words);
        List<Integer> numberOfSyllables = getSyllables.toCompletableFuture().get();
        CompletionStage<List<Double>> getFleschReadabilityIndexForEachProject = descriptionReadability.calculateFleschReadabilityIndexForEachProject(numberOfSentences, numberOfWords, numberOfSyllables);
        List<Double> fleschReadabilityIndexForEachProject = getFleschReadabilityIndexForEachProject.toCompletableFuture().get();
        String educationalLevel = descriptionReadability.calculateEducationalLevel(fleschReadabilityIndexForEachProject.get(1));
        assertEquals(educationalLevel,"7th grade");
        educationalLevel = descriptionReadability.calculateEducationalLevel(102.32);
        assertEquals(educationalLevel,"Early");
        educationalLevel = descriptionReadability.calculateEducationalLevel(92.54);
        assertEquals(educationalLevel,"5th grade");
        educationalLevel = descriptionReadability.calculateEducationalLevel(85.23);
        assertEquals(educationalLevel,"6th grade");
        educationalLevel = descriptionReadability.calculateEducationalLevel(72.54);
        assertEquals(educationalLevel,"7th grade");
        educationalLevel = descriptionReadability.calculateEducationalLevel(66.23);
        assertEquals(educationalLevel,"8th grade");
        educationalLevel = descriptionReadability.calculateEducationalLevel(64.34);
        assertEquals(educationalLevel,"9th grade");
        educationalLevel = descriptionReadability.calculateEducationalLevel(51.34);
        assertEquals(educationalLevel,"High School (10th to 12th grade)");
        educationalLevel = descriptionReadability.calculateEducationalLevel(30.67);
        assertEquals(educationalLevel,"Some College");
        educationalLevel = descriptionReadability.calculateEducationalLevel(29.45);
        assertEquals(educationalLevel,"College Graduate");
        educationalLevel = descriptionReadability.calculateEducationalLevel(5.6);
        assertEquals(educationalLevel,"Law School Graduate / Professional");
    }
    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void calculateAverage() throws Exception{
        CompletionStage<List<List<String>>> getSentences = descriptionReadability.getAllSentencesFromEachProject(projects);
        List<List<String>> sentences = getSentences.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfSentences = descriptionReadability.countNumberOfSentencesForEachProject(sentences);
        List<Integer> numberOfSentences = getNumberOfSentences.toCompletableFuture().get();
        CompletionStage<List<List<String>>> getWords = descriptionReadability.getAllWordsFromEachProject(sentences);
        List<List<String>> words = getWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getNumberOfWords = descriptionReadability.countNumberOfWordsForEachProject(words);
        List<Integer> numberOfWords = getNumberOfWords.toCompletableFuture().get();
        CompletionStage<List<Integer>> getSyllables = descriptionReadability.getAllSyllablesFromEachProject(words);
        List<Integer> numberOfSyllables = getSyllables.toCompletableFuture().get();
        CompletionStage<List<Double>> getFleschReadabilityIndexForEachProject = descriptionReadability.calculateFleschReadabilityIndexForEachProject(numberOfSentences, numberOfWords, numberOfSyllables);
        List<Double> fleschReadabilityIndexForEachProject = getFleschReadabilityIndexForEachProject.toCompletableFuture().get();
        CompletionStage<Double> getAverage = descriptionReadability.calculateAverage(fleschReadabilityIndexForEachProject);
        double average = getAverage.toCompletableFuture().get();
        assertTrue(average!=0);
        assertEquals(average,79.86555882352943,0);
        getAverage = descriptionReadability.calculateAverage(Arrays.asList(66.72,87.45,56.82,60.54));
        average = getAverage.toCompletableFuture().get();
        assertTrue(average!=0);
        assertEquals(average,67.8825,0);
    }

    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getDescriptionReadability() throws Exception{
        CompletionStage<Boolean> calculateReadability = descriptionReadability.calculateDescriptionReadabilityOfAllProjects(keyword, projects);
        boolean getReadability = calculateReadability.toCompletableFuture().get();
        CompletionStage<Boolean> calculateReadabilityForEachProject = descriptionReadability.calculateDescriptionReadabilityOfEachProject(keyword, projects);
        boolean getReadabilityForEachProject = calculateReadabilityForEachProject.toCompletableFuture().get();
        HashMap<String, DescriptionReadability> readability = descriptionReadability.getDescriptionReadability();
        DescriptionReadability descriptionReadability = readability.get(keyword);
        assertEquals(descriptionReadability.getOverallFleschReadabilityIndex(),81.51432170542637,0);
        assertEquals(descriptionReadability.getOverallFleschKincaidGradeLevel(),3.67011627906977,0);
        assertTrue(descriptionReadability.getOverallFleschReadabilityIndex()!=0.0);
        assertTrue(descriptionReadability.getOverallFleschKincaidGradeLevel()!=0.0);
        assertEquals(descriptionReadability.getAverageFleschReadabilityIndex(),79.86555882352943, 0);
        assertEquals(descriptionReadability.getAverageFleschKincaidGradeLevel(), 4.877235294117648,0);
        assertEquals(descriptionReadability.getFleschReadabilityIndexOfEachProject().size(), 2);
        assertEquals(descriptionReadability.getFleschKincaidGradeLevelOfEachProject().size(), 2);
        assertEquals(descriptionReadability.getFleschReadabilityIndexOfEachProject().get(0),85.07411764705884,0);
        assertEquals(descriptionReadability.getFleschReadabilityIndexOfEachProject().get(1),74.65700000000002,0);
        assertEquals(descriptionReadability.getFleschKincaidGradeLevelOfEachProject().get(0),5.616470588235295,0);
        assertEquals(descriptionReadability.getFleschKincaidGradeLevelOfEachProject().get(1),4.138000000000002,0);
    }

    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getReadability() throws Exception{
        CompletionStage<Boolean> calculateReadability = descriptionReadability.calculateDescriptionReadabilityOfAllProjects(keyword, projects);
        boolean getReadability = calculateReadability.toCompletableFuture().get();
        CompletionStage<Boolean> calculateReadabilityForEachProject = descriptionReadability.calculateDescriptionReadabilityOfEachProject(keyword, projects);
        boolean getReadabilityForEachProject = calculateReadabilityForEachProject.toCompletableFuture().get();
        CompletionStage<DescriptionReadability> getDescriptionReadability = descriptionReadability.getReadability(keyword);
        DescriptionReadability descriptionReadability = getDescriptionReadability.toCompletableFuture().get();
        assertEquals(descriptionReadability.getOverallFleschReadabilityIndex(),81.51432170542637,0);
        assertEquals(descriptionReadability.getOverallFleschKincaidGradeLevel(),3.67011627906977,0);
        assertTrue(descriptionReadability.getOverallFleschReadabilityIndex()!=0.0);
        assertTrue(descriptionReadability.getOverallFleschKincaidGradeLevel()!=0.0);
        assertEquals(descriptionReadability.getAverageFleschReadabilityIndex(),79.86555882352943, 0);
        assertEquals(descriptionReadability.getAverageFleschKincaidGradeLevel(), 4.877235294117648,0);
        assertEquals(descriptionReadability.getFleschReadabilityIndexOfEachProject().size(), 2);
        assertEquals(descriptionReadability.getFleschKincaidGradeLevelOfEachProject().size(), 2);
        assertEquals(descriptionReadability.getFleschReadabilityIndexOfEachProject().get(0),85.07411764705884,0);
        assertEquals(descriptionReadability.getFleschReadabilityIndexOfEachProject().get(1),74.65700000000002,0);
        assertEquals(descriptionReadability.getFleschKincaidGradeLevelOfEachProject().get(0),5.616470588235295,0);
        assertEquals(descriptionReadability.getFleschKincaidGradeLevelOfEachProject().get(1),4.138000000000002,0);
    }

    @Test
    public void averageFleschReadabilityIndex() throws Exception{
        descriptionReadability.setAverageFleschReadabilityIndex(averageFleschReadabilityIndex);
        assertTrue(descriptionReadability.getAverageFleschReadabilityIndex()>0);
        assertEquals(descriptionReadability.getAverageFleschReadabilityIndex(),averageFleschReadabilityIndex,0);
    }

    @Test
    public void averageFleschKincaidGradeLevel() throws Exception{
        descriptionReadability.setAverageFleschKincaidGradeLevel(averageFleschKincaidGradeLevel);
        assertTrue(descriptionReadability.getAverageFleschKincaidGradeLevel()>0);
        assertEquals(descriptionReadability.getAverageFleschKincaidGradeLevel(),averageFleschKincaidGradeLevel,0);
    }

    @Test
    public void overallFleschReadabilityIndex() throws Exception{
        descriptionReadability.setOverallFleschReadabilityIndex(overallFleschReadabilityIndex);
        assertTrue(descriptionReadability.getOverallFleschReadabilityIndex()>0);
        assertEquals(descriptionReadability.getOverallFleschReadabilityIndex(), overallFleschReadabilityIndex, 0);
    }

    @Test
    public void overallFleschKincaidGradeLevel() throws Exception{
        descriptionReadability.setOverallFleschKincaidGradeLevel(overallFleschKincaidGradeLevel);
        assertTrue(descriptionReadability.getOverallFleschKincaidGradeLevel()>0);
        assertEquals(descriptionReadability.getOverallFleschKincaidGradeLevel(), overallFleschKincaidGradeLevel, 0);
    }

    @Test
    public void fleschReadabilityIndexOfEachProject() throws Exception{
        descriptionReadability.setFleschReadabilityIndexOfEachProject(fleschReadabilityIndexOfEachProject);
        assertTrue(descriptionReadability.getFleschReadabilityIndexOfEachProject().size()!=0);
        assertEquals(descriptionReadability.getFleschReadabilityIndexOfEachProject(),fleschReadabilityIndexOfEachProject);
        CompletionStage<Double> getFleshReadabilityIndex = descriptionReadability.getFleschReadabilityIndexOfEachProject(0);
        double fleshReadabilityIndex = getFleshReadabilityIndex.toCompletableFuture().get();
        assertTrue(fleshReadabilityIndex!=0);
        assertEquals(Double.valueOf(fleshReadabilityIndex),fleschReadabilityIndexOfEachProject.get(0));
        assertNotEquals(Double.valueOf(fleshReadabilityIndex),fleschReadabilityIndexOfEachProject.get(1));
        getFleshReadabilityIndex = descriptionReadability.getFleschReadabilityIndexOfEachProject(1);
        fleshReadabilityIndex = getFleshReadabilityIndex.toCompletableFuture().get();
        assertTrue(fleshReadabilityIndex!=0);
        assertEquals(Double.valueOf(fleshReadabilityIndex),fleschReadabilityIndexOfEachProject.get(1));
        assertNotEquals(Double.valueOf(fleshReadabilityIndex),fleschReadabilityIndexOfEachProject.get(2));

    }

    @Test
    public void fleschKincaidGradeLevelOfEachProject() throws Exception{
        descriptionReadability.setFleschKincaidGradeLevelOfEachProject(fleschKincaidGradeLevelOfEachProject);
        assertTrue(descriptionReadability.getFleschKincaidGradeLevelOfEachProject().size()!=0);
        assertEquals(descriptionReadability.getFleschKincaidGradeLevelOfEachProject(), fleschKincaidGradeLevelOfEachProject);
        CompletionStage<Double> getFleschKincaidGradeLevel = descriptionReadability.getFleschKincaidGradeLevelOfEachProject(0);
        double fleschKincaidGradeLevel = getFleschKincaidGradeLevel.toCompletableFuture().get();
        assertTrue(fleschKincaidGradeLevel!=0);
        assertEquals(Double.valueOf(fleschKincaidGradeLevel),fleschKincaidGradeLevelOfEachProject.get(0));
        assertNotEquals(Double.valueOf(fleschKincaidGradeLevel),fleschKincaidGradeLevelOfEachProject.get(1));
        getFleschKincaidGradeLevel = descriptionReadability.getFleschKincaidGradeLevelOfEachProject(1);
        fleschKincaidGradeLevel = getFleschKincaidGradeLevel.toCompletableFuture().get();
        assertTrue(fleschKincaidGradeLevel!=0);
        assertEquals(Double.valueOf(fleschKincaidGradeLevel),fleschKincaidGradeLevelOfEachProject.get(1));
        assertNotEquals(Double.valueOf(fleschKincaidGradeLevel),fleschKincaidGradeLevelOfEachProject.get(2));
    }

    @Test
    public void educationLevelOfEachProject() throws Exception{
        descriptionReadability.setEducationLevelOfEachProject(educationLevelOfEachProject);
        assertTrue(descriptionReadability.getEducationLevelOfEachProject().size()!=0);
        assertEquals(descriptionReadability.getEducationLevelOfEachProject(), educationLevelOfEachProject);
        CompletionStage<String> getEducationLevelOfEachProject = descriptionReadability.getEducationLevelOfEachProject(0);
        String educationLevel = getEducationLevelOfEachProject.toCompletableFuture().get();
        assertTrue(educationLevel != null);
        assertEquals(educationLevel,educationLevelOfEachProject.get(0));
        assertNotEquals(educationLevel,educationLevelOfEachProject.get(1));
        getEducationLevelOfEachProject = descriptionReadability.getEducationLevelOfEachProject(1);
        educationLevel = getEducationLevelOfEachProject.toCompletableFuture().get();
        assertTrue(educationLevel!=null);
        assertEquals(educationLevel,educationLevelOfEachProject.get(1));
        assertNotEquals(educationLevel,educationLevelOfEachProject.get(2));
    }

    @Test
    public void numberOfSentencesOfEachProject() throws Exception{
        descriptionReadability.setNumberOfSentencesOfEachProject(numberOfSentencesOfEachProject);
        assertTrue(descriptionReadability.getNumberOfSentencesOfEachProject().size()!=0);
        assertEquals(descriptionReadability.getNumberOfSentencesOfEachProject(), numberOfSentencesOfEachProject);
    }

    @Test
    public void numberOfWordsOfEachProject() throws Exception{
        descriptionReadability.setNumberOfWordsOfEachProject(numberOfWordsOfEachProject);
        assertTrue(descriptionReadability.getNumberOfWordsOfEachProject().size()!=0);
        assertEquals(descriptionReadability.getNumberOfWordsOfEachProject(), numberOfWordsOfEachProject);
    }

    @Test
    public void numberOfSyllablesOfEachProject() throws Exception{
        descriptionReadability.setNumberOfSyllablesOfEachProject(numberOfSyllablesOfEachProject);
        assertTrue(descriptionReadability.getNumberOfSyllablesOfEachProject().size()!=0);
        assertEquals(descriptionReadability.getNumberOfSyllablesOfEachProject(), numberOfSyllablesOfEachProject);
    }

    @Test
    public void previewDescription() throws Exception{
        descriptionReadability.descriptionReadability = new DescriptionReadability();
        descriptionReadability.setPreviewDescription(projects.get(1).findPath("previewDescription").asText());
        assertTrue(!descriptionReadability.getPreviewDescription().isEmpty());
        assertEquals(descriptionReadability.getPreviewDescription(), projects.get(1).findPath("previewDescription").asText());
    }







}
