package test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

public class PathDebugTool {
    public static void main(String[] args) {
        System.out.println("=== PATH DEBUG TOOL ===\n");
        
        // 1. Check system properties
        System.out.println("1. SYSTEM PROPERTIES:");
        System.out.println("   Working directory: " + System.getProperty("user.dir"));
        System.out.println("   Java class path: " + System.getProperty("java.class.path"));
        System.out.println("   Java home: " + System.getProperty("java.home"));
        System.out.println();
        
        // 2. Check where the class is loaded from
        System.out.println("2. CLASS LOCATION:");
        try {
            URL classLocation = PathDebugTool.class.getProtectionDomain().getCodeSource().getLocation();
            System.out.println("   This class loaded from: " + classLocation);
        } catch (Exception e) {
            System.out.println("   Could not determine class location: " + e.getMessage());
        }
        System.out.println();
        
        // 3. Check all available resources in root
        System.out.println("3. AVAILABLE RESOURCES:");
        try {
            ClassLoader classLoader = PathDebugTool.class.getClassLoader();
            Enumeration<URL> resources = classLoader.getResources("");
            System.out.println("   Root resource directories:");
            while (resources.hasMoreElements()) {
                System.out.println("     " + resources.nextElement());
            }
        } catch (Exception e) {
            System.out.println("   Error getting resources: " + e.getMessage());
        }
        System.out.println();
        
        // 4. Test specific file paths
        System.out.println("4. JSON FILE SEARCH:");
        String[] pathsToTest = {
            "test/SkillDatabase.json",
            "SkillDatabase.json",
            "/test/SkillDatabase.json",
            "/SkillDatabase.json",
            "src/main/resources/test/SkillDatabase.json",
            "demo/src/main/resources/test/SkillDatabase.json"
        };
        
        for (String path : pathsToTest) {
            System.out.println("   Testing: " + path);
            
            // Try as resource
            InputStream is = PathDebugTool.class.getClassLoader().getResourceAsStream(path);
            if (is != null) {
                System.out.println("     ✓ Found as resource!");
                try {
                    is.close();
                } catch (Exception e) {}
            } else {
                System.out.println("     ✗ Not found as resource");
            }
            
            // Try as URL
            URL url = PathDebugTool.class.getClassLoader().getResource(path);
            if (url != null) {
                System.out.println("     ✓ URL: " + url);
            }
            
            // Try as file
            File file = new File(path);
            if (file.exists()) {
                System.out.println("     ✓ Found as file: " + file.getAbsolutePath());
            }
            
            System.out.println();
        }
        
        // 5. Check target/classes directory
        System.out.println("5. TARGET/CLASSES DIRECTORY:");
        File targetClasses = new File("target/classes");
        if (targetClasses.exists()) {
            System.out.println("   target/classes exists: " + targetClasses.getAbsolutePath());
            
            File testDir = new File("target/classes/test");
            if (testDir.exists()) {
                System.out.println("   target/classes/test exists");
                File[] files = testDir.listFiles();
                if (files != null) {
                    System.out.println("   Files in target/classes/test:");
                    for (File f : files) {
                        System.out.println("     " + f.getName());
                    }
                }
            } else {
                System.out.println("   target/classes/test does NOT exist");
            }
        } else {
            System.out.println("   target/classes does NOT exist");
        }
        System.out.println();
        
        // 6. Check src/main/resources directory
        System.out.println("6. SRC/MAIN/RESOURCES DIRECTORY:");
        File srcResources = new File("src/main/resources");
        if (srcResources.exists()) {
            System.out.println("   src/main/resources exists: " + srcResources.getAbsolutePath());
            
            File testDir = new File("src/main/resources/test");
            if (testDir.exists()) {
                System.out.println("   src/main/resources/test exists");
                File[] files = testDir.listFiles();
                if (files != null) {
                    System.out.println("   Files in src/main/resources/test:");
                    for (File f : files) {
                        System.out.println("     " + f.getName());
                    }
                }
            } else {
                System.out.println("   src/main/resources/test does NOT exist");
            }
        } else {
            System.out.println("   src/main/resources does NOT exist");
        }
    }
}