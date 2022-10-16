const descriptionReadabilitySocket = new WebSocket("ws://localhost:9000/freelancelot/descriptionReadability");

descriptionReadabilitySocket.onopen = function (e) {
    console.log("Description Readability Socket Connected!");
    getDescriptionReadability();
    //Spinner.hide();
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

const getDescriptionReadability = ()=>{
    const params = new URLSearchParams(window.location.search);
    const project = {
        previewDescription:params.get("preview_description")
    }
    const data = {
        keyword:window.location.pathname.split("/")[3].replaceAll("-"," "),
        data:[project],
        individual: true,
        isNew: true
    }
    const message = JSON.stringify(data);
    descriptionReadabilitySocket.send(message);
    descriptionReadabilitySocket.onmessage = (e) => {
        displayDescriptionReadability(data.keyword, project.previewDescription, JSON.parse(e.data).data)
    }
}

const displayDescriptionReadability = (keyword, previewDescription, descriptionReadability)=>{
    document.querySelector("#previewDescription").innerHTML = previewDescription;
    document.querySelector("#fleschReadabilityIndex").innerHTML = descriptionReadability.averageFleschReadabilityIndex;
    document.querySelector("#fleschKincaidGradeLevel").innerHTML = descriptionReadability.averageFleschKincaidGradeLevel;
    document.querySelector("#educationalLevel").innerHTML = descriptionReadability.descriptionReadability[keyword].educationLevelOfEachProject[0];

}


