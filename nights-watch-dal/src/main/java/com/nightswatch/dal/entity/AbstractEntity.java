package com.nightswatch.dal.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Each entity extends this has auditing information.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractEntity implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column(name = "C_VERSION", nullable = false, precision = 4)
    private Integer version;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass().equals(obj.getClass())) {
            final Entity entity = (Entity) obj;
            return Objects.equals(this.getId(), entity.getId())
                    || this.getId().equals(entity.getId());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : 31;
    }

    @Override
    public int compareTo(Entity o) {
        return this.getId().compareTo(o.getId());
    }
}
