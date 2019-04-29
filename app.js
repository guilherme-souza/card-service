var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#transactions").html("");
}

function connect() {
    var socket = new SockJS('http://localhost:8080/withdraw-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/result', function (greeting) {
            console.log(greeting);
            showTransactions(JSON.parse(greeting.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/withdraw", {}, JSON.stringify(
    {
        'action': $("#action").val(),
        'cardNumber': $("#card").val(),
        'amount': $("#value").val()
    }));
}

function showTransactions(result) {
    $("#transactions").append("<tr><td>" + result.action + "</td><td>" + result.code + "</td><td>" + result.authorizationCode + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});