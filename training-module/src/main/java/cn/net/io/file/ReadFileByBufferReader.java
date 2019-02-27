package cn.net.io.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Author:chendong
 * @Date:2018/12/12
 */
public class ReadFileByBufferReader {

    public static void main(String[] args){

        String fileName = "/Users/chendongdong/smile_1.txt";

        try(BufferedReader bufferReader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder fileContent = new StringBuilder();
            String line = bufferReader.readLine();

            while (line != null) {
                fileContent.append(line);
                fileContent.append(System.lineSeparator());
                line = bufferReader.readLine();
            }

            String fileInformation = fileContent.toString();
            System.out.println(fileInformation);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
