<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Account Created</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .success-container {
            background-color: #d4edda; /* Light Green */
            color: #155724; /* Dark Green */
            border: 1px solid #c3e6cb;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        h2 {
            margin: 0 0 10px 0;
        }

        p {
            margin: 10px 0;
        }

        button {
            background-color: #007bff; /* Blue */
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }

        .timer-message {
            margin-top: 15px;
            font-style: italic;
            color: #555;
        }
    </style>
    <script>
        let timer;
        let secondsLeft = 5; // Initial countdown time in seconds

        function updateTimerDisplay() {
            const timerDisplay = document.getElementById("timerDisplay");
            timerDisplay.textContent = secondsLeft;
        }

        function startRedirectTimer() {
            updateTimerDisplay(); // Update the display immediately
            timer = setInterval(() => {
                if (secondsLeft > 0) {
                    secondsLeft--;
                    updateTimerDisplay();
                } else {
                    clearInterval(timer);
                    window.location.href = "/login"; // Redirect to login page
                }
            }, 1000); // Update every second
        }

        function stopRedirectTimer() {
            clearInterval(timer);
            const timerMessage = document.getElementById("timerMessage");
            timerMessage.textContent = "Timer stopped. You will remain on this page.";
        }

        window.onload = function () {
            startRedirectTimer();
        };
    </script>
</head>

<body>
    <div class="success-container">
        <h2>Account Created Successfully!</h2>
        <p>Your account has been created. You will be redirected to the login page in <span id="timerDisplay">5</span> seconds.</p>
        <p>
            <button onclick="stopRedirectTimer()">Stop Timer</button>
        </p>
        <p id="timerMessage" class="timer-message"></p>
    </div>
</body>
</html>
