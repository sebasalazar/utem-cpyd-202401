package cl.utem.ws.api.vo;

import cl.utem.ws.domain.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author seba
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InfoVO extends Utem {

    @JsonProperty("data")
    private String data = null;

    public InfoVO() {
        this.data = "";
    }

    public InfoVO(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
