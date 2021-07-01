import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileAccess implements AutoCloseable {
    private FileSystem hdfs;
    //private Path pathFile;

    private final URI uri;

    public void close() {
        try {
            hdfs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileSystem getHdfs() {
        return hdfs;
    }

    public void setHdfs(FileSystem hdfs) {
        this.hdfs = hdfs;
    }

    /*public Path getPathFile() {
        return pathFile;
    }

    public void setPathFile(Path pathFile) {
        this.pathFile = pathFile;
    }*/

    private String normalizePath(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(uri);
        stringBuilder.append(path);
        return stringBuilder.toString();
    }


    /**
     * Initializes the class, using rootPath as "/" directory
     *
     * @param rootPath - the path to the root of HDFS,
     *                 for example, hdfs://localhost:32771
     */
    public FileAccess(String rootPath) throws URISyntaxException, IOException {
        uri = new URI(rootPath);
        Configuration configuration = new Configuration();
        configuration.set("dfs.client.use.datanode.hostname", "true");
        System.setProperty("HADOOP_USER_NAME", "root");
        hdfs = FileSystem.get(uri, configuration);
        //pathFile = new Path(uri + rootPath);
    }

    /**
     * Creates empty file or directory
     *
     * @param path
     */
    public void create(String path) throws IOException {
        path = normalizePath(path);
        Path createPath = new Path(path);
        hdfs.setReplication(createPath, (short) 1);
        if (new File(path).isDirectory()) {
            hdfs.mkdirs(createPath);
        } else {
            hdfs.create(createPath);
        }
    }

    /**
     * Appends content to the file
     *
     * @param path
     * @param content
     */
    public void append(String path, String content) {
        path = normalizePath(path);
        Path writePath = new Path(path);
        /*FSDataOutputStream fsDataOutputStream;
        try {
            fsDataOutputStream = hdfs.append(writePath);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fsDataOutputStream, StandardCharsets.UTF_8));
            bufferedWriter.write(content);
            bufferedWriter.close();
            fsDataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try (FSDataOutputStream fs_append = hdfs.append(writePath);
             PrintWriter writer = new PrintWriter(fs_append)) {
            writer.append(content);
            writer.flush();
            fs_append.hflush();
            System.out.printf("Append string \"%s\"%n", content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns content of the file
     *
     * @param path
     * @return
     */
    public String read(String path) {
        path = normalizePath(path);
        Path hdfsReadPath = new Path(path);
        StringBuilder stringBuilder = new StringBuilder();
        /*try (FSDataInputStream inputStream = hdfs.open(hdfsReadPath)) {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            while ((bufferedReader.readLine()) != null) {
                stringBuilder.append(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();*/

        try (FSDataInputStream inputStream = hdfs.open(hdfsReadPath)) {
            String out = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            stringBuilder.append(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Classical input stream usage

        /*BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line = null;
        while ((line=bufferedReader.readLine())!=null){
            System.out.println(line);
        }*/
        return stringBuilder.toString();
    }

    /**
     * Deletes file or directory
     *
     * @param path
     */
    public void delete(String path) {
        path = normalizePath(path);
        Path deletePath = new Path(path);
        try {
            if (!hdfs.exists(deletePath)) {
                System.out.println("File " + path + " does not exists");
            } else {
                hdfs.delete(deletePath, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks, is the "path" is directory or file
     *
     * @param path
     * @return
     */
    public boolean isDirectory(String path) {
        path = normalizePath(path);
        Path checkPath = new Path(path);
        try {
            if (!hdfs.exists(checkPath)) {
                System.out.println("Dir " + path + " does not exists");
                return false;
            }
            return hdfs.isDirectory(checkPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Return the list of files and subdirectories on any directory
     *
     * @param path
     * @return
     */
    public List<String> list(String path) {
        String lPath = normalizePath(path);
        path = lPath.replace(path, "");
        Path listPath = new Path(lPath);
        List<String> fileList = new ArrayList<>();
        FileStatus[] fileStatus;
        try {
            fileStatus = hdfs.listStatus(listPath);
            for (FileStatus fileStat : fileStatus) {
                fileList.add(fileStat.getPath().toString().replace(path, ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public boolean exists(String path) {
        path = normalizePath(path);
        Path checkPath = new Path(path);
        try {
            if (hdfs.exists(checkPath)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*public List<String> browseFile(String path) {
        FileStatus[] status;
        path = normalizePath(path);
        if (!new File(path).isDirectory()) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                status = hdfs.listStatus(new Path(path));
                for (int i = 0; i < status.length; i++) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(hdfs.open(status[i].getPath())));
                    stringBuilder.append(br.readLine());
                    while (br.readLine() != null) {
                        stringBuilder.append(br.readLine());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<String> resultList = Arrays.asList(stringBuilder.toString().split("\n"));
            return resultList;
        } else {
            return null;
        }
    }*/

    public void createRandomFile(String path) {
        path = normalizePath(path);
        //System.out.println(path);
        Path createPath = new Path(path);
        StringBuilder stringBuilder = new StringBuilder();
        try (OutputStream os = hdfs.create(createPath);
             OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
             BufferedWriter br = new BufferedWriter(osw)) {

            hdfs.setReplication(createPath, (short) 1);
            for (int i = 0; i < 10_000; i++) {
                stringBuilder.append(getRandomWord())
                        .append(" ");
            }
            br.write(stringBuilder.toString());
            br.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getRandomWord() {
        String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        int length = 2 + (int) Math.round(10 * Math.random());
        int symbolsCount = symbols.length();
        for (int i = 0; i < length; i++) {
            builder.append(symbols.charAt((int) (symbolsCount * Math.random())));
        }
        return builder.toString();
    }

    public static List<String> readFileInList(String fileName) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public void createTextFile(String pathToResult, String pathToSource) {
        pathToResult = normalizePath(pathToResult);
        Path createPath = new Path(pathToResult);
        StringBuilder stringBuilder = new StringBuilder();
        try (OutputStream os = hdfs.create(createPath);
             OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
             BufferedWriter br = new BufferedWriter(osw)) {

            hdfs.setReplication(createPath, (short) 1);
            List<String> text = readFileInList(pathToSource);
            for (String string : text) {
                stringBuilder.append(string)
                        .append(" ");
            }
            br.write(stringBuilder.toString());
            br.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
