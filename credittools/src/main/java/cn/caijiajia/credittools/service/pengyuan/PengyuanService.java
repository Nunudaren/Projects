package cn.caijiajia.credittools.service.pengyuan;

import cn.caijiajia.creditdata.common.resp.FaceVerifyDataResp;
import cn.caijiajia.creditdata.common.resp.PhotoSupplierDataResp;
import cn.caijiajia.creditdata.rpc.PhotoSupplierRpc;
import cn.caijiajia.credittools.bo.UnionJumpBo;
import cn.caijiajia.credittools.common.constant.ErrorResponseConstants;
import cn.caijiajia.credittools.common.req.PengyuanLoginReq;
import cn.caijiajia.credittools.configuration.Configs;
import cn.caijiajia.credittools.constant.UnionLoginChannelEnum;
import cn.caijiajia.credittools.service.IProductsService;
import cn.caijiajia.credittools.service.LoanProductMgrService;
import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.userloan.common.resp.UserResp;
import cn.caijiajia.userloan.rpc.UserInfoRpc;
import cn.caijiajia.utils.crypto.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:chendongdong
 * @Date:2018/5/9
 */
@Service
@Slf4j
public class PengyuanService implements IProductsService {

    @Autowired
    private Configs configs;
    @Autowired
    private PyBuildReqUrlService pyBuildReqUrlService;
    @Autowired
    private LoanProductMgrService loanProductMgrService;
    @Autowired
    private UserInfoRpc userInfoRpc;
    @Autowired
    private PhotoSupplierRpc photoSupplierRpc;

    @Override
    public UnionJumpBo unionLogin(String uid, String key) {

        String jumpUrl = loanProductMgrService.getUnionLoginUrl(key);
        String hostUrl = configs.getExternalUrl();
        PengyuanLoginReq pengyuanLoginReq = new PengyuanLoginReq();
        PengyuanLoginReq.ExtendInfo extendInfo = new PengyuanLoginReq.ExtendInfo();
        try {
            String photoUrl = null;
            UserResp userInfo = userInfoRpc.getUser(uid);
            if (userInfo == null) {
                throw new CjjClientException(ErrorResponseConstants.USER_NOT_EXISTS_CODE, ErrorResponseConstants.USER_NOT_EXISTS_MESSAGE);
            }

            pengyuanLoginReq.setUuid(uid);
            pengyuanLoginReq.setName(userInfo.getName());
            pengyuanLoginReq.setDocNo(userInfo.getIdentificationNo());

            if (StringUtils.isEmpty(userInfo.getMobile())) {
                log.error("该用户手机号获取失败,uid:{}", uid);
                throw new CjjClientException(ErrorResponseConstants.USER_PHONE_NOT_EXISTS_CODE, ErrorResponseConstants.USER_PHONE_NOT_EXISTS_MESSAGE);
            }
            pengyuanLoginReq.setMobile(userInfo.getMobile());

            PhotoSupplierDataResp photoSupplierDataResp = photoSupplierRpc.getUserPhotos(uid);
            if (photoSupplierDataResp != null) {
                List<String> photos = photoSupplierDataResp.getLivePhotos();
                if (!CollectionUtils.isEmpty(photos)) {
                    if (photos.size() > 0 && !StringUtils.isEmpty(photos.get(0))) {
                        photoUrl = hostUrl + "/image?token=" + photos.get(0) + "&bucketName=" + photoSupplierDataResp.getBucketName();
                    }
                }
            }
            //活体照片
            extendInfo.setPhotoUrl(photoUrl);

            FaceVerifyDataResp resp = photoSupplierRpc.getFaceVerifyData(uid, null);
            if (resp != null) {
                //活体来源
                if (resp.getSupplier() != null) {
                    extendInfo.setLivingBodySource("sht".equals(resp.getSupplier().getCode()) ? "shangtang" : "face++");
                }
                if (resp.getVerifyScore() != null) {
                    extendInfo.setConfidence(Base64Utils.encodeBase64String(resp.getVerifyScore().getBytes()));
                }
            }

            extendInfo.setThresholdLevel("0.6"); //阈值给固定值
            pengyuanLoginReq.setExtraField(extendInfo);

        } catch (Exception e) {
            log.warn("联合登陆所需请求参数获取失败：" + e);
            return UnionJumpBo.builder().jumpUrl(jumpUrl).build();
        }

        Map<String, Object> reqParam = bulidReqParam(pengyuanLoginReq);
        log.info("鹏元联合登陆请求参数：" + reqParam.toString());
        jumpUrl = pyBuildReqUrlService.bulidReqUrl(reqParam, jumpUrl);
        return UnionJumpBo.builder().jumpUrl(jumpUrl).build();
    }

    private Map<String, Object> bulidReqParam(PengyuanLoginReq pengyuanLoginReq) {
        Map<String, Object> param = Maps.newHashMap();
        Map<String, Object> extraField = Maps.newHashMap();
        extraField.put("confidence", pengyuanLoginReq.getExtraField().getConfidence());
        extraField.put("thresholdLevel", pengyuanLoginReq.getExtraField().getThresholdLevel());
        extraField.put("photoUrl", pengyuanLoginReq.getExtraField().getPhotoUrl());
        extraField.put("policeUrl", pengyuanLoginReq.getExtraField().getPoliceUrl());
        extraField.put("livingBodySource", pengyuanLoginReq.getExtraField().getLivingBodySource());
        param.put("uuid", pengyuanLoginReq.getUuid());
        param.put("name", pengyuanLoginReq.getName());
        param.put("docNo", pengyuanLoginReq.getDocNo());
        param.put("mobile", pengyuanLoginReq.getMobile());
        param.put("extraField", extraField);

        return param;
    }

    @Override
    public String getChannelName() {
        return UnionLoginChannelEnum.PENGYUAN.toString();
    }
}
