package doancuoikhoa.login.entities;

import doancuoikhoa.login.enums.Role;

public class LandLord extends User {


    public LandLord(String username, String email, String phoneNumber, Role role) {
        super(username, email, phoneNumber, Role.LANDLORD);
    }

    public LandLord(String username, String password) {
        super(username, password);
    }
}
