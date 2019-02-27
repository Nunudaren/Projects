package cn.net.io.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author:chendong
 * @Date:2018/12/12
 */
public class ReadFileByApacheFileUtils {
    public static void main(String[] args) throws IOException {

        String fileName = "/Users/chendongdong/Documents/RSA签名";
        List<String> fileContent = FileUtils.readLines(new File(fileName), StandardCharsets.UTF_8);

        for (String line : fileContent) {
            System.out.println(line);
        }
    }
}
