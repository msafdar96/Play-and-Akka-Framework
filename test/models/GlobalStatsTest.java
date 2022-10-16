package models;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.*;

/**
 * Test cases for model class GlobalStats
 * @author Mohammad Misbah Uddin Shareef
 */
public class GlobalStatsTest {
    Integer twords;
    Integer uwords;
    Integer chare;
    Integer sen;
    float charpersen;
    float charperword;
    float wordpersen;
    Map<String, Integer> wordstatus = new HashMap<String, Integer>();
    GlobalStats stats;
    String keyword;
    private JsonNode skills = null;
    private JsonNode projects = null;
    private JsonNode project1 = null;
    private JsonNode project2 = null;

    @Before
    public void setup(){
        this.setupMockData();
        stats = new GlobalStats();
        stats =  new GlobalStats(twords, uwords,chare, sen, charperword, wordpersen, charpersen,   wordstatus);
    }

    public void setupMockData(){
        keyword = "react";
        wordstatus.put("Java",14);
        twords  = 12;
        uwords  = 33;
        chare = 55;
        sen =66;
        charpersen =99;
        charperword =44;
        wordpersen= 665;
        skills = Json.newArray()
                .add(Json.newObject().put("name","React.js"))
                .add(Json.newObject().put("name","Node.js"))
                .add(Json.newObject().put("name","JavaScript"));
        project1 = Json.newObject()
                .put("owner_id", 2487292)
                .put("time_submitted", 1647732665)
                .put("title", "NFT App Install for NFT minting website")
                .put("type", "Fixed")
                .put("preview_description","I need to install a landing page and minting app (React) to create an NFT minting website.")
                .set("jobs",skills);
        project2 = Json.newObject()
                .put("owner_id", 2487293)
                .put("time_submitted", 1647732665)
                .put("title", "This is a test")
                .put("type", "Fixed")
                .put("preview_description","This is a test. Need a developer urgently! " +
                        "Answer following questions before bidding: 1) Do you have experience in this field?" +
                        "2) How good you are in coding?")
                .set("jobs",skills);

        projects = Json.newArray()
                .add(project1)
                .add(project2);
    }

    @Test
    public void tWords(){
        stats.setTwords(twords);
        assertTrue(stats.getTwords()>0);
        assertEquals(twords, stats.getTwords());
    }

    @Test
    public void uWords(){
        stats.setUwords(uwords);
        assertEquals(stats.getUwords(),uwords);
        assertTrue(stats.getUwords()>0);
    }

    @Test
    public void characters(){
        stats.setCharecters(chare);
        assertEquals(stats.getCharecters(),chare);
        assertTrue(stats.getUwords()>0);
    }

    @Test
    public void sentences(){
        stats.setSentences(sen);
        assertEquals(stats.getSentences(),sen);
        assertTrue(stats.getSentences()>0);
    }

    @Test
    public void charsPerWord(){
        stats.setCharsPerWord(charperword);
        assertEquals(stats.getCharsPerWord(),charperword,0);
        assertTrue(stats.getCharsPerWord()>0);

    }

    @Test
    public void wordsPerSent(){
        stats.setWordsPerSent(wordpersen);
        assertEquals(stats.getWordsPerSent(),wordpersen,0);
        assertTrue(stats.getWordsPerSent()>0);
    }

    @Test
    public void charsPerSent(){
        stats.setCharsPerSent(charpersen);
        assertEquals(stats.getCharsPerSent(), charpersen, 0);
        assertTrue(stats.getCharsPerSent()>0);
    }

    @Test
    public void wordStats(){
        stats.setWordStats(wordstatus);
        assertEquals(stats.getWordStats(), wordstatus);
    }

    @Test
    public void getWStats() throws Exception{
        String text = projects.get(1).findPath("preview_description").asText();
        Map<String, Integer> getWordStats = stats.getWStats(text);
        assertTrue(getWordStats.size()>0);
        assertEquals(getWordStats.get("you").intValue(),2);
        assertEquals(getWordStats.get("developer").intValue(),1);
        assertEquals(getWordStats.get("in").intValue(),2);
        assertNotEquals(getWordStats.get("questions").intValue(),3);
    }

    @Test
    public void globalWStats() throws Exception{
        CompletionStage<Boolean> calculateGlobalWordStats = stats.globalWStats(keyword,projects);
        boolean result = calculateGlobalWordStats.toCompletableFuture().get();
        assertTrue(result);
        assertTrue(stats.getGStats().size()>0);
        assertTrue(stats.getGStats().containsKey(keyword));
        assertTrue(!stats.getGStats().keySet().isEmpty());
        assertEquals(stats.getGStats().keySet().toArray()[0],keyword);
        GlobalStats g = stats.getGStats().get(keyword);
        assertEquals(g.getUwords().intValue(),36);
        assertEquals(g.getWordStats().get("you").intValue(),2);
        assertEquals(g.getSentences().intValue(),6);
        assertEquals(g.getCharecters().intValue(),192);
        assertEquals(g.getCharsPerSent(),32.0,0);
        assertEquals(g.getCharsPerWord(),4.266666889190674,0);
        assertEquals(g.getTwords().intValue(),45);
        assertEquals(g.getWordsPerSent(),7.5,0);
    }





}
