package cn.caijiajia.credittools.service;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author:chendongdong
 * @Date:2018/5/14
 */
@Service
public class ImageService {

    @Autowired
    private FileService fileService;

    public void getImage(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter("token");
        String bucketName = request.getParameter("bucketName");
        InputStream inputStream = fileService.s3DownloanFile(token, bucketName);
        OutputStream outputStream = null;
        try {
            response.setContentType("image/png");
            outputStream = response.getOutputStream();
            byte[] data = IOUtils.toByteArray(inputStream);
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
