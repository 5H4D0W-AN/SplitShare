
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <style>
        /* General Page Styling */
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .login-container {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 300px;
            text-align: center;
        }

        h2 {
            margin-bottom: 20px;
            color: #333;
        }

        /* Input Styling */
        .input-field {
            margin: 10px 0;
            display: flex;
            flex-direction: column;
            text-align: left;
        }

        .input-field label {
            margin-bottom: 5px;
            font-size: 14px;
            color: #555;
        }

        .input-field input {
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            outline: none;
        }

        .input-field input:focus {
            border-color: #a87c4f;
        }

        /* Buttons and Link Styling */
        .login-btn, .create-account-link {
            background-color: #a87c4f; /* Light Brown Color */
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin: 10px 0;
            text-align: center;
        }

        .login-btn:hover {
            background-color: #8b643b;
        }

        .create-account-link {
            background-color: transparent;
            color: #a87c4f;
            text-decoration: underline;
            margin-top: 10px;
        }

        .create-account-link:hover {
            color: #8b643b;
        }

        .app-name{
            color: #8b643b;
        }
        .success-container {
            background-color: #d4edda; /* Light Green */
            color: #155724; /* Dark Green */
            border: 1px solid #c3e6cb;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 10px;
            left: 50%;
            transform: translateX(-50%);
            z-index: 1000;
            animation: fadeOut 2s ease forwards;
        }
        @keyframes fadeOut{
            0% {
                opacity: 1;
            }
            100% {
                opacity: 0;
            }
        }
    </style>
</head>
<body>

        <c:if test="${not empty logoutMessage}">
            <div class = "success-container">
                 <p>${logoutMessage}</p>
            </div>
        </c:if>

    <div class="app-name">
            <h1>SplitShare - Login</h1>
    </div>
    <div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </div>
    <div class="login-container">
        <h2>Login</h2>
        <form action="/login" method="POST" onsubmit="return validateForm()">
            <!-- User ID Input -->
            <div class="input-field">
                <label for="userId">User ID:</label>
                <input type="text" id="userId" name="userId" placeholder="Enter your User ID">
            </div>

            <!-- Password Input -->
            <div class="input-field">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" placeholder="Enter your Password">
            </div>


            <!-- Login Button -->
            <button type="submit" class="login-btn">Login</button>

            <!-- Create Account Link -->
            <a href="<c:url value='/createNewAccount' />" class="create-account-link">Create New Account</a>

        </form>
        <c:if test="${not empty message}">
            <p style="color: red;">${message}</p>
        </c:if>
    </div>

    <script>
        function validateForm() {
            var userId = document.getElementById("userId").value.trim();
            var password = document.getElementById("password").value.trim();

            if (userId === "" || password === "") {
                alert("User ID and Password are required!");
                return false; // Prevent form submission
            }
            return true; // Allow form submission
        }
    </script>
</body>
</html>


