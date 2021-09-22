package duke;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;
import duke.Task.*;

/**
 * Class to handle storing and loading of file
 */

public class Storage {
    /**Path to current directory in which the app is run*/
    private java.nio.file.Path dirpath;
    /**Path to tasklist*/
    private Path taskListPath;


    /**<p>Constructor for the Storage class. Creates a directory named data and file named DukeTask.txt
     * if it does not exist.</p>
     *
     */
    Storage() {
        this.dirpath = Paths.get("data");
        this.taskListPath = Paths.get("data", "DukeTask.txt");
        boolean directoryExists = Files.exists(dirpath);
        boolean fileExist = Files.exists(taskListPath);
        if (!directoryExists) {
            try {
                Files.createDirectory(dirpath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if (!fileExist) {
            try {
                Files.createFile(taskListPath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**Write the specified task list in argument to the file DukeTask.txt
     *
     * @param tasks tasks to be written to the file
     */

    public void saveTasksToStorage(TaskList tasks) {
        try {
            String newFile = tasks.toString();
            this.writeStringToFile(newFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Write string to the path of the storage file.
     * @param newFile string to be added
     * @throws IOException if there is a failure in writing the file.
     */
    public void writeStringToFile(String newFile) throws IOException {
        assert taskListPath != null : "File path must exist";
        Files.write(taskListPath, newFile.getBytes());
    }

    /**
     * Loads data of tasks from the storage to a TaskList. Method will append all the data
     * to the supplied task list.
     *
     * @param tasks Task list that will be appended with data.
     */
    public void loadDataToTasks(TaskList tasks) {
        assert taskListPath != null : "File path must exist";
        try {
            File taskList = new File(taskListPath.toString());
            Scanner scanner = new Scanner(taskList);
            while (scanner.hasNext()) {
                Task currTask = null;
                String curr = scanner.nextLine();
                if (curr.equals("")) {
                    continue;
                }
                Character tasktype = curr.charAt(1);
                Character isDone = curr.charAt(4);
                String desc = curr.substring(6);
                desc = desc.trim();
                boolean done = isDone == 'X';
                if (tasktype == 'T') {
                    currTask = new Todo(desc, done);
                } else if (tasktype == 'E') {
                    int index = desc.indexOf("at:");
                    int n = desc.length();
                    String date = desc.substring(index + 4, n - 1);
                    currTask = new Event(desc.substring(0, index - 1), done, LocalDate.parse(date));
                } else if (tasktype == 'D') {
                    int index = desc.indexOf("by:");
                    int n = desc.length();
                    String date = desc.substring(index + 4, n - 1);

                    currTask = new Deadline(desc.substring(0, index), done, LocalDate.parse(date));
                }
                tasks.addTask(currTask);
        }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
