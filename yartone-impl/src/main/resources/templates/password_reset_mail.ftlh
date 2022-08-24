<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        div {
            text-align: center;
            align-items: center;
        }
        h2 {
            color: #444485;
        }
        #main-text {
            border: 1px solid #d0d0d0;
            font-size: medium;
            padding: 2vh 3vh;
            text-align: center;
        }
    </style>
</head>
<body>
<h2></h2>
<div class="message">
    <h2><strong>Yartone password reset</strong></h2>
    <div>
        <h4 id="main-text">
            Dear ${username}! To confirm your password-reset and create new password,
            follow the <a href="localhost/api/v1/account/password-reset/${confirm_code}">link</a>
            <br>
            <h3>
                If you haven't initiated a password reset request, just skip this message.
            </h3>
        </h4>
    </div>
</div>
</body>
</html>

