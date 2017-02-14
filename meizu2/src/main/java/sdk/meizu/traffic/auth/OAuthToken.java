package sdk.meizu.traffic.auth;

import org.json.JSONObject;

public class OAuthToken {
    public static final String SERVER_KEY_ACCESS_TOKEN = "access_token";
    public static final String SERVER_KEY_EXPIRES_IN = "expires_in";
    public static final String SERVER_KEY_REFRESH_TOKEN = "refresh_token";
    public static final String SERVER_KEY_SCOPE = "scope";
    public static final String SERVER_KEY_TOKEN_TYPE = "token_type";
    private long mExpireIn;
    private String mRefreshToken;
    private String mScope;
    private String mToken;
    private String mTokenType;

    public OAuthToken(JSONObject jSONObject) {
        this.mToken = jSONObject.getString(SERVER_KEY_ACCESS_TOKEN);
        this.mRefreshToken = jSONObject.getString(SERVER_KEY_REFRESH_TOKEN);
        this.mExpireIn = jSONObject.getLong(SERVER_KEY_EXPIRES_IN);
        this.mScope = jSONObject.getString(SERVER_KEY_SCOPE);
        this.mTokenType = jSONObject.getString(SERVER_KEY_TOKEN_TYPE);
    }

    public String getToken() {
        return this.mToken;
    }

    public String getRefreshToken() {
        return this.mRefreshToken;
    }

    public String toString() {
        return "[" + this.mToken + " , " + this.mRefreshToken + " , " + this.mExpireIn + " , " + this.mScope + " , " + this.mTokenType + "]";
    }
}
