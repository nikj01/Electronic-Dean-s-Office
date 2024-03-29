package ua.dgma.electronicDeansOffice.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"teacher", "pages"})
@Table(name = "TeachersJournals")
public class TeachersJournal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "journalSeq")
    @SequenceGenerator(name = "journalSeq", sequenceName = "journalSeqq", initialValue = 102)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private Teacher teacher;

    @OneToMany(
            mappedBy = "journal",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.REMOVE})
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private List<JournalPage> pages = new ArrayList<>();

    @Column(nullable = false)
    private boolean deleted;

    private LocalDateTime wasDeleted;
}
