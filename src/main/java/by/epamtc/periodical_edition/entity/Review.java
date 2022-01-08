package by.epamtc.periodical_edition.entity;


import javax.persistence.*;

@Entity
@Table(name = "review")
public class Review extends BaseEntity<Long> {
    @Column(name = "user_comment")
    private String userComment;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "periodical_edition_id")
    private PeriodicalEdition periodicalEdition;


    public Review() {}

    public Review(Long id, String userComment, int rating, User user, PeriodicalEdition periodicalEdition) {
        super.setId(id);
        this.userComment = userComment;
        this.rating = rating;
        this.user = user;
        this.periodicalEdition = periodicalEdition;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Review aThat = (Review) object;

        if (getId() == null) {
            if (aThat.getId()  != null) { return false;}
        } else if (!getId().equals(aThat.getId())) { return false;}

        if (getUserComment() == null) {
            if (aThat.getUserComment()  != null) { return false;}
        } else if (!getUserComment().equals(aThat.getUserComment())) { return false;}

        return getRating() == aThat.getRating();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (getUserComment() != null ? getUserComment().hashCode() : 0);
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        result = prime * result + getRating();
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + getId() +
                ", userComment='" + getUserComment() + '\'' +
                ", rating=" + getRating() +
                '}';
    }
}