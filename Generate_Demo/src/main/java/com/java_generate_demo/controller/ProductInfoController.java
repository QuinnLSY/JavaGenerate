package com.java_generate_demo.controller;

import com.java_generate_demo.entity.po.ProductInfo;
import com.java_generate_demo.entity.query.ProductInfoQuery;
import com.java_generate_demo.service.ProductInfoService;
import com.java_generate_demo.entity.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.List;

/**
 * @ Author: 单纯同学
 * @ Date: 2024-08-29-21:21
 * @ Description: 商品信息控制器
 */
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController extends ABaseController{

	@Resource
	private ProductInfoService productInfoService;
	/**
	 * 加载数据列表
	 */
	@RequestMapping("loadDataList")
	public ResponseVO loadDataList(ProductInfoQuery query){
		return getSuccessResponseVO(productInfoService.findListByPage(query));
	}
	/**
	 * 新增
	 */
	@RequestMapping("add")
	public ResponseVO add(ProductInfo bean){
		productInfoService.add(bean);
		return getSuccessResponseVO(null);
	}
	/**
	 * 批量新增
	 */
	@RequestMapping("addBatch")
	public ResponseVO addBatch(@RequestBody List<ProductInfo> listBean){
		productInfoService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}
	/**
	 * 批量新增或修改
	 */
	@RequestMapping("addOrUpdateBatchBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<ProductInfo> listBean){
		productInfoService.addOrUpdateBatch(listBean);
		return getSuccessResponseVO(null);
	}
	/**
	 * 根据Id查询
	 */
	@RequestMapping("getProductInfoById")
	public ResponseVO getProductInfoById(Integer id){
		return getSuccessResponseVO(productInfoService.getProductInfoById(id));
	}

	/**
	 * 根据Id更新
	 */
	@RequestMapping("updateProductInfoById")
	public ResponseVO updateProductInfoById(ProductInfo bean, Integer id){
		productInfoService.updateProductInfoById(bean, id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id删除
	 */
	@RequestMapping("deleteProductInfoById")
	public ResponseVO deleteProductInfoById(Integer id){
		productInfoService.deleteProductInfoById(id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Code查询
	 */
	@RequestMapping("getProductInfoByCode")
	public ResponseVO getProductInfoByCode(String code){
		return getSuccessResponseVO(productInfoService.getProductInfoByCode(code));
	}

	/**
	 * 根据Code更新
	 */
	@RequestMapping("updateProductInfoByCode")
	public ResponseVO updateProductInfoByCode(ProductInfo bean, String code){
		productInfoService.updateProductInfoByCode(bean, code);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Code删除
	 */
	@RequestMapping("deleteProductInfoByCode")
	public ResponseVO deleteProductInfoByCode(String code){
		productInfoService.deleteProductInfoByCode(code);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据SkuTypeAndColorType查询
	 */
	@RequestMapping("getProductInfoBySkuTypeAndColorType")
	public ResponseVO getProductInfoBySkuTypeAndColorType(Integer skuType, Integer colorType){
		return getSuccessResponseVO(productInfoService.getProductInfoBySkuTypeAndColorType(skuType, colorType));
	}

	/**
	 * 根据SkuTypeAndColorType更新
	 */
	@RequestMapping("updateProductInfoBySkuTypeAndColorType")
	public ResponseVO updateProductInfoBySkuTypeAndColorType(ProductInfo bean, Integer skuType, Integer colorType){
		productInfoService.updateProductInfoBySkuTypeAndColorType(bean, skuType, colorType);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据SkuTypeAndColorType删除
	 */
	@RequestMapping("deleteProductInfoBySkuTypeAndColorType")
	public ResponseVO deleteProductInfoBySkuTypeAndColorType(Integer skuType, Integer colorType){
		productInfoService.deleteProductInfoBySkuTypeAndColorType(skuType, colorType);
		return getSuccessResponseVO(null);
	}

}
