package edu.hw3.Task5;

public record Contact(String firstName, String lastName) {

    public static Contact create(String fullName) {
        var name = fullName.split(" ");
        if (name.length != 2) {
            return new Contact(fullName, null);
        }
        return new Contact(name[0], name[1]);
    }

}
