package com.java_generate_demo.service;

import com.java_generate_demo.entity.po.ProductInfo;
import com.java_generate_demo.entity.query.ProductInfoQuery;
import com.java_generate_demo.entity.vo.PaginationResultVO;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.java_generate_demo.utils.DateUtils;
import com.java_generate_demo.enums.DateTimePatternEnum;
/**
 * @ Author: 单纯同学
 * @ Date: 2024-08-27-11:45
 * @ Description: 商品信息服务类
 */
public interface ProductInfoService {
	/**
	 * 根据条件查询列表
	 */
	List<ProductInfo> findListByParam(ProductInfoQuery query);
	/**
	 * 根据条件查询数量
	 */
	Long findCountByParam(ProductInfoQuery query);
	/**
	 * 分页查询
	 */
	PaginationResultVO<ProductInfo> findListByPage(ProductInfoQuery query);
	/**
	 * 新增
	 */
	Long add(ProductInfo bean);
	/**
	 * 批量新增
	 */
	Long addBatch(List<ProductInfo> ListBean);
	/**
	 * 批量新增或修改
	 */
	Long addOrUpdateBatch(List<ProductInfo> ListBean);
	/**
	 * 根据Id查询
	 */
	ProductInfo getProductInfoById(Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateProductInfoById(ProductInfo bean, Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteProductInfoById(Integer id);

	/**
	 * 根据Code查询
	 */
	ProductInfo getProductInfoByCode(String code);

	/**
	 * 根据Code更新
	 */
	Integer updateProductInfoByCode(ProductInfo bean, String code);

	/**
	 * 根据Code删除
	 */
	Integer deleteProductInfoByCode(String code);

	/**
	 * 根据SkuTypeAndColorType查询
	 */
	ProductInfo getProductInfoBySkuTypeAndColorType(Integer skuType, Integer colorType);

	/**
	 * 根据SkuTypeAndColorType更新
	 */
	Integer updateProductInfoBySkuTypeAndColorType(ProductInfo bean, Integer skuType, Integer colorType);

	/**
	 * 根据SkuTypeAndColorType删除
	 */
	Integer deleteProductInfoBySkuTypeAndColorType(Integer skuType, Integer colorType);

}
