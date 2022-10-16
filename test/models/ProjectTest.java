package models;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for model class Project
 * @author group
 */

public class ProjectTest {
    String ownerID;
    String id;
    String previewDescription;
    String title;
    String type;
    Date timeSubmitted;
    List<String> skills;
    Integer twords;
    Integer uwords;
    Integer chare;
    Integer sen;
    float charpersen;
    float charperword;
    float wordpersen;
    Map<String, Integer> wordstatus;

    Project project;
    Project project1;
    Project project2;
    Project project3;

    @Before
    public void setup(){
        this.setupMockData();
    }

    public void setupMockData(){
        skills = Arrays.asList("C++","Java");
        id = "123";
        twords  = 12;
        uwords  = 33;
        chare = 55;
        sen = 66;
        charpersen =99;
        charperword =44;
        wordpersen= 665;
        wordstatus = new HashMap<String, Integer>();
        wordstatus.put("Java",14);
        ownerID = "23232323";
        previewDescription = "need to login";
        title = "Java Fx Basic Task";
        type = "fixed";
        timeSubmitted = new Date();
        project = new Project();
        project = new Project(id, ownerID, previewDescription, timeSubmitted, title, type, skills, twords, uwords,
                chare, sen, charperword, wordpersen, charpersen, wordstatus);
        project1 = new Project(id, ownerID, previewDescription,timeSubmitted, title, type, skills);
        project2 = new Project(id, ownerID,timeSubmitted, title, type, skills);
//        project3 = new Project(id, ownerID, previewDescription, timeSubmitted, title, type, skills);
    }



    @Test
    public void ownerID(){
        project.setOwnerID(ownerID);
        assertTrue(project.getOwnerID()!=null);
        assertEquals(project.getOwnerID(),ownerID);
    }

    @Test
    public void previewDescription(){
        project.setPreviewDescription(previewDescription);
        assertTrue(project.getPreviewDescription()!=null);
        assertEquals(project.getPreviewDescription(),previewDescription);
    }

    @Test
    public void timeSubmitted(){
        project.setTimeSubmitted(timeSubmitted);
        assertTrue(project.getTimeSubmitted()!=null);
        assertEquals(project.getTimeSubmitted(),timeSubmitted);
    }

    @Test
    public void title(){
        project.setTitle(title);
        assertTrue(project.getTitle()!=null);
        assertEquals(project.getTitle(),title);
    }

    @Test
    public void type(){
        project.setType(type);
        assertTrue(project.getType()!=null);
        assertEquals(project.getType(),type);
    }

    @Test
    public void skills(){
        project.setSkills(skills);
        assertTrue(project.getSkills()!=null);
        assertEquals(project.getSkills(),skills);
    }

    @Test
    public void tWords(){
        project.setTwords(twords);
        assertTrue(project.getTwords()>0);
        assertEquals(project.getTwords(),twords);
    }

    @Test
    public void uWords(){
        project.setUwords(uwords);
        assertTrue(project.getUwords()>0);
        assertEquals(project.getUwords(),uwords);
    }

    @Test
    public void characters(){
        project.setCharecters(chare);
        assertTrue(project.getCharecters()>0);
        assertEquals(project.getCharecters(),chare);
    }

    @Test
    public void sentences(){
        project.setSentences(sen);
        assertTrue(project.getSentences()>0);
        assertEquals(project.getSentences(),sen);
    }

    @Test
    public void charsPerWord(){
        project.setCharsPerWord(charperword);
        assertTrue(project.getCharsPerWord()>0);
        assertEquals(project.getCharsPerWord(),charperword,0);
    }

    @Test
    public void wordsPerSent(){
        project.setWordsPerSent(wordpersen);
        assertTrue(project.getWordsPerSent()>0);
        assertEquals(project.getWordsPerSent(),wordpersen,0);
    }

    @Test
    public void charsPerSent(){
        project.setCharsPerSent(charpersen);
        assertTrue(project.getCharsPerSent()>0);
        assertEquals(project.getCharsPerSent(),charpersen,0);
    }

    @Test
    public void wordStats(){
        project.setWordStats(wordstatus);
        assertTrue(project.getWordStats().size()>0);
        assertEquals(project.getWordStats(),wordstatus);
    }

    @Test
    public void id(){
        project.setId(id);
        assertTrue(project.getId()!=null);
        assertEquals(project.getId(),id);

    }

}
