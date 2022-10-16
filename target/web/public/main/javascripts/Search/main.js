const form = document.querySelector("#searchForm");

form.addEventListener("submit", (e) => {
    e.preventDefault();
    search(e.target.keyword.value);
})

const searchSocket = new WebSocket("ws://localhost:9000/freelancelot/search");
const descriptionReadabilitySocket = new WebSocket("ws://localhost:9000/freelancelot/descriptionReadability");
searchSocket.onopen = function (e) {
    console.log("Search Socket Connected!");
};
descriptionReadabilitySocket.onopen = function (e) {
    console.log("Description Readability Socket Connected!");
};

searchSocket.onclose = function (event) {
    if (event.wasClean) {
        console.log("Connection closed");
        console.log(`code: ${event.code} reason: ${event.reason}`)
    } else {
        console.log('Connection Died');
    }
};

searchSocket.onerror = function (error) {
    console.log(`Error:  +  ${error.message}`);
};

descriptionReadabilitySocket.onclose = function (event) {
    if (event.wasClean) {
        console.log("Connection closed");
        console.log(`code: ${event.code} reason: ${event.reason}`)
    } else {
        console.log('Connection Died');
    }
};

descriptionReadabilitySocket.onerror = function (error) {
    console.log(`Error:  +  ${error.message}`);
};

const search = (keyword) => {
    const searchedKeyword = {
        keyword,
    };
    const message = JSON.stringify(searchedKeyword);
    searchSocket.send(message);
    searchSocket.onmessage = (e) => {
        const searchResult = JSON.parse(e.data);
       if(searchResult.allProjects) {
            getDescriptionReadability(searchResult);
       }else {
           if (searchResult.isNew) {
               displayNewSearchResults(searchResult);
           } else {
               displayUpdatedSearchResults(searchResult);
           }
       }

    }

}

const getDescriptionReadability = (projects) => {
    projects.individual = false;
    let message = JSON.stringify(projects);
    descriptionReadabilitySocket.send(message);
    descriptionReadabilitySocket.onmessage = (e) => {
        displayDescriptionReadability(JSON.parse(e.data));
    }
}

const displayNewSearchResults = (searchResults) => {
    const keyword = searchResults.keyword.toString().replaceAll(" ", "-");
    let renderData = "<div class=\"card text-center card-margin\">" +
        "                     <div class=\"card-header\">" +
        "                     Search Results" +
        "                     </div>" +
        "                     <div class=\"card-body\">" +
        "                     <h5 class=\"card-title\">Search Results for " + searchResults.keyword + "</h5> " +
        "                       <p class=\"card-text\">" +
        "                     <div class=\"alert alert-success\" role=\"alert\">" +
        "                     <div id=\"descriptionReadability-" + keyword + "\"></div>                                                  " +
        "                     <hr>" +
        "                     </div>" +
        "                     </p>" +
        "                     <a href=" + `http://localhost:9000/freelancelot/search/${keyword}/globalstats ` + " class=\"btn btn-primary\">Global Stats</a>" +
        "                     </div>" +
        "                     </div>" +
        "                   </div>" +
        "                   </div>";


    renderData += `<table class="table table-hover" id=table-${keyword}>`;
    renderData += " <thead class=\"dark-head\">" +
        "                            <th>#</th>" +
        "                            <th>Owner ID</th>" +
        "                            <th>Time Submitted</th>" +
        "                            <th>Title</th>" +
        "                            <th>Type</th>" +
        "                            <th>Skills</th>" +
        "                            <th>Readability</th>" +
        "                            <th>Words Stats</th>" +
        "                        </thead>" +
                                `<tbody id=tbody-${keyword}>`;
    for (let [index, project] of searchResults.data.entries()) {
        renderData += "<tr>" +
            "                                <td>" + parseInt(index + 1) + "</td>" +
            "                                <td><a href= " + `http://localhost:9000/freelancelot/userdetails/${project.ownerID}` + ">" + project.ownerID + "</a></td>" +
            "                                <td>" + project.timeSubmitted + "</td>" +
            "                                <td>" + project.title + "</td>" +
            "                                <td>" + project.type + "</td>" +
            "                                <td>";

        for (let skill of project.skills) {
            renderData += "<a href= " + `http://localhost:9000/freelancelot/projects/${skill.replaceAll(" ","-")}` + ">" + skill + "</a>, ";
        }
        renderData += "</td>" +
            "                                <td>" +
            "                                    <a href=" + `http://localhost:9000/freelancelot/search/${keyword}/${index}/readability?preview_description=${encodeURIComponent(project.previewDescription)}` + ">Readability</a>" +
            "                                </td>" +
            "                                <td>" +
            "                                    <a href=" + `http://localhost:9000/freelancelot/search/${keyword}/${index}/projectstats?preview_description=${encodeURIComponent(project.previewDescription)}` + ">Stats</a>" +
            "                                </td>" +
            "                            </tr>";
    }
    renderData += "</tbody>" +
        "</table>";
    document.querySelector("#searchResults").insertAdjacentHTML("afterbegin", renderData);
}

const displayDescriptionReadability = (descriptionReadability) => {
    let renderData = "<h4 class=\"alert-heading\">Description Readability Statistics</h4>" +
        "             <p><b>Flesch Readability Index: </b>" + parseFloat(descriptionReadability.data.averageFleschReadabilityIndex).toFixed(2) + " (Average) </p>" +
        "             <p><b>Flesch Kincaid Grade Level: </b>" + parseFloat(descriptionReadability.data.averageFleschKincaidGradeLevel).toFixed(2) + " (Average)</p>" +
        "             <p><b>Overall Flesch Readability Index: </b>" + parseFloat(descriptionReadability.data.overallFleschReadabilityIndex).toFixed(2) + " (Whole Document)</p>" +
        "             <p><b>Overall Flesch Kincaid Grade Level: </b>" + parseFloat(descriptionReadability.data.overallFleschKincaidGradeLevel).toFixed(2) + " (Whole Document)</p>"
    document.querySelector("#descriptionReadability-" + descriptionReadability.keyword.toString().replaceAll(" ", "-"))
        .innerHTML = renderData;

}

const displayUpdatedSearchResults = (searchResults) => {
    console.log("Updating...");
    let renderData ="";
    for (let [index, project] of searchResults.data.entries()) {
        renderData += "<tr>" +
            "                                <td>" + parseInt(index + 1) + "</td>" +
            "                                <td><a href= \"@routes.SearchController.userDetails(project.getOwnerID())\">" + project.ownerID + "</a></td>" +
            "                                <td>" + project.timeSubmitted + "</td>" +
            "                                <td>" + project.title + "</td>" +
            "                                <td>" + project.type + "</td>" +
            "                                <td>";

        for (let skill of project.skills) {
            renderData += "<a href= \"@routes.SearchController.skillProjects(skill)\">" + skill + "</a>, ";
        }
        renderData += "</td>" +
            "                                <td>" +
            "                                    <a href=" + `http://localhost:9000/freelancelot/search/${keyword}/${index}/readability?preview_description=${encodeURIComponent(project.previewDescription)}` + ">Readability</a>" +
            "                                </td>" +
            "                                <td>" +
            "                                    <a href=" + `http://localhost:9000/freelancelot/search/${keyword}/${index}/projectstats?preview_description=${encodeURIComponent(project.previewDescription)}` + ">Stats</a>" +
            "                                </td>" +
            "                            </tr>";

    }

    document.querySelector(`#tbody-${searchResults.keyword.replaceAll(" ","-")}`).innerHTML = renderData;

}



