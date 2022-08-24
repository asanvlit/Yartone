# 🔮  Yartone

## 🐱 My Pet-project "Platform for artists"

## Used technologies:

* REST API
* Spring boot, Spring security with JWT, Spring Data JPA
* PostgreSQL, Minio, MongoDB
* Liquibase
* WebClient
* OAuth 2 (using VK API & WebClient)
* JUnit 5
* Java Mail
* Gradle

## Project functionality:

* Create posts
* Likes on posts
* Account Subscriptions
 
## Additional features :

* Login via VKontakte account
* Ability to reset password (for example, if you forgot your password)
* Confirm-codes for account verification or password-reset are sent by email using JavaMailSender
* Confirmation codes have a **lifetime**, and their **hashed** value is stored in the database
* Ability to resend the account verification code (for example, if the email wasn't delivered)
