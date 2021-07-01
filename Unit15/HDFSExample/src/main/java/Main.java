
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        //docker-compose up -V

        //docker pull harisekhon/hadoop
        //docker run -ti -p 8020:8020 -p 8032:8032 -p 8088:8088 -p 9000:9000 -p 10020:10020 -p 19888:19888 -p 50010:50010 -p 50020:50020 -p 50070:50070 -p 50075:50075 -p 50090:50090 harisekhon/hadoop

        String pathToFile = "test/file.txt";
        String pathToText = "D:/IdeaProjects/skillbox/java_basics/15_BigData/HDFSExample/src/main/res/text.txt";
        String rootPath = "hdfs://51ddae836fd1:8020/";
        String pathForBrowse = "test";
        try (FileAccess fileAccess = new FileAccess(rootPath)) {
            fileAccess.delete("/test/result/");
            fileAccess.create("test/task/");
            fileAccess.create("task");
            fileAccess.delete("task");

            if (fileAccess.exists(pathToFile)) {
                fileAccess.delete(pathToFile);
            }
            //fileAccess.createRandomFile(pathToFile);
            fileAccess.createTextFile(pathToFile, pathToText);
            System.out.println(fileAccess.read(pathToFile));
            fileAccess.append(pathToFile, "123456789");

            List<String> list = fileAccess.list(pathForBrowse);
            if (!list.isEmpty()){
                System.out.printf("ls for \"%s\" directory:%n", pathForBrowse);
                for (String s : list) {
                    System.out.println(s);
                }
            } else {
                System.out.printf("Directory %s is empty%n", pathForBrowse);
            }

            fileAccess.append("test/file.txt", " test for append");

            System.out.println(fileAccess.read(pathToFile));
        }
    }
}
