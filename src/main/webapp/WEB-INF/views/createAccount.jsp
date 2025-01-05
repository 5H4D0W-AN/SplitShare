<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>





<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create New Account</title>
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

        .form-container {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 400px;
        }

        h2 {
            margin-bottom: 20px;
            color: #333;
            text-align: center;
        }

        .input-field {
            margin: 10px 0;
            display: flex;
            flex-direction: column;
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

        .required {
            color: red;
        }

        .form-btn {
            background-color: #a87c4f;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
            text-align: center;
            margin-top: 10px;
            width: 100%;
        }

        .form-btn:hover {
            background-color: #8b643b;
        }

        .error-message {
            color: red;
            font-size: 12px;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function validateForm(event) {
            event.preventDefault(); // Prevent form submission
            let isValid = true;

                    // Reset previous errors
            $('.error-message').text('');
            $('input').removeClass('is-invalid');

                    // Validate required fields
            $('#createAccountForm').find('input').each(function () {
                const $input = $(this);
                if ($input.attr('type') !== 'file' && $input.val().trim() === '' && $input.attr('required') !== undefined) {
                    isValid = false;
                    $input.addClass('is-invalid');
                    $input.closest('.input-field').find('.error-message').text('This field is required.');
                }
            });
            const email = $('#email').val();
            if (email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
                isValid = false;
                $('#email').addClass('is-invalid');
                $('#email').closest('.input-field').find('.error-message').text('Invalid email format.');
            }
            const mobile = $('#mobile').val();
            if (mobile && !/^\d{10}$/.test(mobile)) {
                isValid = false;
                $('#mobile').addClass('is-invalid');
                $('#mobile').closest('.input-field').find('.error-message').text('Mobile number must be 10 digits.');
            }
            const password = $('#password').val();
            if (password && password.length < 8) {
                isValid = false;
                $('#password').addClass('is-invalid');
                $('#password').closest('.input-field').find('.error-message').text('Password must be atleast 8 charachters long.');
            }
            const name = $('#name').val();
            if (name && !/^[a-zA-Z]+$/.test(name)) {
                isValid = false;
                $('#name').addClass('is-invalid');
                $('#name').closest('.input-field').find('.error-message').text('Name must contain only alphabets.');
            }
            const photo = $('#photo').val();
            if(!photo){
                isValid = false;
                $('#photo').addClass('is-invalid');
                $('#photo').closest('.input-field').find('.error-message').text('Photo is mandatory. Would like to know if you are Ugly.');
            }
            if (isValid) {
                $('#createAccountForm')[0].submit();
            }
        }


    </script>
</head>
<body>
    <div class="form-container">
        <h2>Create New Account</h2>

        <form:form id="createAccountForm" action="/createNewAccount" method="POST" modelAttribute="account" enctype="multipart/form-data">

            <div class="input-field">
                <label for="email">Email: <span class="required">*</span></label>
                <form:input path="email" id="email" placeholder="Enter your Email" required="required" />
                <form:errors path="email" cssClass="error-message" />
                <span class="error-message"></span>
                <c:if test="${not empty message}">
                            <p style="color: red;">${message}</p>
                </c:if>
            </div>


            <div class="input-field">
                <label for="id">User ID: <span class="required">*</span></label>
                <form:input path="id" id="id" placeholder="Enter your User ID" required="required"/>
                <form:errors path="id" cssClass="error-message" />
                <span class="error-message"></span>
                <c:if test="${not empty message2}">
                            <p style="color: red;">${message2}</p>
                </c:if>
            </div>


            <div class="input-field">
                <label for="password">Password: <span class="required">*</span></label>
                <form:password path="password" id="password" placeholder="Enter your Password" required="required"/>
                <form:errors path="password" cssClass="error-message" />
                <span class="error-message"></span>
            </div>


            <div class="input-field">
                <label for="name">Name: <span class="required">*</span></label>
                <form:input path="profile.name" id="name" placeholder="Enter your Name" required="required" />
                <form:errors path="profile.name" cssClass="error-message" />
                <span class="error-message"></span>
            </div>


            <div class="input-field">
                <label for="photo">Photo: <span class="required">*</span></label>
                <form:input path="profile.photo" id="photo" type="file" />
                <form:errors path="profile.photo" cssClass="error-message" required="required"/>
                <span class="error-message"></span>
                <c:if test="${not empty message3}">
                    <p style="color: red;">${message3}</p>
                </c:if>
            </div>

            <div class="input-field">
                <label for="mobile">Mobile:</label>
                <form:input path="mobile" id="mobile" placeholder="Enter your Phone Number" />
                <form:errors path="mobile" cssClass="error-message" />
            </div>


            <div class="input-field">
                <label for="city">City: <span class="required">*</span></label>
                <form:input path="profile.address.city" id="city" placeholder="Enter your City" required="required" />
                <form:errors path="profile.address.city" cssClass="error-message" />
                <span class="error-message"></span>
            </div>


            <div class="input-field">
                <label for="state">State:</label>
                <form:input path="profile.address.state" id="state" placeholder="Enter your State" />
                <form:errors path="profile.address.state" cssClass="error-message" />
            </div>


            <div class="input-field">
                <label for="country">Country:</label>
                <form:input path="profile.address.country" id="country" placeholder="Enter your Country" />
                <form:errors path="profile.address.country" cssClass="error-message" />
            </div>


            <button type="button" class="form-btn" onclick="validateForm(event)" >Create Account</button>
        </form:form>
    </div>


</body>
</html>





