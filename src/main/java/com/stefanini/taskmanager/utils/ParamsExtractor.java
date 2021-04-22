package com.stefanini.taskmanager.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class ParamsExtractor {
    public static final String CREATE_USER = "createUser";
    public static final String SHOW_USERS = "showAllUsers";
    public static final String CREATE_TASK = "addTask";
    public static final String SHOW_TASKS_BY_USERNAME = "showTasks";
    public static final String SHOW_ALL_TASKS = "showAllTasks";
    public static final String COMPLETE_TASK = "completeTask";

    public static String USERNAME_FLAG;
    public static String FIRSTNAME_FLAG;
    public static String LASTNAME_FLAG;
    public static String TASK_TITLE_FLAG;
    public static String TASK_DESCRIPTION_FLAG;

    static {
        setCommandFlags();
    }

    public static void setCommandFlags() {
        Properties properties = AppConfig.getProperties();

        USERNAME_FLAG = properties.getProperty("username.flag");
        FIRSTNAME_FLAG = properties.getProperty("firstname.flag");
        LASTNAME_FLAG = properties.getProperty("lastname.flag");
        TASK_TITLE_FLAG = properties.getProperty("tasktitle.flag");
        TASK_DESCRIPTION_FLAG = properties.getProperty("taskdescription.flag");
    }

    public static String getOperationName(String arg) throws Exception {
        String task = arg.replace("-", "");
        List<String> tasks = Arrays.asList(
                CREATE_USER,
                SHOW_USERS,
                CREATE_TASK,
                SHOW_TASKS_BY_USERNAME,
                SHOW_ALL_TASKS,
                COMPLETE_TASK
        );

        if (!tasks.contains(task)) {
            throw new Exception("Error: Wrong operation name");
        }

        return task;
    }

    public static String getParamFromArg(String[] args, String flag) {
        String param = uniteArgs(args);
        param = param.substring(param.indexOf(flag + "='") + 4);
        param = param.substring(0, param.indexOf("'"));

        return param;
    }

    public static String uniteArgs(String[] args) {
        StringBuilder unitedArgs = new StringBuilder();

        for (String arg : args) {
            unitedArgs
                    .append(arg)
                    .append(" ");
        }

        return unitedArgs.toString();
    }
}
