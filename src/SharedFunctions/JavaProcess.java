package SharedFunctions;

import java.io.File;
import java.io.IOException;

/**
 * This class allows a java class to be run as a separate process by supplying a class containing the main() function as a parameter
 *
 * Credit to the user hallidave of stackoverflow.com for this code
 * http://stackoverflow.com/questions/636367/executing-a-java-application-in-a-separate-process/723914#723914
 *
 * class creation date: 4/27/2016
 *
 * @author hallidave, Zack
 * @version 2016.4.27
 */
public class JavaProcess {

    /**
     * default constructor is empty
     */
    private JavaProcess() {}

    /**
     * This function takes the supplied class and runs it as a new process and wait for it to finish
     * @param klass The class that will be run in the new process
     * @return exitValue The exit value the newly made process
     * @throws IOException
     * @throws InterruptedException
     */
    public static int exec(Class klass) throws IOException,
            InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = klass.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-cp", classpath, className);

        Process process = builder.start();
        process.waitFor();
        return process.exitValue();
    }
}
