package by.epamtc.periodical_edition.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper=true)
@Entity
@Table(name = "user_role")
public class Role extends BaseEntity<Long>{
    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private List<User> users = new ArrayList<>();

    public Role(Long id, String roleName) {
        super.setId(id);
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Role aThat = (Role) object;

        if (getId() == null) {
            if (aThat.getId()  != null) { return false;}
        } else if (!getId().equals(aThat.getId())) { return false;}

        if (getRoleName() == null) {
            return aThat.getRoleName() == null;
        } else return getRoleName().equals(aThat.getRoleName());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        result = prime * result + (getRoleName() != null ? getRoleName().hashCode() : 0);
        return result;
    }
}