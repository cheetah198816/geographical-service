package mappers;

import dto.excel.GeographicalClassData;
import dto.excel.SectionData;
import model.SectionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by chetan on 23.12.2017.
 */
@Component
public class SectionEntityMapper {

    public static SectionData entity2Dto(SectionEntity sectionEntity) {
        final SectionData sectionData = new SectionData();
        sectionData.setId(sectionEntity.getId());
        sectionData.setName(sectionEntity.getSectionName());
        final List<GeographicalClassData> geographicalClassDatas = sectionEntity.getGeographicalClassesEntityList()
                .stream()
                .map(geographicalClassesEntity -> GeographicalEntityMapper.entity2Dto(geographicalClassesEntity))
                .collect(Collectors.toList());

        sectionData.setGeographicalClassDataList(geographicalClassDatas);

        return sectionData;
    }
}
