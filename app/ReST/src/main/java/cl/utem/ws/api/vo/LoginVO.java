package cl.utem.ws.api.vo;

import cl.utem.ws.domain.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoginVO extends Utem {

    @JsonProperty("token")
    private String token = null;

    @JsonProperty("key")
    private String key = null;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
