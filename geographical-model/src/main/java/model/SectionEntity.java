package model;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by chetan on 22.12.2017.
 */
@Entity
@Table(name = "section")
@SequenceGenerator(name = "seq_section", sequenceName = "seq_section")
@Data
public class SectionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_section")
    private Long id;

    @Column(name = "section_name")
    private String sectionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private JobEntity job;


    @OneToMany(mappedBy = "section")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<GeographicalClassesEntity> geographicalClassesEntityList;
}
