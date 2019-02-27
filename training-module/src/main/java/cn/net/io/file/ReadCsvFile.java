package cn.net.io.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:chendong
 * @Date:2018/12/12
 */
public class ReadCsvFile {
    public static void main(String[] args) {
        File csv = new File("/Users/chendongdong/cb_avaya.csv");  // CSV文件路径
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = "";
        String everyLine = "";
        List<String> allString = new ArrayList<>();
        try {
            while ((line = br.readLine()) != null)  //读取到的内容给line变量
            {
                everyLine = line;
//                System.out.println(everyLine);
                allString.add(everyLine);
            }
            System.out.println("csv表格中所有行数：" + allString.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String s: allString) {
            System.out.println(s);
        }

    }
}
