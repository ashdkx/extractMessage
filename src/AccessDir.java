import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AccessDir {
    private ArrayList dirName = new ArrayList();

    public AccessDir (String input) {
        Path dir = Paths.get(input);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                dirName.add(file);
                System.out.println(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    public String getDirName (int dirIdx) {
        return (String) dirName.get(dirIdx);
    }

    public boolean containsDir (String dirName) {
        return dirName.contains(dirName);
    }

    public ArrayList getDir () {
        return dirName;
    }
}
