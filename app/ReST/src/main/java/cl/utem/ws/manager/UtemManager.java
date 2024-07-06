package cl.utem.ws.manager;

import cl.utem.ws.api.vo.InfoVO;
import cl.utem.ws.domain.model.Faculty;
import cl.utem.ws.domain.repository.FacultyRepository;
import cl.utem.ws.exception.UtemException;
import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtemManager implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final double EARTH_RADIUS = 6378.14;

    private final transient FacultyRepository facultyRepository;

    @Autowired
    public UtemManager(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    private long distance(double latOne, double lonOne, double latTwo, double lonTwo) {
        double latOneRad = Math.toRadians(latOne);
        double lonOneRad = Math.toRadians(lonOne);
        double latTwoRad = Math.toRadians(latTwo);
        double lonTwoRad = Math.toRadians(lonTwo);

        double difLat = latTwoRad - latOneRad;
        double difLon = lonTwoRad - lonOneRad;

        // Fórmula del haversine
        double a = Math.sin(difLat / 2) * Math.sin(difLat / 2)
                + Math.cos(latOneRad) * Math.cos(latTwoRad)
                * Math.sin(difLon / 2) * Math.sin(difLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distancia en kilómetros
        double distanceInKm = EARTH_RADIUS * c;

        // Convertir a metros
        return (long) distanceInKm * 1000;
    }

    public InfoVO calculate(final String facultyCode, Double latitude, Double longitude) {
        InfoVO vo = null;
        try {
            final Faculty faculty = facultyRepository.findByCodeIgnoreCase(StringUtils.trimToEmpty(facultyCode));
            final long distance = distance(latitude, longitude, faculty.getLatitude(), faculty.getLongitude());
            vo = new InfoVO(String.format("La distancia desde tu posición actual a la %s es de %d metros", faculty.getName(), distance));
        } catch (Exception e) {
            throw new UtemException("No fue posible calcular la distancia", e);
        }
        return vo;
    }
}
