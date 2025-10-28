package com.world.web.model;

import java.util.Objects;

public  record Student(String name, String email, String address) {
    public Student {
        Objects.requireNonNull(name);
        Objects.requireNonNull(email);
        Objects.requireNonNull(address);
    }
}
