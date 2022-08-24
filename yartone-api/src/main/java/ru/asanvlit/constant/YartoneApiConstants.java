package ru.asanvlit.constant;

public interface YartoneApiConstants {

    /** ===== URL's ====== */

    String API = "/api/v1";

    /** ===== VALIDATION ===== */

    String USERNAME_PATTERN = "[a-zA-Z0-9_\\.]{2,30}";
    String EMAIL_PATTERN = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    String NAME_PATTERN = "^[\\pL-'\\s]{2,55}$";

    String VALIDATION_ERROR_MESSAGE_TITLE = "Validation error";

    /** == POST == */

    int POST_HOURS_MIN_SPENT = 0;
    int POST_HOURS_MAX_SPENT = 5000;
    int POST_MATERIALS_MAX_LENGTH = 100;
    int POST_DESCRIPTION_MAX_LENGTH = 500;

    int POST_ATTACHMENTS_MIN = 1;
    int POST_ATTACHMENTS_MAX = 10;

    int PASSWORD_MIN_LENGTH = 8;
    int PASSWORD_MAX_LENGTH = 60;
    String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{" + PASSWORD_MIN_LENGTH + "," + PASSWORD_MAX_LENGTH + "}$";

    /** == ACCOUNT == */

    int ACCOUNT_BIOGRAPHY_MAX_LENGTH = 200;
}
