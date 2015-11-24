package com.nightswatch.dal.entity.user;

import com.nightswatch.dal.entity.AbstractEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Each user may have different role with different permissions. A role may enable/disable to access any resources
 * in the application.
 */
@Entity
@Table(name = "ROLE")
@Access(AccessType.FIELD)
public class Role extends AbstractEntity {

    /**
     * Each role must have at least a name to make it more user friendly
     */
    @Column(name = "ROLE_NAME", nullable = false, unique = true)
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + roleName + '\'' +
                '}';
    }
}
