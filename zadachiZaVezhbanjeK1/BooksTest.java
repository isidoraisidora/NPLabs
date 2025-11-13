    package NP.zadachiZaVezhbanjeK1;

    import java.util.*;
    import java.util.stream.Collectors;
    import java.util.stream.Stream;

    class Book{
        private String title;
        private String category;
        private float price;

        public Book(String title, String category, float price) {
            this.title = title;
            this.category = category;
            this.price = price;
        }

        public String getCategory() {
            return category;
        }

        public String getTitle() {
            return title;
        }

        public float getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return String.format("%s (%s) %.2f", title, category, price);
        }

    }

    class BookCollection{
        private List<Book> books;

        public BookCollection() {
            this.books = new ArrayList<>();
        }

        public void addBook(Book book){
            books.add(book);
        }

        public void printByCategory(String category){
            List<Book> filteredBooks = books.stream().filter(s-> s.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
            filteredBooks = filteredBooks.stream().sorted(Comparator.comparing(Book::getTitle).thenComparing(Book::getPrice)).collect(Collectors.toList());
            filteredBooks.forEach(System.out::println);
        }

        public List<Book> getCheapestN(int n){
            books = books.stream().sorted(Comparator.comparing(Book::getPrice).thenComparing(Book::getTitle)).collect(Collectors.toList());
            if(n>books.size())
                return books;

            return books.subList(0, n);
        }
    }

    public class BooksTest {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            scanner.nextLine();
            BookCollection booksCollection = new BookCollection();
            Set<String> categories = fillCollection(scanner, booksCollection);
            System.out.println("=== PRINT BY CATEGORY ===");
            for (String category : categories) {
                System.out.println("CATEGORY: " + category);
                booksCollection.printByCategory(category);
            }
            System.out.println("=== TOP N BY PRICE ===");
            print(booksCollection.getCheapestN(n));
        }

        static void print(List<Book> books) {
            for (Book book : books) {
                System.out.println(book);
            }
        }

        static TreeSet<String> fillCollection(Scanner scanner,
                                              BookCollection collection) {
            TreeSet<String> categories = new TreeSet<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if(line.isEmpty()) continue; // skip empty lines

                String[] parts = line.split(":");
                if(parts.length != 3) {
                    System.out.println("Invalid input line (expected format title:category:price): " + line);
                    continue;
                }

                Book book = new Book(parts[0].trim(), parts[1].trim(), Float.parseFloat(parts[2].trim()));
                collection.addBook(book);
                categories.add(parts[1].trim());
            }
            return categories;
        }

    }

    // Вашиот код овде
