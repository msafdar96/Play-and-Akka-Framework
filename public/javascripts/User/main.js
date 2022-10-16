const userSocket = new WebSocket("ws://localhost:9000/freelancelot/user");

userSocket.onopen = function (e) {
    console.log("User Socket Connected!");
    getDetails();
};

userSocket.onclose = function (event) {
    if (event.wasClean) {
        console.log("Connection closed");
        console.log(`code: ${event.code} reason: ${event.reason}`)
    } else {
        console.log('Connection Died');
    }
};

userSocket.onerror = function (error) {
    console.log(`Error:  +  ${error.message}`);
};

const getDetails = () => {

    const data = {
        ownerID: window.location.pathname.split("/")[3],
    }
    const message = JSON.stringify(data);
    userSocket.send(message);
    userSocket.onmessage = (e) => {
        const data = JSON.parse(e.data).data;
        if(!Array.isArray(data)){
            displayDetails(data);
        }else{
            displayProjects(data);
        }
    }
}

const displayDetails = (details) => {
    document.querySelector("#name").append(details.name);
    document.querySelector("#role").append(details.role);
    document.querySelector("#rate").append(details.hourly_rate);
    document.querySelector("#dName").append(details.display_name);
    document.querySelector("#rDate").append(details.registration_date);
    document.querySelector("#cRole").append(details.role);
    document.querySelector("#country").append(details.country);
    document.querySelector("#currency").append(details.currency);
    document.querySelector("#email").append(details.email);
    document.querySelector("#lAccount").append(details.limited_account);

}

const displayProjects = (projects)=>{
    let renderData = "";
    for (let [index, project] of projects.entries()) {
        renderData += "<tr>" +
            "                                <td>" + parseInt(index + 1) + "</td>" +
            "                                <td>" +  project.ownerID + "</td>" +
            "                                <td>" + project.timeSubmitted + "</td>" +
            "                                <td>" + project.title + "</td>" +
            "                                <td>" + project.type + "</td>" +
            "                                <td>";

        for (let skill of project.skills) {
            renderData += skill;
        }

    }
    document.querySelector("#projects").innerHTML = renderData;

}