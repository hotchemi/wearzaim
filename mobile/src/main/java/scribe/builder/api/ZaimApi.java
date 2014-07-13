package scribe.builder.api;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public final class ZaimApi extends DefaultApi10a {

    private static final String ACCESS_TOKEN_RESOURCE = "https://api.zaim.net/v2/auth/access";

    private static final String REQUEST_TOKEN_RESOURCE = "https://api.zaim.net/v2/auth/request";

    private static final String AUTHORIZE_URL = "https://www.zaim.net/users/auth/?oauth_token=%s";

    @Override
    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_RESOURCE;
    }

    @Override
    public String getRequestTokenEndpoint() {
        return REQUEST_TOKEN_RESOURCE;
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return String.format(AUTHORIZE_URL, requestToken.getToken());
    }

}