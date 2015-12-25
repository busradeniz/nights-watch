package com.nightswatch.web.util;

import com.nightswatch.dal.entity.user.Role;

import java.util.ArrayList;
import java.util.Collection;

public final class RoleDtoConversionUtls {

    private RoleDtoConversionUtls() {
    }

    public static String convert(final Role role) {
        return role.getRoleName();
    }

    public static Collection<String> convert(final Collection<Role> roles) {
        final Collection<String> roleDtos = new ArrayList<>();
        for (Role role : roles) {
            roleDtos.add(RoleDtoConversionUtls.convert(role));
        }
        return roleDtos;
    }
}
