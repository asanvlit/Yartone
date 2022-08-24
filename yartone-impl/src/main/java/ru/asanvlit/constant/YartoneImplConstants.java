package ru.asanvlit.constant;

import ru.asanvlit.dto.enums.FileType;

import java.util.Map;

import static java.util.Map.entry;
import static ru.asanvlit.constant.YartoneApiConstants.API;

public interface YartoneImplConstants {

    /** ===== URL's ===== */

    String HTTP = "http:/";
    String HTTPS = "https:/";

    String LOCALHOST = "/localhost";

    String AUTHENTICATION_URL = API + "/login";
    String SIGN_UP_URL = API + "/sign-up";
    String ACCOUNT_CONFIRM_URL = API + "/account/confirmation/**";
    String RESEND_ACCOUNT_CONFIRM_CODE_URL = API + "/account/confirmation/confirm-code";
    String PASSWORD_RESET = API + "/account/password-reset/**";
    String REFRESH_TOKEN_URL = API + "/token/refresh";
    String USER_INFO_BY_TOKEN_URL = API + "/token/user-info";

    String USERNAME_PARAM = "email";

    /** == VK OAUTH == */

    String OAUTH_API = API + "/oauth";

    String VK_API_HOST = "/api.vk.com";
    String VK_API_METHOD = "/method";
    String VK_OAUTH_HOST = "/oauth.vk.com";

    String VK_API_URL = HTTPS + VK_API_HOST + VK_API_METHOD;
    String VK_OAUTH_URL = HTTPS + VK_OAUTH_HOST;

    String VK_OAUTH_URLS = OAUTH_API + "/vk";
    String VK_OAUTH_LINKS = VK_OAUTH_URLS + "/link";
    String VK_OAUTH_ALL_LINKS = VK_OAUTH_LINKS + "/**";
    String VK_OAUTH_ACCESS = VK_OAUTH_URLS + "/access";

    String VK_OAUTH_LOGIN_REDIRECT_URL = VK_OAUTH_ACCESS + "/account";

    String VK_GET_USER_INFO_URL = VK_API_URL + "/users.get";

    String OAUTH_CODE_PARAM = "code";

    /** ===== PATHS ===== */

    String CLASSPATH = "classpath:/";

    /** ===== SPRING SECURITY ===== */

    String ROLE_CLAIM = "role";
    String EMAIL_CLAIM = "email";

    String AUTHORIZATION_HEADER_NAME = "AUTHORIZATION";
    String BEARER = "Bearer";

    String ROLE_AUTHORITY = "ROLE_";

    String DECODED_ROLE_PARAM = "role";
    String DECODED_EMAIL_PARAM = "email";
    String DECODED_SUBJECT_PARAM = "sub";
    String DECODED_EXPIRES_PARAM = "exp";

    /** ===== LOGIC ===== */

    String CODES_ENCODING_ALG = "HmacSHA256";

    String[] IMAGE_ACCEPTED_EXTENSIONS = new String[]{"jpg", "jpeg", "png"};

    Map<FileType, String[]> EXTENSIONS_FOR_FILE_TYPES = Map.ofEntries(
            entry(FileType.PROFILE_PHOTO, IMAGE_ACCEPTED_EXTENSIONS),
            entry(FileType.POST_ATTACHMENT, IMAGE_ACCEPTED_EXTENSIONS)
    );

    String[] TEXT_PROHIBITED_TAGS = new String[]{"script"};

    int POST_MAX_ATTACHMENTS_COUNT = 10;

    /** ===== MESSAGES ===== */

    String SIGN_UP_EMAIL_TITLE = "Yartone registration";
    String RESET_PASSWORD_EMAIL_TITLE = "Yartone password reset";

    String DB_DUPLICATE_MESSAGE = "Violation of the uniqueness condition";
    String METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE = "Invalid method argument type";
    String JSON_PARSE_ERROR_MESSAGE = "Json parse error";
    String EMAIL_EXCEPTIONS_MESSAGE = "Could not perform the action due to email errors on the server";

    /** ===== HEADERS ===== */

    String CONTENT_TYPE_HEADER = "Content-Type";
}
