package com.java_generate_demo.entity.query;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @ Author: 单纯同学
 * @ Date: 2024-08-27-12:22
 * @ Description: 商品信息查询对象
 */
public class ProductInfoQuery extends BaseQuery{
	/**
	 * 自增ID
	 */
	private Integer id;
	/**
	 * 公司ID
	 */
	private String companyId;
	/**
	 * 公司ID（模糊查询）
	 */
	private String companyIdFuzzy;
	/**
	 * 商品编号
	 */
	private String code;
	/**
	 * 商品编号（模糊查询）
	 */
	private String codeFuzzy;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品名称（模糊查询）
	 */
	private String productNameFuzzy;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * sku类型
	 */
	private Integer skuType;
	/**
	 * 颜色类型
	 */
	private Integer colorType;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建时间（开始时间）
	 */
	private String createTimeStart;
	/**
	 * 创建时间（结束时间）
	 */
	private String createTimeEnd;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 创建日期（开始时间）
	 */
	private String createDateStart;
	/**
	 * 创建日期（结束时间）
	 */
	private String createDateEnd;
	/**
	 * 库存
	 */
	private Long stock;
	/**
	 * 状态0：未上架， 1：已上架
	 */
	private Integer status;


	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setSkuType(Integer skuType) {
		this.skuType = skuType;
	}

	public Integer getSkuType() {
		return skuType;
	}

	public void setColorType(Integer colorType) {
		this.colorType = colorType;
	}

	public Integer getColorType() {
		return colorType;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Long getStock() {
		return stock;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setCompanyIdFuzzy(String companyIdFuzzy) {
		this.companyIdFuzzy = companyIdFuzzy;
	}

	public String getCompanyIdFuzzy() {
		return companyIdFuzzy;
	}

	public void setCodeFuzzy(String codeFuzzy) {
		this.codeFuzzy = codeFuzzy;
	}

	public String getCodeFuzzy() {
		return codeFuzzy;
	}

	public void setProductNameFuzzy(String productNameFuzzy) {
		this.productNameFuzzy = productNameFuzzy;
	}

	public String getProductNameFuzzy() {
		return productNameFuzzy;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}

	public String getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public String getCreateDateEnd() {
		return createDateEnd;
	}

}