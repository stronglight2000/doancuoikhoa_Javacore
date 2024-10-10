package doancuoikhoa.login.entities;

import doancuoikhoa.login.enums.Role;

import java.util.List;

public class Tenant extends User {

    public Tenant(String username, String password) {
        super(username, password);
    }

    public Tenant(String username, String email, String phoneNumber, Role role, Room room) {
        super(username, email, phoneNumber, Role.TENANT);

    }

}
