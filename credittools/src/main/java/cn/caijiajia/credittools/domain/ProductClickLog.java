package cn.caijiajia.credittools.domain;

import java.util.Date;

public class ProductClickLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_click_log.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_click_log.uid
     *
     * @mbggenerated
     */
    private String uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_click_log.product_id
     *
     * @mbggenerated
     */
    private Integer productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_click_log.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_click_log.click_time
     *
     * @mbggenerated
     */
    private Integer clickTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_click_log.created_at
     *
     * @mbggenerated
     */
    private Date createdAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_click_log.updated_at
     *
     * @mbggenerated
     */
    private Date updatedAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_click_log.id
     *
     * @return the value of product_click_log.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_click_log.id
     *
     * @param id the value for product_click_log.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_click_log.uid
     *
     * @return the value of product_click_log.uid
     *
     * @mbggenerated
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_click_log.uid
     *
     * @param uid the value for product_click_log.uid
     *
     * @mbggenerated
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_click_log.product_id
     *
     * @return the value of product_click_log.product_id
     *
     * @mbggenerated
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_click_log.product_id
     *
     * @param productId the value for product_click_log.product_id
     *
     * @mbggenerated
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_click_log.name
     *
     * @return the value of product_click_log.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_click_log.name
     *
     * @param name the value for product_click_log.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_click_log.click_time
     *
     * @return the value of product_click_log.click_time
     *
     * @mbggenerated
     */
    public Integer getClickTime() {
        return clickTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_click_log.click_time
     *
     * @param clickTime the value for product_click_log.click_time
     *
     * @mbggenerated
     */
    public void setClickTime(Integer clickTime) {
        this.clickTime = clickTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_click_log.created_at
     *
     * @return the value of product_click_log.created_at
     *
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_click_log.created_at
     *
     * @param createdAt the value for product_click_log.created_at
     *
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_click_log.updated_at
     *
     * @return the value of product_click_log.updated_at
     *
     * @mbggenerated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_click_log.updated_at
     *
     * @param updatedAt the value for product_click_log.updated_at
     *
     * @mbggenerated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}