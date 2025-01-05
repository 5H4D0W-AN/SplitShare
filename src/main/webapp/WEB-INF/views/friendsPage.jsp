<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Friends</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f2f5;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 10px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        .friend-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px solid #ccc;
        }
        .friend-item:last-child {
            border-bottom: none;
        }
        .friend-details {
            display: flex;
            align-items: center;
        }
        .friend-photo {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            margin-right: 10px;
        }
        .friend-name {
            font-size: 1.2em;
            font-weight: bold;
        }
        .unfriend-button {
            background-color: #ff4d4d;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
        }
        .unfriend-button:hover {
            background-color: #e60000;
        }
        /* Modal Styles */
        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }
        .modal-content {
            background: white;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
        }
        .modal-buttons {
            margin-top: 20px;
            display: flex;
            justify-content: center;
            gap: 20px;
        }
        .modal-button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .confirm-button {
            background-color: #28a745;
            color: white;
        }
        .cancel-button {
            background-color: #dc3545;
            color: white;
        }
    </style>
    <script>
        let currentFriendId = null;

        function showModal(friendId) {
            currentFriendId = friendId;
            document.getElementById('confirmation-modal').style.display = 'flex';
        }

        function hideModal() {
            document.getElementById('confirmation-modal').style.display = 'none';
            currentFriendId = null;
        }

        async function confirmUnfriend() {
            if (!currentFriendId) return;

            try {
                let url = "/unfriend?id="+currentFriendId;
                const response = await fetch(url, {
                    method: "DELETE",
                });

                if (response.ok) {
                    document.getElementById("friend-" + currentFriendId).remove();
                    hideModal();
                } else {
                    alert("Failed to unfriend. Please try again.");
                }
            } catch (error) {
                console.error("Fetch error:", error);
                alert("Error while unfriending. Check your connection.");
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Your Friends</h1>
        <div class="friend-list">
            <c:forEach var="friend" items="${friends}">
                <div class="friend-item" id="friend-${friend.id}">
                    <div class="friend-details">
                        <img src="data:image/jpeg;base64,${friend.base64photo}" alt="Friend Photo" class="friend-photo">
                        <span class="friend-name">${friend.name}</span>
                    </div>
                    <button class="unfriend-button" onclick="showModal('${friend.id}')">Unfriend</button>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- Modal -->
    <div id="confirmation-modal" class="modal">
        <div class="modal-content">
            <h2>Confirm Unfriend</h2>
            <p>Are you sure you want to unfriend this person?</p>
            <div class="modal-buttons">
                <button class="modal-button confirm-button" onclick="confirmUnfriend()">Yes</button>
                <button class="modal-button cancel-button" onclick="hideModal()">No</button>
            </div>
        </div>
    </div>
</body>
</html>
