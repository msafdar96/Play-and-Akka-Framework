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

    const data = {
        keyword:window.location.pathname.split("/")[3].replaceAll("-"," "),
        global: true
    }
    const message = JSON.stringify(data);
    statsSocket.send(message);
    statsSocket.onmessage = (e) => {
        displayStats(JSON.parse(e.data).data)
    }
}

const displayStats = (stats) => {
    document.querySelector("#tWords").append(stats.twords);
    document.querySelector("#uWords").append(stats.uwords);
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