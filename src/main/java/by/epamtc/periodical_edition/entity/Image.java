package by.epamtc.periodical_edition.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper=true)
@Entity
@Table(name = "periodical_edition_image")
public class Image extends BaseEntity<Long> {

    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "periodical_edition_id")
    private PeriodicalEdition periodicalEdition;

    @Builder
    public Image(Long id, PeriodicalEdition periodicalEdition, String imagePath) {
        super.setId(id);
        this.imagePath = imagePath;
        this.periodicalEdition = periodicalEdition;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {return true;}
        if (object == null || getClass() != object.getClass()) {return false;}

        Image aThat = (Image) object;

        if (getId() == null) {if (aThat.getId() != null) {return false;}
        } else if (!getId().equals(aThat.getId())) {return false;}

        if (getImagePath() == null) {
            return aThat.getImagePath() == null;
        } else return getImagePath().equals(aThat.getImagePath());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (getImagePath() != null ? getImagePath().hashCode() : 0);
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
}