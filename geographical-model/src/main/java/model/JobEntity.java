package model;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by chetan on 22.12.2017.
 */

@Entity
@Table(name = "job")
@Data
public class JobEntity implements Serializable {

    @Id
    private Long id;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "file_name")
    private String fileName;

    @OneToMany(mappedBy = "job", orphanRemoval = true, fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<SectionEntity> sectionEntityList;
}
