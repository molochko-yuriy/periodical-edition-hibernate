package by.epamtc.periodical_edition.entity;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "content")
public class Content extends BaseEntity<Long> {
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @ManyToOne
    @JoinColumn(name = "periodical_edition_id")
    private PeriodicalEdition periodicalEdition;

    public Content() {}

    @Builder
    public Content(Long id, LocalDate startDate, LocalDate expirationDate, int price,
                   Subscription subscription, PeriodicalEdition periodicalEdition) {
        super.setId(id);
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.price = price;
        this.subscription = subscription;
        this.periodicalEdition = periodicalEdition;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {return true;}
        if (object == null || getClass() != object.getClass()) { return false; }
        Content aThat = (Content) object;

        if (getId() == null) {
            if (aThat.getId() != null) { return false;}
        } else if (!getId().equals(aThat.getId())) { return false;}

        if (getStartDate() == null) {
            if (aThat.getStartDate() != null) { return false;}
        } else if (!getStartDate().equals(aThat.getStartDate())) { return false;}

        if (getExpirationDate() == null) {
            if (aThat.getExpirationDate() != null) { return false;}
        } else if (!getExpirationDate().equals(aThat.getExpirationDate())) { return false;}

        return getPrice() == aThat.getPrice();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        result = prime * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = prime * result + (getExpirationDate() != null ? getExpirationDate().hashCode() : 0);
        result = prime * result + getPrice();
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + getId() +
                ", startDate=" + getStartDate() +
                ", expirationDate=" + getExpirationDate() +
                ", price=" + getPrice() +
                '}';
    }
}