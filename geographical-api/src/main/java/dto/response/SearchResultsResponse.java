package dto.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import dto.excel.SectionData;
import lombok.Data;

import java.util.List;

/**
 * Created by chetan on 23.12.2017.
 */
@Data
@JsonClassDescription("Dto containing the search results according to name and code.")
public class SearchResultsResponse {

    @JsonPropertyDescription("Section Data List according to the search query.")
    private List<SectionData> sectionDatas;
}
