package com.hummingbird.maaccount.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hummingbird.maaccount.entity.FactoryTask;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.mapper.FactoryAccountIdMapper;
import com.hummingbird.maaccount.mapper.FactoryTaskMapper;
import com.hummingbird.maaccount.mapper.ProductMapper;

/**
 * 本JOB是检查现金账户和投资账户的剩余有效帐号，当剩余帐号小于一定量时 就生成相应
 * 的任务
 * @author Administrator
 *
 */

@Component("createFactoryTaskSchedule")
public class CreateFactoryTaskSchedule {
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private FactoryAccountIdMapper factoryAccountIdMapper;
    
    @Autowired
    private FactoryTaskMapper factoryTaskMapper;
    
    //现金账户ID
    private final String CASH_ACCOUNT_PRODUCT_ID="9500";
    
    //投资账户ID
    private final String INVEST_ACCOUNT_PRODUCT_ID="9700";
    
    //账户最小可用数量
    private final long MIN_COUNT=3000;
    
    //创建账户的数量
    private final long DEFAULT_COUNT=10000;
    
    //可用状态
    private final String USABLE="NUS";
    
    //需要自动创建的任务的产品ID列表
    private List<String> productIdList;
    
    org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(CreateFactoryTaskSchedule.class);
    
    public CreateFactoryTaskSchedule() {
    	productIdList=new ArrayList<String>();
    	productIdList.add(CASH_ACCOUNT_PRODUCT_ID);
    	productIdList.add(INVEST_ACCOUNT_PRODUCT_ID);
	}
    
    public void createFactoryTask(){
    	log.info("进入自动创建账户任务JOB...");
    	for (String productId: productIdList) {
    		createFactoryTask(productId);
		}
    	log.info("自动创建账户任务JOB结束...");
    }
    
    //创建对应任务
    private void createFactoryTask(String productId){
    	Product product=productMapper.selectByPrimaryKey(productId);
		if(product==null){
			log.info(String.format("当前产品ID为: %s 的产品不存在,不进行任务创建。", productId));
			return;
		}
		int crtTaskCount=factoryTaskMapper.selectCRTTaskByProductId(productId);
		if(crtTaskCount>0){
			log.info(String.format("当前产品ID为: %s 的产品已经存在相关创建任务,不进行任务创建。", productId));
			return;
		}
		long accountIdCount=factoryAccountIdMapper.selectCountByProductIdAndStatus(productId, USABLE);
		if(MIN_COUNT<accountIdCount){
			log.info(String.format("当前产品名称为: %s 的产品可用账户大于%s,不进行任务创建。", product.getProductName(),MIN_COUNT));
			return;
		}
		log.info(String.format("正在创建产品名称为: %s 的任务,该任务生成的账户数量为:%s", product.getProductName(),DEFAULT_COUNT));
		FactoryTask factoryTask=createFactoryTaskBean(product);
		factoryTaskMapper.insert(factoryTask);
		log.info(String.format("创建产品名称为: %s 的任务完成", product.getProductName()));
    }
    
    private FactoryTask createFactoryTaskBean(Product product){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	FactoryTask factoryTask=new FactoryTask();
    	factoryTask.setAmount(DEFAULT_COUNT);
    	factoryTask.setCounter(0L);
    	factoryTask.setProductId(product.getProductId());
    	factoryTask.setProductName(product.getProductName());
    	factoryTask.setRemark(sdf.format(new Date())+"系统自动创建的任务");
    	factoryTask.setStatus("CRT");//状态，CRT-创建任务，DNG-执行中，OK#-执行完成，DEL-任务撤销，FLS-任务无法执行，失败
    	factoryTask.setTaskName(product.getProductName()+"系统自动创建任务");
    	factoryTask.setStartTime(new Date());
    	factoryTask.setUnitId("010");
    	return factoryTask;
    }
}
