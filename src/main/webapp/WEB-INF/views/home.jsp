<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <style>
        body {
            background-color: #f7f7f7;
        }

        .profile-photo-container {
                position: flex;
                top: 20px; /* Adjust this for vertical spacing */
                left: 20px; /* Adjust this for horizontal spacing */
                width: 60px; /* Width of the circle */
                height: 60px; /* Height of the circle */
        }


        .profile-photo {
            width: 100%; /* Ensure it fits inside the container */
            height: 100%; /* Ensure it fits inside the container */
            border-radius: 50%; /* Make it circular */
            object-fit: cover; /* Crop the image to fit the circle */
            border: 2px solid #ddd; /* Optional: Add a border */
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); /* Optional: Add a shadow */
        }

        .ledger-box {
            background-color: #f5f0e1; /* Light brown */
            border-radius: 10px;
            padding: 10px;
            margin-top: 10px;
            font-size: 0.9rem;
        }

        .ledger-box .owe, .ledger-box .lent {
            font-size: 0.85rem;
        }

        .create-post-section {
            padding: 10px 20px; /* Adjust the padding for width and height */
            background-color: #a88e5b;
            color: white; /* Text color */
            border: none; /* Remove default border */
            border-radius: 30px; /* Rounded edges, making it oval */
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .create-post-section:hover {
            background-color: #8b643b; /* Darker color on hover */
        }

        .friend-suggestions img {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            margin-right: 10px;
        }
        .app-name {
            position: fixed;
            bottom: 10px;
            left: 10px;
            font-size: 12px;
            color: #8b643b;
        }
        .friend-photo {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 10px;
        }

        .friend-action-btn {
            border: none;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .friend-action-btn:hover {
             background-color: #A9A9A9;
        }

        .add-friend-btn {
            background-color: #a88e5b;
            color: white;
        }

        .add-friend-btn:hover {
            background-color: #8b643b; /* Darker green on hover */
        }

        .withdraw-friend-btn {
            background-color: #D3D3D3; /* Red for Withdraw */
            color: white;
        }

        .withdraw-friend-btn:hover {
            background-color: #A9A9A9;
        }

    </style>
    <script>
        // Accept Friend Request
        function acceptRequest(requestId, button) {
            let url = "/acceptRequest?id="+requestId;
            console.log("Accepting friend request from:", requestId);
            fetch(url, {
                method: 'POST',
            })
            .then(response => {
                if (response.ok) {
                    // Remove the request from the list
                    const listItem = button.closest('li');
                    listItem.parentNode.removeChild(listItem);
                } else {
                    alert("Failed to accept friend request. Try again.");
                }
            })
            .catch(err => console.error(err));
        }

        // Reject Friend Request
        function rejectRequest(requestId, button) {
            let url = "/rejectRequest?id="+requestId;
            console.log("Rejecting friend request from:", requestId);
            fetch(url, {
                method: 'POST',
            })
            .then(response => {
                if (response.ok) {
                    // Remove the request from the list
                    const listItem = button.closest('li');
                    listItem.parentNode.removeChild(listItem);
                } else {
                    alert("Failed to reject friend request. Try again.");
                }
            })
            .catch(err => console.error(err));
        }
        window.sendFriendRequest = function(friendId, button) {
            let url = "/addFriend?id="+friendId;
            console.log("Sending friend request to:", friendId);
            fetch(url, {
                method: 'POST',
            })
                .then(response => {
                    if (response.ok) {
                        // Hide "Add Friend" button and show "Withdraw" button
                        button.style.display = "none";
                        const withdrawButton = button.nextElementSibling;
                        withdrawButton.style.display = "inline-block";
                    } else {
                        alert("Failed to send friend request. Try again.");
                    }
                })
                .catch(err => console.error(err));
        }

        window.withdrawFriendRequest = function(friendId, button) {
            console.log("withdrawing friend request from:", friendId);
            let url = "/withdrawFriendRequest?id="+friendId;
            fetch(url, {
                method: 'POST',
            })
                .then(response => {
                    if (response.ok) {
                        // Hide "Withdraw" button and show "Add Friend" button
                        button.style.display = "none";
                        const addFriendButton = button.previousElementSibling;
                        addFriendButton.style.display = "inline-block";
                    } else {
                        alert("Failed to withdraw friend request. Try again.");
                    }
                })
                .catch(err => console.error(err));
        }

        function searchProfiles() {
            const inputElement = document.getElementById("searchInput");
            if (!inputElement) {
                console.error("Search input element not found!");
                return;
            }

            const query = inputElement.value.trim();
            console.log("Query Value after trimming:", query);

            if (query.length === 0) {
                console.warn("Empty query, skipping search");
                return;
            }
            let url = "/searchProfiles?query=";
            if (query) {
                url += encodeURIComponent(query);
            }
            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log("Received Data:", data);
                    const resultsContainer = document.getElementById("searchResults");
                    resultsContainer.innerHTML = '';
                    data.forEach(profile => {
                        const li = document.createElement("li");
                        li.textContent = profile.name;
                        li.onclick = () => window.location.href = `/profile/${profile.id}`;
                        resultsContainer.appendChild(li);
                    });
                })
                .catch(error => {
                    console.error("Error fetching profiles:", error);
                });
        }
    </script>
 </head>
<body>
<div class="container-fluid">
    <div class="row mt-4">
        <!-- Left Sidebar -->
        <div class="col-md-3">
            <div class="profile-photo-container">
                <c:set var="account" value="${sessionScope.account}" />
                <c:choose>
                    <c:when test="${not empty account.profile.base64photo}">
                        <img class = "profile-photo" src="data:image/jpeg;base64,${account.profile.base64photo}" alt="Profile Photo">
                    </c:when>
                    <c:otherwise>
                        <img class = "profile-photo" src="../../default-profile-photo.png" alt="Default Profile Photo">
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Navigation Links -->
            <ul class="list-group mt-4">
                <li class="list-group-item"><a href="/profile/${sessionScope.account.id}">Profile</a></li>
                <li class="list-group-item"><a href="/chats">Chats</a></li>
                <li class="list-group-item"><a href="/friends">Friends</a></li>
                <li class="list-group-item"><a href="/sentRequests"> Sent Requests</a></li>
                <li class="list-group-item"><a href="/ledger">
                    Ledger</a>
                    <div class="ledger-box mt-2">
                        <div class="owe">Owe: ${account.profile.ledger.owe} Rs</div>
                        <div class="lent">Lent: ${account.profile.ledger.lent} Rs</div>
                    </div>
                </li>
                <li class="list-group-item"><a href="/logout">Logout</a></li>
            </ul>
        </div>

        <!-- Main Content -->
        <div class="col-md-6">
            <!-- Create Post Button -->
                    <button class="create-post-section" onclick="window.location.href='/createPost'">Create Post</button>

                    <!-- Search Bar -->
                    <div class="search-bar">
                        <input type="text" id="searchInput" class="form-control" placeholder="Search Profiles..." oninput="searchProfiles()">
                        <ul id="searchResults" class="search-results"></ul>
                    </div>


            <!-- Posts -->
            <div id="posts">
                <!-- Posts of friends will be dynamically inserted here -->
                <c:forEach var="post" items="${posts}">
                    <div class="card mb-4">
                        <div class="card-body">
                            <h6>${post.author.profile.name}</h6>
                            <p>${post.content}</p>
                            <img src="data:image/jpeg;base64,<c:out value='${T(java.util.Base64).getEncoder().encodeToString(post.image)}' />"
                                 class="img-fluid" alt="Post Image">
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>


        <!-- Right Sidebar -->
        <div class="col-md-3">
            <!-- Friend Suggestions -->
            <c:if test="${not empty friendSuggestions}">
                <h5>Friend Suggestions</h5>
                <ul class="list-group">
                    <c:forEach var="friend" items="${friendSuggestions}">
                        <li class="list-group-item d-flex align-items-center justify-content-between">
                            <div class = "d-flex align-items-center">
                                <c:choose>
                                    <c:when test="${friend.profile.photobytes != null}">
                                        <img src="data:image/jpeg;base64,${friend.profile.base64photo}"
                                             alt="Friend Photo"
                                             class="rounded-circle me-2"
                                             style="width: 40px; height: 40px; object-fit: cover;">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="../../images/default-profile-photo.png"
                                             alt="Default Avatar"
                                             class="rounded-circle me-2"
                                             style="width: 40px; height: 40px; object-fit: cover;">
                                    </c:otherwise>
                                </c:choose>
                                <span>${friend.profile.name}</span>
                            </div>
                            <button class = "friend-action-btn add-friend-btn"
                                    data-friend-id ="${friend.id}"
                                    onclick = "sendFriendRequest('${friend.id}', this)">
                                    Add Friend
                            </button>
                            <button class = "friend-action-btn withdraw-friend-btn"
                                    data-friend-id ="${friend.id}"
                                    style="display: none;"
                                    onclick = "withdrawFriendRequest('${friend.id}', this)">
                                    Withdraw
                            </button>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>

            <!-- Pending Requests Section -->
            <c:if test="${not empty pendingRequests}">
                <h5>Pending Requests</h5>
                <ul class="list-group">
                    <c:forEach var="request" items="${pendingRequests}">
                        <li class="list-group-item d-flex align-items-center justify-content-between">
                            <div class="d-flex align-items-center">
                                <c:choose>
                                    <c:when test="${request.profile.photobytes != null}">
                                        <img src="data:image/jpeg;base64,${request.profile.base64photo}"
                                             alt="Request Photo"
                                             class="rounded-circle me-2"
                                             style="width: 40px; height: 40px; object-fit: cover;">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="../../images/default-profile-photo.png"
                                             alt="Default Avatar"
                                             class="rounded-circle me-2"
                                             style="width: 40px; height: 40px; object-fit: cover;">
                                    </c:otherwise>
                                </c:choose>
                                <span>${request.profile.name}</span>
                            </div>
                            <button class="friend-action-btn accept-request-btn"
                                    data-request-id="${request.id}"
                                    onclick="acceptRequest('${request.id}', this)">
                                Accept
                            </button>
                            <button class="friend-action-btn reject-request-btn"
                                    data-request-id="${request.id}"
                                    onclick="rejectRequest('${request.id}', this)">
                                <i class="fas fa-trash"></i>
                            </button>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>

    </div>
</div>
<div class="app-name">
    <h1>SplitShare</h1>
</div>

<!-- Bootstrap CSS -->
<link href="https://unpkg.com/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Bundle with Popper -->
<script src="https://unpkg.com/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
