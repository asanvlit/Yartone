package ru.asanvlit.util.request;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.apache.tomcat.websocket.Constants.AUTHORIZATION_HEADER_NAME;
import static ru.asanvlit.constant.YartoneImplConstants.BEARER;

@UtilityClass
public class JwtRequestUtil {

    public String getFormattedAccessToken(String accessToken) {
        return BEARER.concat(StringUtils.SPACE).concat(accessToken);
    }

    public boolean hasAuthorizationToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER_NAME);
        return header != null && header.startsWith(BEARER);
    }

    public boolean isValidTokenScheme(String token) {
        return token.startsWith(BEARER.concat(StringUtils.SPACE));
    }

    public String getToken(HttpServletRequest request) {
        return getToken(request.getHeader(AUTHORIZATION_HEADER_NAME));
    }

    public String getToken(String fullFormat) {
        return fullFormat.replace(BEARER.concat(StringUtils.SPACE), StringUtils.EMPTY);
    }
}

