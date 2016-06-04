package service;

import entities.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserServiceTest {
    @Test
    public void testFindUsersWithAddressesCountMoreThan() throws Exception {
        List<Address> adresses = new ArrayList<>();
        adresses.add(new Address(0, null, null, null, null, null));
        adresses.add(new Address(0, null, null, null, null, null));

        User user = new User("Imie1", null, new Person("Nazwisko1", "Imie1", null, adresses, null, 0));

        List<Address> adresses2 = new ArrayList<>();
        adresses2.add(new Address(0, null, null, null, null, null));

        User user2 = new User("Imie2", null, new Person("Nazwisko2", "Imie2", null, adresses2, null, 0));

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        UserService us = new UserService(users);

        List<User> usersWithAddressesCountMoreThan = us.findUsersWithAddressesCountMoreThan(1);
        Assert.assertEquals(1, usersWithAddressesCountMoreThan.size());
        Assert.assertEquals(user.getName(), usersWithAddressesCountMoreThan.get(0).getName());
    }

    @Test
    public void testFindOldestPerson() throws Exception {
        User user = new User("Imie1", null, new Person("Nazwisko1", "Imie1", null, null, null, 21));
        User user2 = new User("Imie1", null, new Person("Nazwisko1", "Imie1", null, null, null, 32));
        User user3 = new User("Imie1", null, new Person("Nazwisko1", "Imie1", null, null, null, 14));

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        UserService us = new UserService(users);

        Assert.assertEquals(user2.getPersonDetails().getAge(), us.findOldestPerson());
    }

    @Test
    public void testFindUserWithLongestUsername() throws Exception {
        User user = new User("Imie1", null, null);
        User user2 = new User("Imie123", null, null);
        User user3 = new User("Imie12", null, null);

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        UserService us = new UserService(users);

        Assert.assertEquals(user2.getName(), us.findUserWithLongestUsername().getName());
    }

    @Test
    public void testGetNamesAndSurnamesCommaSeparatedOfAllUsersAbove18() throws Exception {
        User user = new User("Imie1", null, new Person("Nazwisko1", "Imie1", null, null, null, 21));
        User user2 = new User("Imie2", null, new Person("Nazwisko1", "Imie1", null, null, null, 12));
        User user3 = new User("Imie3", null, new Person("Nazwisko1", "Imie1", null, null, null, 42));

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        UserService us = new UserService(users);

        String[] expectedNames = {
                user.getName() + " " + user.getPersonDetails().getSurname(),
                user3.getName() + " " + user3.getPersonDetails().getSurname(),

        };

        String namesAndSurnamesCommaSeparatedOfAllUsersAbove18 = us.getNamesAndSurnamesCommaSeparatedOfAllUsersAbove18();
        String[] names = namesAndSurnamesCommaSeparatedOfAllUsersAbove18.split(",");

        Assert.assertArrayEquals(expectedNames, names);
    }

    @Test
    public void testGetSortedPermissionsOfUsersWithNameStartingWith() throws Exception {

        List<Permission> permissions = new ArrayList<>();
        Permission perm1 = new Permission("Perm1");
        permissions.add(perm1);
        Permission perm3 = new Permission("Perm3");
        permissions.add(perm3);

        List<Permission> permissions2 = new ArrayList<>();
        Permission perm2 = new Permission("Perm2");
        permissions2.add(perm2);

        List<String> expectedSortedPermissions = new ArrayList<>();
        expectedSortedPermissions.add(perm1.getName());
        expectedSortedPermissions.add(perm2.getName());
        expectedSortedPermissions.add(perm3.getName());

        User user = new User("Imie1", null, new Person("Nazwisko1", "Imie1", null, null, new Role(null, permissions), 21));
        User user2 = new User("Imie2", null, new Person("Nazwisko1", "Imie1", null, null, new Role(null, permissions2), 12));
        User user3 = new User("Inne", null, new Person("Nazwisko1", "Imie1", null, null, null, 42));

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        UserService us = new UserService(users);
        List<String> sortedPermissions = us.getSortedPermissionsOfUsersWithNameStartingWith("Imie");

        Assert.assertEquals(expectedSortedPermissions, sortedPermissions);
    }

    @Test
    public void testGetUsersAverageAge() throws Exception {
        User user = new User("Imie1", null, new Person("Nazwisko1", "Imie1", null, null, null, 21));
        User user2 = new User("Imie2", null, new Person("Nazwisko1", "Imie1", null, null, null, 12));

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        UserService us = new UserService(users);

        Assert.assertEquals(0, Double.compare((user.getPersonDetails().getAge() + user2.getPersonDetails().getAge()) / 2.0, us.getUsersAverageAge()));
    }

    @Test
    public void testPrintCapitalizedPermissionNamesOfUsersWithSurnameStartingWith() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        List<Permission> permissions = new ArrayList<>();
        Permission perm1 = new Permission("Perm1");
        permissions.add(perm1);
        Permission perm3 = new Permission("Perm3");
        permissions.add(perm3);

        List<Permission> permissions2 = new ArrayList<>();
        Permission perm2 = new Permission("Perm2");
        permissions2.add(perm2);

        List<Permission> permissions3 = new ArrayList<>();
        permissions3.add(new Permission("Perm4"));

        String[] expectedPermissionNames = {"PERM1", "PERM2", "PERM3"};

        User user = new User("Imie1", null, new Person("Nazwisko1", "Imie1", null, null, new Role(null, permissions), 21));
        User user2 = new User("Imie2", null, new Person("Nazwisko1", "Imie1", null, null, new Role(null, permissions2), 12));
        User user3 = new User("Imie3", null, new Person("Inne", "Imie1", null, null, new Role(null, permissions3), 42));

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        UserService us = new UserService(users);

        us.printCapitalizedPermissionNamesOfUsersWithSurnameStartingWith("Nazwisko");

        String[] permissionNames = out.toString().split("\n");
        Arrays.sort(permissionNames);

        Assert.assertArrayEquals(expectedPermissionNames, permissionNames);
    }

    @Test
    public void testGroupUsersByRole() throws Exception {
        Role role1 = new Role("role1", null);
        Role role2 = new Role("role2", null);

        User user = new User("Imie1", null, new Person("Nazwisko1", "Imie1", null, null, role1, 21));
        User user2 = new User("Imie2", null, new Person("Nazwisko1", "Imie1", null, null, role1, 21));
        User user3 = new User("Imie3", null, new Person("Nazwisko1", "Imie1", null, null, role2, 12));
        
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        UserService us = new UserService(users);

        Map<Role, List<User>> roleListMap = us.groupUsersByRole();

        Assert.assertEquals(2, roleListMap.get(role1).size());
        Assert.assertEquals(1, roleListMap.get(role2).size());

    }

    @Test
    public void testPartitionUserByUnderAndOver18() throws Exception {
        User user = new User("Imie1", null, new Person("Nazwisko1", "Imie1", null, null, null, 11));
        User user2 = new User("Imie2", null, new Person("Nazwisko1", "Imie1", null, null, null, 21));
        User user3 = new User("Imie3", null, new Person("Nazwisko1", "Imie1", null, null, null, 22));

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        UserService us = new UserService(users);
        Map<Boolean, List<User>> booleanListMap = us.partitionUserByUnderAndOver18();

        Assert.assertEquals(2, booleanListMap.get(true).size());
        Assert.assertEquals(1, booleanListMap.get(false).size());
    }
}
