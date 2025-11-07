package NP.zadachiZaVezhbanjeK1;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class DuplicateNumberException extends Exception{
    public DuplicateNumberException() {
    }
}

class Contact{
    private final String name;
    private final String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " " + number;
    }
}

class PhoneBook{
    private final List<Contact> contactList;

    public PhoneBook() {
        this.contactList = new ArrayList<>();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        Contact c = new Contact(name, number);
        if(contactList.contains(c)){
            throw new DuplicateNumberException();
        }
        contactList.add(c);
    }
    public void checkEmpty(List<Contact> list){
        if(list.isEmpty()){
            System.out.println("NOT FOUND");
        }
    }

    public void contactsByNumber(String number){
        List<Contact> c;
        c = contactList.stream()
                .filter(r->r.getNumber().contains(number))
                .collect(Collectors.toList());
        checkEmpty(c);
        c = c.stream()
                .sorted(Comparator.comparing(Contact::getName)
                        .thenComparing(Contact::getNumber))
                .collect(Collectors.toList());
        for(Contact contact : c){
            System.out.println(contact.toString());
        }
    }

    public void contactsByName(String name){
        List<Contact> c;
        c = contactList.stream()
                .filter(r->r.getName().contains(name))
                .collect(Collectors.toList());
        checkEmpty(c);
        c = c.stream()
                .sorted(Comparator.comparing(Contact::getName)
                        .thenComparing(Contact::getNumber))
                .collect(Collectors.toList());
        for(Contact contact : c){
            System.out.println(contact.toString());
        }
    }
}
public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}

// Вашиот код овде

