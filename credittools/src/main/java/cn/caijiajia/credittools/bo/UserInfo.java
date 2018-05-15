package cn.caijiajia.credittools.bo;

import java.util.Date;
import java.util.List;

/**
 * @Author:chendongdong
 * @Date:2018/5/10
 */
public class UserInfo {
    private String uid;

    private String idCardFront;

    private String idCardBack;

    private String name;

    private String academic;

    private String email;

    private String marriage;

    private String address;

    private String identificationNo;

    private String creditCardNo;

    private String mobile;

    private String infoStatus;

    private Date birthday;

    private String applicationNo;

    private String issueGroup;

    private String identAddress;

    private Date createdAt;

    private String facePhoto;

    private String closeName;

    private String commonName;

    private String province;

    private String city;

    private String countryTown;

    private String meridian;

    private String parallel;

    private String actionPhoto1;

    private String actionPhoto2;

    private String actionPhoto3;

    private String actionPhoto4;

    private String actionPhoto5;

    private String actionPhoto6;

    private Date updatedAt;

    private String photoPackage;

    private String channel;

    private String fundSource;

    private String auditRouter;

    private String thirdAuditResultMemo;

    private String companyName;

    private String thirdApplyInfo;

    private String thirdInfo;

    private String userGroup;

    private String auditStatus;

    private List<String> deviceList;

    private String devices;

    private String infoStep;

    private String applyStep;

    private String customerNo;

    private String thirdAuditResult;

    private String closeMobile;  //申请用户联系人1

    private String commonMobile; //申请用户联系人2

    private Date cardApplyDate; //身份证申请时间

    private Date cardDate; //身份证有效期

    private boolean isVirtual;  //是否虚拟机

    private int userContactCount; //通讯录个数

    private String deviceModel; //设备类型

    private int sensitiveUserContactCount;    //通讯录敏感词个数

    private int latest3MonthSameLBSApplyCount; //最近3个月同LBS的申请次数

    private int sameDeviceUserCount;        //同设备注册、登录的账号数

    private int sameDeviceBssiCount;            //同WiFi申请个数

    private String cardValidDate;

    private Date auditFinishAt;

    private String os;

    //同盾用的
    private String blackBox;
    //同盾用的
    private String ipAddress;
    //同盾用的p_o
    private String osType;

    private String lawId;

    private String wifi;

    private String product;

    private String auditMobile;

    public String getAuditMobile() {
        return auditMobile;
    }

    public void setAuditMobile(String auditMobile) {
        this.auditMobile = auditMobile;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public boolean isVirtual() {
        return isVirtual;
    }

    public void setVirtual(boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getLawId() {
        return lawId;
    }

    public void setLawId(String lawId) {
        this.lawId = lawId;
    }

    public String getThirdInfo() {
        return thirdInfo;
    }

    public void setThirdInfo(String thirdInfo) {
        this.thirdInfo = thirdInfo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcademic() {
        return academic;
    }

    public void setAcademic(String academic) {
        this.academic = academic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCloseMobile() {
        return closeMobile;
    }

    public void setCloseMobile(String closeMobile) {
        this.closeMobile = closeMobile;
    }

    public String getCommonMobile() {
        return commonMobile;
    }

    public void setCommonMobile(String commonMobile) {
        this.commonMobile = commonMobile;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInfoStatus() {
        return infoStatus;
    }

    public void setInfoStatus(String infoStatus) {
        this.infoStatus = infoStatus;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getIssueGroup() {
        return issueGroup;
    }

    public void setIssueGroup(String issueGroup) {
        this.issueGroup = issueGroup;
    }

    public String getIdentAddress() {
        return identAddress;
    }

    public void setIdentAddress(String identAddress) {
        this.identAddress = identAddress;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFacePhoto() {
        return facePhoto;
    }

    public void setFacePhoto(String facePhoto) {
        this.facePhoto = facePhoto;
    }

    public String getCloseName() {
        return closeName;
    }

    public void setCloseName(String closeName) {
        this.closeName = closeName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryTown() {
        return countryTown;
    }

    public void setCountryTown(String countryTown) {
        this.countryTown = countryTown;
    }

    public String getMeridian() {
        return meridian;
    }

    public void setMeridian(String meridian) {
        this.meridian = meridian;
    }

    public String getParallel() {
        return parallel;
    }

    public void setParallel(String parallel) {
        this.parallel = parallel;
    }

    public String getActionPhoto1() {
        return actionPhoto1;
    }

    public void setActionPhoto1(String actionPhoto1) {
        this.actionPhoto1 = actionPhoto1;
    }

    public String getActionPhoto2() {
        return actionPhoto2;
    }

    public void setActionPhoto2(String actionPhoto2) {
        this.actionPhoto2 = actionPhoto2;
    }

    public String getActionPhoto3() {
        return actionPhoto3;
    }

    public void setActionPhoto3(String actionPhoto3) {
        this.actionPhoto3 = actionPhoto3;
    }

    public String getActionPhoto4() {
        return actionPhoto4;
    }

    public void setActionPhoto4(String actionPhoto4) {
        this.actionPhoto4 = actionPhoto4;
    }

    public String getActionPhoto5() {
        return actionPhoto5;
    }

    public void setActionPhoto5(String actionPhoto5) {
        this.actionPhoto5 = actionPhoto5;
    }

    public String getActionPhoto6() {
        return actionPhoto6;
    }

    public void setActionPhoto6(String actionPhoto6) {
        this.actionPhoto6 = actionPhoto6;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPhotoPackage() {
        return photoPackage;
    }

    public void setPhotoPackage(String photoPackage) {
        this.photoPackage = photoPackage;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFundSource() {
        return fundSource;
    }

    public void setFundSource(String fundSource) {
        this.fundSource = fundSource;
    }

    public String getAuditRouter() {
        return auditRouter;
    }

    public void setAuditRouter(String auditRouter) {
        this.auditRouter = auditRouter;
    }

    public String getThirdAuditResultMemo() {
        return thirdAuditResultMemo;
    }

    public void setThirdAuditResultMemo(String thirdAuditResultMemo) {
        this.thirdAuditResultMemo = thirdAuditResultMemo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getThirdApplyInfo() {
        return thirdApplyInfo;
    }

    public void setThirdApplyInfo(String thirdApplyInfo) {
        this.thirdApplyInfo = thirdApplyInfo;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public List<String> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<String> deviceList) {
        this.deviceList = deviceList;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public String getInfoStep() {
        return infoStep;
    }

    public void setInfoStep(String infoStep) {
        this.infoStep = infoStep;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getThirdAuditResult() {
        return thirdAuditResult;
    }

    public void setThirdAuditResult(String thirdAuditResult) {
        this.thirdAuditResult = thirdAuditResult;
    }

    public Date getCardApplyDate() {
        return cardApplyDate;
    }

    public void setCardApplyDate(Date cardApplyDate) {
        this.cardApplyDate = cardApplyDate;
    }

    public boolean getIsVirtual() {
        return isVirtual;
    }

    public int getUserContactCount() {
        return userContactCount;
    }

    public void setUserContactCount(int userContactCount) {
        this.userContactCount = userContactCount;
    }

    public int getSensitiveUserContactCount() {
        return sensitiveUserContactCount;
    }

    public void setSensitiveUserContactCount(int sensitiveUserContactCount) {
        this.sensitiveUserContactCount = sensitiveUserContactCount;
    }

    public int getLatest3MonthSameLBSApplyCount() {
        return latest3MonthSameLBSApplyCount;
    }

    public void setLatest3MonthSameLBSApplyCount(int latest3MonthSameLBSApplyCount) {
        this.latest3MonthSameLBSApplyCount = latest3MonthSameLBSApplyCount;
    }

    public int getSameDeviceUserCount() {
        return sameDeviceUserCount;
    }

    public void setSameDeviceUserCount(int sameDeviceUserCount) {
        this.sameDeviceUserCount = sameDeviceUserCount;
    }

    public int getSameDeviceBssiCount() {
        return sameDeviceBssiCount;
    }

    public void setSameDeviceBssiCount(int sameDeviceBssiCount) {
        this.sameDeviceBssiCount = sameDeviceBssiCount;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public Date getCardDate() {
        return cardDate;
    }

    public void setCardDate(Date cardDate) {
        this.cardDate = cardDate;
    }

    public Date getAuditFinishAt() {
        return auditFinishAt;
    }

    public void setAuditFinishAt(Date auditFinishAt) {
        this.auditFinishAt = auditFinishAt;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBlackBox() {
        return blackBox;
    }

    public void setBlackBox(String blackBox) {
        this.blackBox = blackBox;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCardValidDate() {
        return cardValidDate;
    }

    public void setCardValidDate(String cardValidDate) {
        this.cardValidDate = cardValidDate;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getApplyStep() {
        return applyStep;
    }

    public void setApplyStep(String applyStep) {
        this.applyStep = applyStep;
    }
}
