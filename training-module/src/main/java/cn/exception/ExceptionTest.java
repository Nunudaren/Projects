package cn.exception;

import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.framework.httpclient.HttpClientException;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * @Author:chendong
 * @Date:2018/9/5
 */
public class ExceptionTest {

//    private static final Logger log = LoggerFactory.getLogger(ExceptionTest.class);

    @Test
    public void catchHttpClientException() {
        try {
            cjjClientExceptionTest();
        } catch (CjjClientException e) {
            System.out.println(String.format("I catch a cjj client exception! e:%s, cause:%s", e.getMessage(), e.getCause()));
        } catch (HttpClientException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
//                System.out.println("I catch a socket timeout exception!");
                String loginfo = String.format("I catch a socket exception! e:%s, message:%s, cause:%s, stackTrace:%s", e, e.getMessage(), e.getCause(), e.getStackTrace());
                System.out.println(loginfo);
                return;
            }
            System.out.println("I can't catch a socket timeout exception!");
        } catch (Exception ex) {
            if (ex.getCause() instanceof IOException) {
                System.out.println("I catch a IO exception!");
                return;
            }
            String loginfo = String.format("I catch an unknown exception! e:%s, message:%s, cause:%s, stackTrace:%s", ex, ex.getMessage(), ex.getCause(), ex.getStackTrace());
            System.out.println(loginfo);
        }
    }

    private void httpClientExceptionTest() throws IOException {
        String exceptionMessage = null;
        try {
            System.out.println("I throw a socket timeout exception!");
            throw new SocketTimeoutException("read time out!");
//            throw new IOException();
        } catch (Exception e) {
            System.out.println("I catch a IO exception");
            exceptionMessage = e.getMessage();
            throw new HttpClientException("Http client call error.uri:" + exceptionMessage, e);
        }
    }

    public void cjjClientExceptionTest() {
        throw new CjjClientException(500, "cjj client exception!");
    }
}
