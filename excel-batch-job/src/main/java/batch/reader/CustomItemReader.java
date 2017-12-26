package batch.reader;

import model.GeographicalClassesEntity;
import model.JobEntity;
import model.SectionEntity;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.batch.item.ItemReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by chetan on 24.12.2017.
 */
public class CustomItemReader implements ItemReader<SectionEntity> {

    private String basePath;

    private Long jobId;

    private Workbook workbook;

    private JobEntity jobEntity;

    private Iterator<Row> rowIterator;

    public CustomItemReader(String fileName, Long jobId, String basePath) {
        this.jobId = jobId;
        this.basePath = basePath;
        initializeExcel(fileName);
    }

    private void initializeExcel(String fileName) {
        try {
            jobEntity = new JobEntity();
            jobEntity.setId(jobId);
            jobEntity.setFileName(fileName);
            jobEntity.setJobName("excelExport");
            final FileInputStream excelFile = new FileInputStream(new File(basePath + fileName));
            workbook = new HSSFWorkbook(excelFile);
            final Sheet sheet = workbook.getSheetAt(0);
            rowIterator = sheet.rowIterator();
        } catch (IOException e) {
            //log the error.
        }
    }

    @Override
    public SectionEntity read() throws Exception {
        SectionEntity sectionData = null;
        if (rowIterator != null) {
            if (rowIterator.hasNext()) {
                sectionData = setRowData();
            }
        }

        return sectionData;
    }

    private SectionEntity setRowData() {
        final SectionEntity sectionData;
        sectionData = new SectionEntity();
        sectionData.setJob(jobEntity);
        final Row currentRow = rowIterator.next();
        final Iterator<Cell> cellIterator = currentRow.iterator();
        final List<GeographicalClassesEntity> geographicalClassesEntities = new ArrayList<>();
        while (cellIterator.hasNext()) {
            setCellData(sectionData, cellIterator, geographicalClassesEntities);
        }
        return sectionData;
    }

    private void setCellData(SectionEntity sectionData, Iterator<Cell> cellIterator, List<GeographicalClassesEntity> geographicalClassesEntities) {
        final Cell currentCell = cellIterator.next();
        if (currentCell.getColumnIndex() == 0) {
            sectionData.setSectionName(currentCell.getStringCellValue());
        } else {
            final GeographicalClassesEntity geographicalClassData = new GeographicalClassesEntity();
            geographicalClassData.setName(currentCell.getStringCellValue());
            geographicalClassData.setCode(cellIterator.next().getStringCellValue());
            geographicalClassData.setSection(sectionData);
            geographicalClassesEntities.add(geographicalClassData);
        }
        sectionData.setGeographicalClassesEntityList(geographicalClassesEntities);
    }
}
