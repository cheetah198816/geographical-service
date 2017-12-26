package mappers;

import dto.excel.GeographicalClassData;
import model.GeographicalClassesEntity;
import org.springframework.stereotype.Component;

/**
 * Created by chetan on 23.12.2017.
 */
@Component
public class GeographicalEntityMapper {

    public static GeographicalClassData entity2Dto(GeographicalClassesEntity geographicalClassesEntity) {
        final GeographicalClassData geographicalClassData = new GeographicalClassData();
        geographicalClassData.setCode(geographicalClassesEntity.getCode());
        geographicalClassData.setName(geographicalClassesEntity.getName());
        geographicalClassData.setId(geographicalClassesEntity.getId());

        return geographicalClassData;
    }
}
