<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Messenger</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            display: flex;
            height: 100vh;
            overflow: hidden;
        }

        /* Left sidebar (friends list) */
        .friends-list {
            width: 30%;
            background-color: #f8f8f8;
            border-right: 1px solid #ddd;
            overflow-y: auto;
            display: flex;
            flex-direction: column;
        }

        .friend {
            display: flex;
            align-items: center;
            padding: 10px;
            cursor: pointer;
            border-bottom: 1px solid #ddd;
            transition: background-color 0.2s;
        }

        .friend:hover {
            background-color: #f1f1f1;
        }

        .friend img {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            margin-right: 10px;
        }

        .friend-info {
            flex-grow: 1;
        }

        .friend-name {
            font-weight: bold;
            font-size: 16px;
        }

        .friend-last-message {
            font-size: 14px;
            color: gray;
        }

        /* Chat window */
        .chat-window {
            width: 70%;
            display: flex;
            flex-direction: column;
        }

        .chat-header {
            padding: 10px;
            background-color: #f8f8f8;
            border-bottom: 1px solid #ddd;
            font-weight: bold;
            font-size: 18px;
        }

        .chat-messages {
            flex-grow: 1;
            padding: 10px;
            overflow-y: auto;
            background-color: #ffffff;
        }

        .message {
            margin-bottom: 10px;
        }

        .message.sent {
            text-align: right;
        }

        .message .message-content {
            display: inline-block;
            padding: 10px;
            border-radius: 10px;
            max-width: 60%;
        }

        .message.sent .message-content {
            background-color: #dcf8c6;
        }

        .message.received .message-content {
            background-color: #f1f1f1;
        }

        .chat-input {
            display: flex;
            padding: 10px;
            border-top: 1px solid #ddd;
            background-color: #f8f8f8;
        }

        .chat-input textarea {
            flex-grow: 1;
            resize: none;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .chat-input button {
            margin-left: 10px;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .chat-input button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <!-- Left Sidebar: Friends List -->
    <div class="friends-list">
        <c:forEach var="friend" items="${friends}">
            <div class="friend" onclick="openChat(${friend.id}, '${friend.name}')">
                <img src="data:image/png;base64,${friend.base64photo}" alt="Profile">
                <div class="friend-info">
                    <div class="friend-name">${friend.name}</div>
                    <div class="friend-last-message">${friend.lastMessage}</div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- Chat Window -->
    <div class="chat-window">
        <div class="chat-header" id="chat-header">Select a friend to start chatting</div>
        <div class="chat-messages" id="chat-messages">
            <!-- Chat messages will appear here -->
        </div>
        <div class="chat-input">
            <textarea id="message-input" placeholder="Type a message..."></textarea>
            <button onclick="sendMessage()">Send</button>
        </div>
    </div>

    <script>
        let selectedFriendId = null;
        // Open chat for the selected friend
        function openChat(friendId, friendName) {
            selectedFriendId = friendId;
            document.getElementById('chat-header').innerText = `Chat with ${friendName}`;
            document.getElementById('chat-messages').innerHTML = ''; // Clear previous messages
            fetchChatHistory(friendId);
        }

        // Fetch chat history via WebSocket or AJAX
        function fetchChatHistory(friendId) {
            stompClient.send('/app/fetchChatHistory', {}, JSON.stringify({ receiverId: friendId }));
        }

        // Display fetched messages
        function displayChatHistory(messages) {
            const chatMessages = document.getElementById('chat-messages');
            chatMessages.innerHTML = '';
            messages.forEach(message => {
                const messageDiv = document.createElement('div');
                messageDiv.className = `message ${message.senderId === selectedFriendId ? 'received' : 'sent'}`;
                messageDiv.innerHTML = `<div class="message-content">${message.content}</div>`;
                chatMessages.appendChild(messageDiv);
            });
        }

        // Send a new message
        function sendMessage() {
            const messageInput = document.getElementById('message-input');
            const content = messageInput.value;
            if (!content.trim() || !selectedFriendId) return;

            stompClient.send('/app/sendMessage', {}, JSON.stringify({
                senderId: loggedInUserId, // Replace with logged-in user ID
                receiverId: selectedFriendId,
                content: content
            }));
            messageInput.value = ''; // Clear input field
        }
    </script>
</body>
</html>
