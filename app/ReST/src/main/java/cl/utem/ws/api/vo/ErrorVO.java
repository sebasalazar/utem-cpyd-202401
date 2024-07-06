package cl.utem.ws.api.vo;

import cl.utem.ws.domain.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 *
 * @author seba
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorVO extends Utem {

    private final int code;
    private final String cause;
    private final OffsetDateTime date;

    public ErrorVO() {
        this.code = 999;
        this.cause = "Error desconocido";
        this.date = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public ErrorVO(int code, String cause) {
        this.code = code;
        this.cause = cause;
        this.date = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public ErrorVO(int code, String cause, OffsetDateTime date) {
        this.code = code;
        this.cause = cause;
        this.date = date;
    }

    @JsonProperty("code")
    public int getCode() {
        return code;
    }

    @JsonProperty("cause")
    public String getCause() {
        return cause;
    }

    @JsonProperty("date")
    public OffsetDateTime getDate() {
        return date;
    }
}
