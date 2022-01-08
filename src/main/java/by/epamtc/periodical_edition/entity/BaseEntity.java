package by.epamtc.periodical_edition.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
@ToString
@MappedSuperclass
public abstract class BaseEntity<E extends Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private E id;
}