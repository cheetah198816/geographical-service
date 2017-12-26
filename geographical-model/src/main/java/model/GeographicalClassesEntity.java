package model;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by chetan on 22.12.2017.
 */
@Entity
@Table(name = "geographical_classes")
@SequenceGenerator(name = "seq_geo_classes", sequenceName = "seq_geo_classes")
@Data
public class GeographicalClassesEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_geo_classes")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private SectionEntity section;
}
