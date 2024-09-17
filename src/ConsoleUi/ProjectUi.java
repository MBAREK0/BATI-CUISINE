package ConsoleUi;

public class ProjectUi {



    public void Ui() {
        String blue = "\033[0;34m";
        String reset = "\033[0m";

        System.out.println();
        System.out.println(blue + "========================================================================================================================"+ reset);
        System.out.println(blue +"|"+ reset + "  1. Create Project  |  2. Update Project  |  3. Delete Project  |  4. Show Project  |  5.List Projects   |   0. <--  "+blue + "|" + reset);
        System.out.println(blue + "========================================================================================================================"+ reset);
        System.out.println();

        }

}

