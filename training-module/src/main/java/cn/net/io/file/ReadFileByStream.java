package cn.net.io.file;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * @Author:chendong
 * @Date:2018/12/12
 */
public class ReadFileByStream {

    public static void main(String[] args) {

        String fileName = "/Users/chendongdong/query_result.csv"; //this path is on my local
        try(FileInputStream inputStream = new FileInputStream(fileName)){

            String fileContent = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            System.out.println(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个文件路径:");
        while (true) {
            String line = sc.nextLine();
            File file = new File(line);
            if (!file.exists()) {
                System.out.println("您输入的文件路径不存在,请重新输入:");
            } else if (file.isDirectory()) {
                System.out.println("您输入的是文件夹路径,请输入一个文件路径");
            } else {
                return file;
            }
        }
    }

    @Test
    public void readFiletoList() throws IOException {
        File file = new File("/Users/chendongdong/smile.txt");
        System.out.println(file.exists());

//        Files.lines(file.toPath())
//                .map(s -> s.trim())
//                .filter(s -> !s.isEmpty())
//                .forEach(System.out :: println);

        List<String> lines = Files.readAllLines(Paths.get("/Users/chendongdong/smile.txt"));
        System.out.println(lines);
    }

}
