package cl.utem.ws.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "faculties")
public class Faculty extends PkBaseEntity {

    @Column(name = "code", nullable = false, unique = true)
    private String code = null;

    @Column(name = "name", nullable = false)
    private String name = null;

    @Column(name = "address", nullable = false)
    private String address = null;

    @Column(name = "latitude", nullable = false)
    private Double latitude = null;

    @Column(name = "longitude", nullable = false)
    private Double longitude = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
