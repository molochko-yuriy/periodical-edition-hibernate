package by.epamtc.periodical_edition.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper=true)
@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "email")
    private String email;

    @Column(name = "balance")
    private int balance;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptions;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "user_role_link",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Builder

    public User(Long id, String lastName, String firstName, String login, String password, String mobilePhone,
                String email, int balance) {
        setId(id);
        this.lastName = lastName;
        this.firstName = firstName;
        this.login = login;
        this.password = password;
        this.mobilePhone = mobilePhone;
        this.email = email;
        this.balance = balance;
    }
}