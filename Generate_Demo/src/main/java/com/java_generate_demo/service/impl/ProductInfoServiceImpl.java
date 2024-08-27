package com.java_generate_demo.service.impl;

import com.java_generate_demo.entity.po.ProductInfo;
import com.java_generate_demo.entity.query.ProductInfoQuery;
import com.java_generate_demo.entity.vo.PaginationResultVO;
import com.java_generate_demo.service.ProductInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author: 单纯同学
 * @ Date: 2024-08-27-12:22
 * @ Description: 商品信息服务类接口
 */
@Service("productInfoService")
public class ProductInfoServiceImpl implements ProductInfoService{
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
	Long updateProductInfoById(ProductInfo bean, Integer id);

	/**
	 * 根据Id删除
	 */
	Long deleteProductInfoById(Integer id);

	/**
	 * 根据Code查询
	 */
	ProductInfo getProductInfoByCode(String code);

	/**
	 * 根据Code更新
	 */
	Long updateProductInfoByCode(ProductInfo bean, String code);

	/**
	 * 根据Code删除
	 */
	Long deleteProductInfoByCode(String code);

	/**
	 * 根据SkuTypeAndColorType查询
	 */
	ProductInfo getProductInfoBySkuTypeAndColorType(Integer skuType, Integer colorType);

	/**
	 * 根据SkuTypeAndColorType更新
	 */
	Long updateProductInfoBySkuTypeAndColorType(ProductInfo bean, Integer skuType, Integer colorType);

	/**
	 * 根据SkuTypeAndColorType删除
	 */
	Long deleteProductInfoBySkuTypeAndColorType(Integer skuType, Integer colorType);

}
