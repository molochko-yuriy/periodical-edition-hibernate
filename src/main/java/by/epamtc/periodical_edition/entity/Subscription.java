package by.epamtc.periodical_edition.entity;

import by.epamtc.periodical_edition.enums.PaymentStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper=true)
@Entity
@Table(name = "subscription")
public class Subscription extends BaseEntity<Long> {
    @Column(name = "price")
    private int price;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    private List<Content> contents;

    @Builder
    public Subscription(Long id, int price, PaymentStatus paymentStatus, User user) {
        super.setId(id);
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.user = user;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Subscription aThat = (Subscription) object;

        if (getId() == null) {
            if (aThat.getId()  != null) { return false;}
        } else if (!getId().equals(aThat.getId())) { return false;}

        if (getPaymentStatus() == null) {
            if (aThat.getPaymentStatus()  != null) { return false;}
        } else if (!getPaymentStatus().equals(aThat.getPaymentStatus())) { return false;}

        return getPrice() == aThat.getPrice();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        result = prime * result + (getPaymentStatus() != null ? getPaymentStatus().hashCode() : 0);
        result = prime * result + getPrice();
        return result;
    }
}