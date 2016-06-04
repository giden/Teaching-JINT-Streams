package service;

import entities.Permission;
import entities.Person;
import entities.Role;
import entities.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class UserService implements UserServiceInterface {

    private List<User> users;

    public UserService(List<User> users) {
        this.users = users;
    }

    public List<User> findUsersWithAddressesCountMoreThan(final int numberOfAddresses) {
        return users.stream()
                .filter(u -> u.getPersonDetails().getAddresses().size() > numberOfAddresses)
                .collect(Collectors.toList());
    }

    public Person findOldestPerson() {
        return users.stream()
                .map(User::getPersonDetails)
                .max((p1,p2) -> p1.getAge() - p2.getAge())
                .get();
    }

    public User findUserWithLongestUsername() {
        return users.stream()
                .max((u1,u2) -> u1.getName().length() - u2.getName().length())
                .get();
    }

    public String getNamesAndSurnamesCommaSeparatedOfAllUsersAbove18() {
        return users.stream()
                .filter(u -> u.getPersonDetails().getAge() > 18)
                .map(u -> u.getName() + " " + u.getPersonDetails().getSurname())
                .collect(Collectors.joining(","));
    }
    public List<String> getSortedPermissionsOfUsersWithNameStartingWith(String prefix) {
        return users.stream()
                .filter(u -> u.getName().startsWith(prefix))
                .map(u -> u.getPersonDetails().getRole().getPermissions())
                .flatMap(Collection::stream)
                .map(Permission::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    public double getUsersAverageAge() {
        return users.stream()
                .mapToDouble(u -> u.getPersonDetails().getAge())
                .average()
                .getAsDouble();
    }

    public void printCapitalizedPermissionNamesOfUsersWithSurnameStartingWith(String suffix) {
        users.stream()
                .filter(u -> u.getPersonDetails().getSurname().startsWith(suffix))
                .map(u -> u.getPersonDetails().getRole().getPermissions())
                .flatMap(Collection::stream)
                .map(u -> u.getName().toUpperCase())
                .forEach(System.out::println);
    }

    public Map<Role, List<User>> groupUsersByRole() {
        return users.stream()
                .collect(Collectors.groupingBy(u -> u.getPersonDetails().getRole()));
    }

    public Map<Boolean, List<User>> partitionUserByUnderAndOver18() {
        return users.stream()
                .collect(Collectors.groupingBy(u -> u.getPersonDetails().getAge()>18));
    }
}
