<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Messenger</title>
    <link rel="stylesheet" href="..\..\CSS\chat.css">
</head>
<body>
        <div class="messenger-container">
            <!-- Left Sidebar -->
            <div class="friends-list">
                <h3>Your Friends</h3>
                <ul>
                    <c:forEach var="friend" items="${friends}">
                        <li data-friend-id="${friend.id}">
                            <img src="data:image/jpeg;base64,${friend.base64photo}" alt="Profile Picture" class="friend-photo">
                            <div class="friend-info">
                                <p class="friend-name">${friend.name}</p>
                                <p class="last-message-content">
                                    ${friend.lastMessage.content}
                                </p>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <!-- Chat Area -->
            <div class="chat-area">
                <div class="chat-header">
                    <h3>Chat</h3>
                </div>
                <div class="chat-messages" id="chatMessages">
                    <!-- Messages will be dynamically loaded here -->
                </div>
                <div class="chat-input">
                    <textarea id="messageInput" placeholder="Type a message..."></textarea>
                    <button onclick="sendMessage()">Send</button>
                </div>
            </div>
        </div>

        <template id="message-template">
            <div class="message-wrapper">
                <div class="message">
                    <p class="message-content"></p>
                </div>
            </div>
        </template>
    <script>
        const userId = "${sessionScope.account.id}";
        // const socket = new WebSocket("ws://localhost:8085/chat/"+userId);

        const socketUrl = "ws://localhost:8085/chat/" +userId;
        let socket = null;
        let selectedFriendId = null;
        const chatMessages = document.getElementById('chatMessages');
        const messageInput = document.getElementById('messageInput');
        const defaultChatArea = `
            <div class="default-icon">
                <img class = "default-chat-photo" src="../../images/chat.png" alt="Chat Icon">
                <p>Select a friend to start chatting</p>
            </div>
        `;

        // Initialize default chat area
        chatMessages.innerHTML = defaultChatArea;

        // Function to initialize WebSocket connection
        function initSocket() {
            socket = new WebSocket(socketUrl);

            socket.onopen = function () {
                console.log("WebSocket connection established.");
            };

            socket.onmessage = function (event) {
                const message = JSON.parse(event.data);
                if (message.senderId === selectedFriendId || message.receiverId === selectedFriendId) {
                    displayMessage(message, 'received');
                }
            };

            socket.onclose = function () {
                console.log("WebSocket connection closed. Reconnecting...");
                setTimeout(initSocket, 3000); // Reconnect after 3 seconds
            };

            socket.onerror = function (error) {
                console.error("WebSocket error:", error);
            };
        }

        // Initialize WebSocket connection
        initSocket();

        // Function to send a message
        function sendMessage() {
            const content = messageInput.value.trim();
            if (content && selectedFriendId) {
                const message = { senderId: userId, receiverId: selectedFriendId, content: content, timestamp: new Date().toISOString() };
                socket.send(JSON.stringify(message));
                displayMessage(message, 'sent');
                messageInput.value = '';
            } else {
                alert("Please select a friend to send a message.");
            }
        }

        // Function to display a message in the chat area
        function displayMessage(message, type) {
            try {

                // Parse the message if it's a string
                const parsedMessage = typeof message === 'string' ? JSON.parse(message) : message;

                const template = document.getElementById('message-template');
                const messageNode = template.content.cloneNode(true);

                // Create a wrapper div for the message
                const messageWrapper = messageNode.querySelector('.message-wrapper');
                const messageContent = messageNode.querySelector('.message-content');

                // Add content to the message
                messageWrapper.classList.add(type);
                messageContent.textContent = parsedMessage.content || "No content";
                console.log("message type: "+type);

                chatMessages.appendChild(messageNode);

                chatMessages.scrollTop = chatMessages.scrollHeight;
            } catch (error) {
                console.error("Error displaying message:", error);
            }
        }


        // Function to load chat history when a friend is selected
        function loadChatHistory(friendId) {
            if (!friendId) {
                console.error("No friendId provided to load chat history.");
                return;
            }
            selectedFriendId = friendId;
            console.log("fetching chat history for..."+selectedFriendId);
            // Clear existing messages
            chatMessages.innerHTML = '';

            // Fetch chat history via AJAX
            fetch(`/fetchChatHistory/`+friendId)
                .then(response => response.json())
                .then(messages => {
                    messages.forEach(message => {
                        const type = message.senderId === userId ? 'sent' : 'received';
                        displayMessage(message, type);
                    });
                })
                .catch(error => console.error("Error loading chat history:", error));
        }

        // Event listener for selecting a friend
        document.querySelectorAll('.friends-list li').forEach(friend => {
            friend.addEventListener('click', function () {
                const friendId = this.dataset.friendId; // Assuming each friend li has a `data-friend-id` attribute
                document.querySelectorAll('.friends-list li').forEach(li => li.classList.remove('active'));
                this.classList.add('active');
                loadChatHistory(friendId);
            });
        });

        // Display default chat area if no friend is selected
        if (!selectedFriendId) {
            chatMessages.innerHTML = defaultChatArea;
        }

    </script>
</body>
</html>
