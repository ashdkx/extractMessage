import java.lang.reflect.Array;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AccessDir {
    private ArrayList dirNames = new ArrayList();

    public AccessDir (String input) {
        Path dir = Paths.get(input);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                dirNames.add(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    /*
    Go into a specific file in the directory and return the contents of "message_1.json"
     */
    public extractMessage getContent(Path dir) {
        extractMessage eM = null;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                String childDirName = file.toString().replaceAll(dir.toString(), "");
                if (childDirName.equals("\\" + "message_1.json")) {
                    eM = new extractMessage(file.toString());
                }
                ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (eM == null) {
            System.err.println("Invalid Directory");
        }
        return eM;
    }

    public String getDirName (int dirIdx) {
        String dirName = dirNames.get(1).toString();
        return dirName;
    }

    public boolean containsDir (String dirName) {
        return dirName.contains(dirName);
    }

    public ArrayList firstTen() {
        ArrayList ten = new ArrayList();
        for (int i = 0; i < 10; i++) {
            ten.add(dirNames.get(i));
        }
        return ten;
    }

    public ArrayList getDir () {
        return dirNames;
    }
}
