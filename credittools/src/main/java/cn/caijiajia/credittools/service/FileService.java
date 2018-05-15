package cn.caijiajia.credittools.service;

import cn.caijiajia.awsservice.S3Service;
import cn.caijiajia.s3util.mapper.S3BoStoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @Author:chendongdong
 * @Date:2018/5/14
 */
@Component
@Slf4j
public class FileService {

    @Autowired
    private S3BoStoreMapper s3BoStoreMapper;

    @Autowired
    private S3Service s3Service;

    /**
     * 上传Object到s3
     *
     * @param rawObject
     */
    public void s3UpdateLoad(Object rawObject) {
        s3BoStoreMapper.insert(rawObject);
    }

    /**
     * 从s3下载object
     *
     * @param targetClz
     * @param businessNo
     * @param <T>
     * @return
     */
    public <T> T s3Download(Class<T> targetClz, String businessNo) {
        return s3BoStoreMapper.get(targetClz, businessNo);
    }

    /**
     * 从s3下载文件
     *
     * @param keyName
     * @param bucketName
     * @return
     */
    public InputStream s3DownloanFile(String keyName, String bucketName) {
        InputStream inputStream = s3Service.downloadFile(bucketName, keyName);
        return inputStream;
    }
}
