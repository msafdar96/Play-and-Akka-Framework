const statsSocket = new WebSocket("ws://localhost:9000/freelancelot/stats");

statsSocket.onopen = function (e) {
    console.log("Stats Socket Connected!");
    getStats();
};

statsSocket.onclose = function (event) {
    if (event.wasClean) {
        console.log("Connection closed");
        console.log(`code: ${event.code} reason: ${event.reason}`)
    } else {
        console.log('Connection Died');
    }
};

statsSocket.onerror = function (error) {
    console.log(`Error:  +  ${error.message}`);
};

const getStats = () => {
    const params = new URLSearchParams(window.location.search);
    const project = {
        previewDescription: params.get("preview_description")
    }
    const data = {
        data: [project],
        global: false
    }
    const message = JSON.stringify(data);
    statsSocket.send(message);
    statsSocket.onmessage = (e) => {
        displayStats(project.previewDescription, JSON.parse(e.data).data)
    }
}

const displayStats = (previewDescription, stats) => {
    document.querySelector("#previewDescription").innerHTML = previewDescription;
    document.querySelector("#tWords").append(stats.twords);
    document.querySelector("#uWords").append(stats.uwords);
    document.querySelector("#characters").append(stats.characters);
    document.querySelector("#sentences").append(stats.sentences);
    document.querySelector("#wordsPerSent").append(stats.wordsPerSent);
    document.querySelector("#charsPerWord").append(stats.charsPerWord);
    document.querySelector("#charsPerSent").append(stats.charsPerSent);

    let renderData = "";
    for (let word in stats.wordStats) {
        renderData += "<tr>" +
            "            <td>" + word + "</td>" +
            "            <td>" + stats.wordStats[word] + "</td>" +
            "        </tr>"
    }
    document.querySelector("#stats").innerHTML = renderData;

}


