<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sent Friend Requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">

    <style>
            .friend-action-btn {
                border: none;
                padding: 5px 10px;
                border-radius: 20px;
                font-size: 14px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            .withdraw-btn {
                background-color: #D3D3D3; /* Red for Withdraw */
                color: white;
            }

            .withdraw-btn:hover {
                background-color: #A9A9A9; /* Darker red on hover */
            }
    </style>

    <script>

        function withdrawRequest(friendId, button) {
            let url = "/withdrawFriendRequest?id="+friendId;
            fetch(url, {
                 method: 'POST',
            })
            .then(response => {
                if (response.ok) {
                    setTimeout(() => {
                        button.closest('li').remove();
                    }, 1000);
                }
            })
            .catch(error => console.error('Error withdrawing friend request:', error));
        }
    </script>



</head>
<body>

<div class="container">
    <h2>Sent Friend Requests</h2>

    <c:if test="${not empty sentRequests}">
        <ul class="list-group">
            <c:forEach var="friend" items="${sentRequests}">
                <li class="list-group-item d-flex align-items-center justify-content-between">
                    <div class="d-flex align-items-center">
                        <a href="/profile/${friend.id}" class="friend-name">
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
                        </a>
                    </div>

                    <!-- Withdraw Button -->
                    <button class="withdraw-btn friend-action-btn" data-friend-id="${friend.id}" onclick="withdrawRequest('${friend.id}', this)">
                        Withdraw
                    </button>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${empty sentRequests}">
        <p>No sent requests.</p>
    </c:if>
</div>


</body>
</html>
