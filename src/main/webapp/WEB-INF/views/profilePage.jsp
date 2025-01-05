<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }
        .profile-container {
            text-align: center;
            padding: 20px;
            background-color: #fff;
            border-bottom: 1px solid #ddd;
        }
        .profile-photo {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid #ddd;
        }
        .profile-details {
            margin: 15px 0;
        }
        .posts-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 10px;
            padding: 20px;
        }
        .post {
            position: relative;
            cursor: pointer;
        }
        .post img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 5px;
        }
        /* Modal Styles */
        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.8);
            z-index: 1000;
            justify-content: center;
            align-items: center;
        }
        .modal-content {
            display: flex;
            background: #fff;
            width: 80%;
            max-height: 90%;
            overflow: hidden;
            border-radius: 5px;
        }
        .modal-image {
            flex: 3;
            background-color: #000;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .modal-image img {
            max-width: 100%;
            max-height: 100%;
        }
        .modal-details {
            flex: 2;
            padding: 20px;
            display: flex;
            flex-direction: column;
        }
        .modal-details .likes-count {
            font-weight: bold;
            margin-bottom: 10px;
        }
        .comments-section {
            flex: 1;
            overflow-y: auto;
            margin-bottom: 10px;
        }
        .comments-section p {
            margin: 5px 0;
        }
        .add-comment {
            display: flex;
            gap: 10px;
        }
        .add-comment input {
            flex: 1;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 3px;
        }
        .add-comment button {
            padding: 8px 12px;
            background-color: #0095f6;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        .add-comment button:hover {
            background-color: #007bb5;
        }
    </style>
</head>
<body>
    <div class="profile-container">
        <img src="data:image/jpeg;base64,${account.profile.base64photo}" alt="Profile Photo" class="profile-photo">
        <div class="profile-details">
            <p>${account.id}</p>
            <h2>${account.profile.name}</h2>
            <p>${account.email}</p>
            <a href="/editProfile" class="edit-profile-link">Edit Profile</a>
        </div>
    </div>

    <div class="posts-container">
        <c:forEach var="post" items="${posts}">
            <div class="post" onclick="openModal(${post.id})">
                <img src="data:image/jpeg;base64,${post.photobytes}" alt="Post Photo">
            </div>
        </c:forEach>
    </div>

    <!-- Modal -->
    <div id="postModal" class="modal">
        <div class="modal-content">
            <div class="modal-image">
                <img id="modalImage" src="" alt="Post Image">
            </div>
            <div class="modal-details">
                <div class="likes-count" id="modalLikes">0 Likes</div>
                <div class="comments-section" id="modalComments">
                    <!-- Comments will be dynamically added here -->
                </div>
                <div class="add-comment">
                    <input type="text" id="commentInput" placeholder="Add a comment...">
                    <button onclick="submitComment()">Post</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        function openModal(postId) {
            // Fetch post details dynamically (using AJAX if needed)
            const post = ${posts}.find(p => p.id === postId);
            document.getElementById('modalImage').src = `data:image/jpeg;base64,${post.photobytes}`;
            document.getElementById('modalLikes').textContent = `${post.likes} Likes`;

            const commentsSection = document.getElementById('modalComments');
            commentsSection.innerHTML = '';
            post.comments.forEach(comment => {
                const p = document.createElement('p');
                p.innerHTML = `<strong>${comment.accountId}:</strong> ${comment.text}`;
                commentsSection.appendChild(p);
            });

            // Show modal
            document.getElementById('postModal').style.display = 'flex';
        }

        function closeModal() {
            document.getElementById('postModal').style.display = 'none';
        }

        function submitComment() {
            const comment = document.getElementById('commentInput').value;
            // Send the comment to the backend via AJAX (implement this)
            alert(`Comment submitted: ${comment}`);
        }

        // Close modal on clicking outside
        window.onclick = function (event) {
            const modal = document.getElementById('postModal');
            if (event.target === modal) {
                closeModal();
            }
        };
    </script>
</body>
</html>
