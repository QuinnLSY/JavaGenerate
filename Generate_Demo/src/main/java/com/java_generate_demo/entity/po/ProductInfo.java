package com.java_generate_demo.entity.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.java_generate_demo.utils.DateUtils;
import com.java_generate_demo.entity.enums.DateTimePatternEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @ Author: 单纯同学
 * @ Date: 2024-08-29-21:21
 * @ Description: 商品信息
 */
public class ProductInfo implements Serializable {
	/**
	 * 自增ID
	 */
	private Integer id;
	/**
	 * 公司ID
	 */
	@JsonIgnore
	private String companyId;
	/**
	 * 商品编号
	 */
	private String code;
	/**
	 * 商品名称
	 */
	private String productName;
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
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 创建日期
	 */
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createDate;
	/**
	 * 库存
	 */
	private Long stock;
	/**
	 * 状态0：未上架， 1：已上架
	 */
	@JsonIgnore
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

	@Override
	public String toString() {
		return "自增ID:" + (id == null ? "空" : id) + ", 公司ID:" + (companyId == null ? "空" : companyId) + ", 商品编号:" + (code == null ? "空" : code) + ", 商品名称:" + (productName == null ? "空" : productName) + ", 价格:" + (price == null ? "空" : price) + ", sku类型:" + (skuType == null ? "空" : skuType) + ", 颜色类型:" + (colorType == null ? "空" : colorType) + ", 创建时间:" + (createTime == null ? "空" : DateUtils.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ", 创建日期:" + (createDate == null ? "空" : DateUtils.format(createDate, DateTimePatternEnum.YYYY_MM_DD.getPattern())) + ", 库存:" + (stock == null ? "空" : stock) + ", 状态0：未上架， 1：已上架:" + (status == null ? "空" : status) ;
	}
}