package catering.myOwnTestingPurpose;

import catering.businesslogic.user.User;

import java.util.HashSet;
import java.util.Set;

// This class is used to test the equals method of the User class
public class UserCheck {
    public static void main(String[] args) {
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.CHEF);
        roles.add(User.Role.CUOCO);
        User user = new User(1, roles, "CHEF_1");
        Set<User.Role> roles2 = new HashSet<>();
        roles2.add(User.Role.CHEF);
        roles2.add(User.Role.CUOCO);
        User user2 = new User(1, roles, "CHEF_1");

        System.out.println(user.equals(user2));
        System.out.println(user.equals(user));
        System.out.println(user2.equals(user2));
    }
}
