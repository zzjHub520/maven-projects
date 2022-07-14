import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GitBash {

    public static final String path_bash = "C:/Program Files/Git/git-bash.exe";

    // Create a file Output.txt where git-bash prints the results
    public static final String path_file_output_git_bash =
            "C:/Users/zzj/Documents/workspace/Java/test/gitbash-test1/Output.txt";

    public static void main(String[] args) throws IOException {
        runCommand("curl https://www.baidu.com"  + " > " + path_file_output_git_bash);
    }

    public static void runCommand(String command) throws IOException {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(path_bash, "-c", command);

            Process process = processBuilder.start();

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println(" --- Command run successfully");
                System.out.println(" --- Output = " + readFileTxt());

            } else {
                System.out.println(" --- Command run unsuccessfully");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(" --- Interruption in RunCommand: " + e);
            // Restore interrupted state
            Thread.currentThread().interrupt();
        }
    }

    public static String readFileTxt() {
        String data = null;
        try {
            File myObj = new File(path_file_output_git_bash);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(" --- An error occurred");
            e.printStackTrace();
        }
        return data;
    }
}