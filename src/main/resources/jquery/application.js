$(function () {
    "use strict";

    var header = $('#header');
    var content = $('#content');
    var input = $('#input');
    var status = $('#status');
    var myName = false;
    var author = null;
    var logged = false;
    var socket = $.atmosphere;
    var subSocket;
    var transport = 'websocket';

    var request = { url: document.location.protocol + "//" + document.location.host + '/northpole',
        contentType: "application/json",
        logLevel: 'debug',
        transport: transport,
        trackMessageLength: true,
        enableProtocol: true,
        fallbackTransport: 'long-polling'
    };

    request.onOpen = function (response) {
        input.removeAttr('disabled').focus();
        status.text('Choose name:');
        transport = response.transport;
    };

    request.onTransportFailure = function (errorMsg, request) {
        if (window.EventSource) {
            request.fallbackTransport = "sse";
        }
    };

    request.onMessage = function (response) {

        var message = response.responseBody;
        console.log(message);
        try {
            var json = jQuery.parseJSON(message);
        } catch (e) {
            console.log('This doesn\'t look like a valid JSON: ', message.data);
            return;
        }

        if (!logged && myName) {
            logged = true;
            status.text(myName + ': ').css('color', 'blue');
            input.removeAttr('disabled').focus();
        }
        else if(json.type === 'northPole' && json.who != 'all'){
            addMessage(json.who, json.message);
        }
        else if(json.who === 'all'){
            printArray($('#GiftsDelivered'), json.message);
        }
        else{
           input.removeAttr('disabled').focus();
        }
        if(json.who === 'Santa' && json.message === 'Unharnessing reindeer') {
            $('#GiftsDelivered').html('<p>Waiting for gifts</p>');
        }
    };

    function printArray($container, wishlist) {
        var listItems = '<p>Gifts Delivered</p>';
        for (var i = 0; i < wishlist.length; i++) {
            listItems += '<li>' + wishlist[i] + '</li>';
        }
        $container.html(listItems);
    }

    request.onClose = function (response) {
        logged = false;
    };

    request.onError = function (response) {
        content.html($('<p>', { text: 'Sorry, but there\'s some problem with your '
            + 'socket or the server is down' }));
    };

    subSocket = socket.subscribe(request);

    function addMessage(who, message) {
        $('#'+who+'Msg').text(message);
    }
    input.keydown(function (e) {
        if (e.keyCode === 13) {
            var msg = $(this).val();
            var msgType = '';
            // First message is always the author's name
            if (author == null) {
                msgType = 'name';
                author = msg;
            }

            subSocket.push(jQuery.stringifyJSON({ author: author, message: msg, type: (msgType === 'name' ? msgType : 'wishlist') }));
            $(this).val('');

            input.attr('disabled', 'disabled');
            if (myName === false) {
                myName = msg;
            }
        }
    });
});
