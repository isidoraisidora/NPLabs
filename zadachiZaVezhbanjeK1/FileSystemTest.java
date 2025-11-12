package NP.zadachiZaVezhbanjeK1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class FileNameExistsException extends Exception{
    public FileNameExistsException(String file, String folder) {
        super(String.format("There is already a file named %s in the folder %s", file, folder));
    }
}

interface IFile{
    String getFileName();
    long getFileSize();
    String getFileInfo();
    void sortBySize();
    long findLargestFile();
}

class File implements IFile{
    private final String name;
    private final long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo() {
        return String.format("File: name %10s File size: %10d",
                name, size );
    }

    @Override
    public void sortBySize() {
    }

    @Override
    public long findLargestFile() {
        return size;
    }
}

class Folder implements IFile{
    private final String name;
    private long size;
    private final List<IFile> allTypesFiles;
    private boolean sorted;

    public Folder(String name) {
        this.name = name;
        this.size = 0;
        this.allTypesFiles = new ArrayList<>();
        this.sorted = false;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return allTypesFiles.stream().mapToLong(IFile::getFileSize).sum();
    }

    @Override
    public String getFileInfo() {
        List<IFile> sortedContents = new ArrayList<>();
        if(sorted){
            sortedContents = allTypesFiles.stream()
                    .sorted(Comparator.comparing(IFile::getFileSize))
                    .collect(Collectors.toList());
        }else{
            sortedContents = allTypesFiles.stream()
                    .sorted(Comparator.comparing(IFile::getFileName))
                    .collect(Collectors.toList());
        }
        StringBuilder sb = new StringBuilder();
        final String INDENT_SPACES = "    ";

        final String NL = System.lineSeparator();

        sb.append(String.format("Folder name: %10s Folder size: %10d",
                name, getFileSize()));

        for(IFile file : sortedContents){
            String childInfo = file.getFileInfo();

            sb.append(NL)
                    .append(INDENT_SPACES)
                    .append(childInfo.replaceAll(NL, NL + INDENT_SPACES));
        }

        return sb.toString();
    }


    @Override
    public void sortBySize() {
        sorted=true;
        allTypesFiles.stream().filter(f-> f instanceof Folder).forEach(IFile::sortBySize);
        //allTypesFiles.sort(Comparator.comparing(IFile::getFileSize));
    }

    @Override
    public long findLargestFile() {
        if(allTypesFiles.isEmpty()) return 0;
        return allTypesFiles.stream()
                .mapToLong(IFile::findLargestFile)
                .max().orElse(0);
    }

    public void addFile(IFile file) throws FileNameExistsException {
        boolean exist = allTypesFiles.stream()
                .anyMatch(f->f.getFileName().equals(file.getFileName()));
        if(exist) throw new FileNameExistsException(file.getFileName(), this.name);
        allTypesFiles.add(file);
    }
}

class FileSystem{
    private Folder rootDirectory;

    public FileSystem() {
        this.rootDirectory = new Folder("root");
    }

    public void addFile(IFile file) throws FileNameExistsException {
        rootDirectory.addFile(file);
    }

    long findLargestFile(){
        return rootDirectory.findLargestFile();
    }

    void sortBySize(){
        rootDirectory.sortBySize();
    }

    @Override
    public String toString() {
        return rootDirectory.getFileInfo();
    }
}

public class FileSystemTest {

    public static Folder readFolder (Scanner sc)  {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i=0;i<totalFiles;i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String [] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args)  {

        //file reading from input

        Scanner sc = new Scanner (System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());
        System.out.println();

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());
    }
}
