package cn.openapi.demo;

import cn.openapi.KeyManager;

/**
 * @author winter
 * @date 2019-01-28
 */
public class KeyManagerConfig implements KeyManager {

    @Override
    public String getShuheEncryptKeyVersion() {
        return "10";
    }

    @Override
    public String getShuheEncryptKey() {
        return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGmEGPa83yRw/or/sThGHHPBJBd8sFQMrJAUTRog6xjjk+OKStA9/Atij8QyCgUYEdctIbDZrQD5ZA6g4WyXpeO016O7h00Yd47bjHvKgScIjpCm1F2OQawSCEBPF8AGCS2IXXYiKwsgWQRcyz1uquovjhl4fNgpPqxxKAZ5xNcwIDAQAB";
    }

    @Override
    public String getMerchantKeyVersion() {
        return "1";
    }

    @Override
    public String getMerchantPrivateKey() {
        return "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK15pIrvYdYj7Zb+2dCSGrlHffjW8InvWDClAfFFy29xyewocKJyjSb05iDsxab3P+E4jk0Gkb+nYotGZzDg8rM5Ts+/w981lOMoDmjTUnGvovWO2/M62wYpniAGVEjJoReSs/NAAZohtwHboADR8OzEB+SpiV86rG9Iz8VWKnSlAgMBAAECgYEAgWMWP+42CdM4VYP2D+O5fPH+2kfuluLm4zlfjoZhKtkzIyoygGdyMlk8/9e5wEPv6FsXgAnjUZBWJs03EDTnr1zvxQwJFDZ6L+DhUu9LqrE1/AHKRRXdmiKP2CuQlF1RnVsGF7KkUWCnPTqQNth4uuWFv/QB45GpVo0ZyxY8PuECQQDbs8oX7KnJ6KRUSfVsJqD5M78vDMxQU85xsrJp0S73VLNcxHEIjP2SEYaHhXBhxeYQSFRYyVEaJwXlFZdIQdbJAkEAyiKygr6ueHRrjtAwN4O/glC/FRFJw4iYVA/YCNhXkD8wra6YTE9b9H8BLUnao/y7iVF6+EdkYYf2CalDQnew/QJAacbA+Q3PnU9ttss45w4eGH0KghuWTuJalbEkZv/4rcnQ5UwK48QF3Yqki6D6P4ipQJOiUHD0pDO/idEAWiCgyQJATV6cj1jnNSnypXZgjk86dWnsyeaVImfdTofF/BS653aYblJe7w6NNveJ/si/DHfHXJc4h2HkNUSyUx5bpUxlDQJAIHX8s0UIcfct98jyI8QQ9g9WCZMCDWGIQhldY+ZuQzBMCBMSlNDoWxxE8Tjbos/oUsSVF1rWF6HM4agqxfu1Sw==";
    }

}
