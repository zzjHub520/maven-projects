import java.io.File;

public class AA {
    public static void main(String[] args) {
        File file = new File(AA.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println("abc " + file);
        System.out.println("abc " + file.getParent());
        System.out.println("abc " + file.getParentFile().getParent());
        System.out.println("abc " + file);
    }
}
