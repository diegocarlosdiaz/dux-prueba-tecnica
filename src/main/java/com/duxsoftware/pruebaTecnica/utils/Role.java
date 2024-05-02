package com.duxsoftware.pruebaTecnica.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Role {
    USER(Arrays.asList(Permission.READ)),

    ADMIN(Arrays.asList(Permission.READ, Permission.WRITE));

    private List<Permission> permissions;

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
