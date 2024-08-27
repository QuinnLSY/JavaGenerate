import com.java_generate_demo.RunDemoApplication;
import com.java_generate_demo.entity.po.ProductInfo;
import com.java_generate_demo.entity.query.ProductInfoQuery;
import com.java_generate_demo.mappers.ProductInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-26-14:42
 * @ Description：测试列表情况，Mapper.xml中的查询列表配置
 */
// 使用SpringBootTest注解启动RunDemoApplication应用
@SpringBootTest(classes = RunDemoApplication.class)
// 使用SpringRunner运行测试
@RunWith(SpringRunner.class)
// MapperTest类用于测试ProductInfoMapper的功能
public class MapperTest {
    // Resource注解用于自动装配ProductInfoMapper bean
    @Resource
    private ProductInfoMapper<ProductInfo, ProductInfoQuery> productInfoMapper;

    // selectList测试方法用于验证ProductInfoMapper的selectList方法
    @Test
    public void selectList() {
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
//        productInfoQuery.setId(6);
        productInfoQuery.setCreateDateStart("2024-08-01");
//        productInfoQuery.setCodeFuzzy("2");
        // 调用selectList方法获取产品信息列表
        List<ProductInfo> list = productInfoMapper.selectList(productInfoQuery);
        // 输出产品信息列表的大小
//        System.out.println(list.size());
        for(ProductInfo productInfo : list){
            System.out.println(productInfo);
        }
    }
//    @Test
//    public void insert() {
//        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
//        productInfoQuery.setCode("100006");
//        productInfoQuery.setColorType(2);
//        productInfoQuery.setSkuType(2);
//        productInfoQuery.setCompanyId("10001");
//        productInfoQuery.setCreateDate(new Date());
//        productInfoQuery.setCreateTime(new Date());
//        this.productInfoMapper.insert(productInfoQuery);
//        System.out.println(productInfoQuery.getId());
//    }

//    @Test
//    public void insertOrUpdate() {
//        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
//        productInfoQuery.setCode("10006");
//        productInfoQuery.setColorType(2);
//        productInfoQuery.setSkuType(2);
//        productInfoQuery.setCompanyId("100000");
//        productInfoQuery.setProductName("apple");
//        productInfoQuery.setCreateDate(new Date());
//        productInfoQuery.setCreateTime(new Date());
//        this.productInfoMapper.insertOrUpdate(productInfoQuery);
//        System.out.println(productInfoQuery.getId());
//    }

//    @Test
//    public void insertBatch(){
//        List<ProductInfo> productInfoList = new ArrayList<>();
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setCode("10007");
//        productInfo.setCreateDate(new Date());
//        productInfoList.add(productInfo);
//
//        productInfo = new ProductInfo();
//        productInfo.setCode("10008");
//        productInfo.setCreateTime(new Date());
//        productInfoList.add(productInfo);
//        productInfoMapper.insertBatch(productInfoList);
//    }

//    @Test
//    public void insertOrUpdateBatch(){
//        List<ProductInfo> productInfoList = new ArrayList<>();
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setCode("10007");
//        productInfo.setCreateDate(new Date());
//        productInfo.setProductName("apple17");
//        productInfoList.add(productInfo);
//
//        productInfo = new ProductInfo();
//        productInfo.setCode("10008");
//        productInfo.setCreateTime(new Date());
//        productInfo.setProductName("apple18");
//        productInfoList.add(productInfo);
//        productInfoMapper.insertOrUpdateBatch(productInfoList);
//    }

//    @Test
//    public void selectByKey(){
//        ProductInfo productInfo1 = productInfoMapper.selectById(9);
//        ProductInfo productInfo2 = productInfoMapper.selectByCode("10005");
//        ProductInfo productInfo3 = productInfoMapper.selectBySkuTypeAndColorType(5,3);
//
//        System.out.println(productInfo1);
//        System.out.println(productInfo2);
//        System.out.println(productInfo3);
//    }

//    @Test
//    public void updateByKey(){
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setProductName("update by 9");
//        productInfoMapper.updateById(productInfo,9);
//
//        productInfo = new ProductInfo();
//        productInfo.setProductName("update by 10007");
//        productInfoMapper.updateByCode(productInfo,"10007");
//
//        productInfo = new ProductInfo();
//        productInfo.setProductName("update by 2");
//        productInfoMapper.updateBySkuTypeAndColorType(productInfo,2,2);
//    }

    @Test
    public void deleteByKey(){
        productInfoMapper.deleteById(21);
        productInfoMapper.deleteByCode("10007");
        productInfoMapper.deleteBySkuTypeAndColorType(1,3);
    }
}
