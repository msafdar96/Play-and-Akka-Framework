package models;

/**
 * This Model Search class contains methods for the group part and individual parts
 */


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

public class Search {

    public String keyword;
    private LinkedHashMap<String, List<Project>> searchedItems = new LinkedHashMap<String, List<Project>>();

    /**
     * default constructor
     */
    public Search(){

    }

    /**
     * constructor to set the key word
     * @param keyword
     */
    public Search(String keyword){
        this.keyword = keyword;
    }

    /**
     * keyword setter method
     * @param keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * keyword getter method
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }




    /**
     * @author Group
     * This method is used to save the all the project details
     * for every keywrd searched on the main page asynchronously usign
     * <code>CompletableFuture supplyAsync</code>
     * @param keyword
     * @param projects
     * @return the <code>CompletionStage<Boolean></code> on completion
     */
    public CompletionStage<List<Project>> saveProjects(String keyword, JsonNode projects){
        CompletionStage<List <Project>> result = CompletableFuture.supplyAsync(()-> {
                List <Project> projectsCollection = StreamSupport
                        .stream(projects.spliterator(), true)
                        .parallel()
                        .map(e -> {
                            String id = e.get("id").toString();
                            String ownerID = e.get("owner_id").toString();
                            Date timeSubmitted = new Date(e.get("time_submitted").asLong() * 1000);
                           // System.out.println(DateFormat.getDateInstance(DateFormat.MEDIUM).format(timeSubmitted));
                            String title = e.get("title").textValue();
                            String type = e.get("type").textValue();
                            List<String> skills = StreamSupport
                                    .stream(e.get("jobs").spliterator(), true)
                                    .parallel()
                                    .map(el -> el.get("name").textValue())
                                    .collect(Collectors.toList());
                            String previewDescription = e.get("preview_description").textValue();

                            return new Project(id, ownerID, previewDescription, timeSubmitted, title, type, skills);
                        })
                        .limit(10)
                        .collect(Collectors.toList());
        searchedItems.put(keyword.toLowerCase(), projectsCollection);
        return projectsCollection;
        });
        return result;
    }

    /**
     * @author Mariam Safdar
     * This method is used to fetch all the project details
     * for s specific skill asynchronously using
     * <code>CompletableFuture supplyAsync</code>
     * @param projects
     * @return the <code>CompletionStage<Boolean></code> on completion
     */
    public CompletionStage<List<Project>> skillProjects(JsonNode projects){
        CompletionStage<List<Project>> result = CompletableFuture.supplyAsync(()-> {
            List <Project> projectsCollection = StreamSupport
                    .stream(projects.spliterator(), true)
                    .parallel()
                    .map(e -> {
                        String id = e.get("id").toString();
                        String ownerID = e.get("owner_id").toString();
                        Date timeSubmitted = new Date(e.get("time_submitted").asLong() * 1000);
                        String title = e.get("title").textValue();
                        String type = e.get("type").textValue();
                        List<String> skills = StreamSupport
                                .stream(e.get("jobs").spliterator(), true)
                                .parallel()
                                .map(el -> el.get("name").textValue())
                                .collect(Collectors.toList());
                        String previewDescription = e.get("preview_description").textValue();
                        return new Project(id, ownerID,previewDescription, timeSubmitted, title, type, skills);
                    })
                    .limit(10)
                    .collect(Collectors.toList());
            return projectsCollection;
        });
        return result;
    }


    /**
     * getter of project object
     * @author Sahran Khuwaja
     * @param keyword
     * @param index
     * @return project object
     */
    public CompletionStage<Project> getProject(String keyword, int index){
        CompletionStage<Project> result = CompletableFuture.supplyAsync(()->{
            List<Project> projects = searchedItems.get(keyword);
            return projects.get(index);
        });
        return result;
    }

    /**
     * getter of SearchedItems
     * @author Sahran Khuwaja
     * @return map of list of projects of each keyword
     */
    public LinkedHashMap<String, List<Project>> getSearchedItems(){

        return  searchedItems;
    }



}
