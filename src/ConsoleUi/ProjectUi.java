package ConsoleUi;

public class ProjectUi {



    public void Ui() {
        String blue = "\033[0;34m";
        String reset = "\033[0m";

        System.out.println(blue + "============================================================================================================================="+ reset);
        System.out.println(blue +"|"+ reset + "  1. Create Project  |  2. Update Project  |  3. Delete Project  |  4. List Projects  |  5.Project Components  |   0. <--  "+blue + "|" + reset);
        System.out.println(blue + "============================================================================================================================="+ reset);
        }

}

