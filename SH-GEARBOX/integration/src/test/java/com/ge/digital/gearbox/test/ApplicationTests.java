//package com.ge.digital.gearbox.test;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.ge.digital.gearbox.entity.ProductionProc;
//import com.ge.digital.gearbox.mapper.CustomMongoRepository;
//import com.ge.digital.gearbox.mapper.MongoC2H2Repository;
//import com.ge.digital.gearbox.mapper.MongoCcfRepository;
//import com.ge.digital.gearbox.mapper.MongoCtgRepository;
//import com.ge.digital.gearbox.mapper.MongoExCarRepository;
//import com.ge.digital.gearbox.mapper.MongoInCarRepository;
//import com.ge.digital.gearbox.mapper.MongoPreoxRepository;
//import com.ge.digital.gearbox.mapper.MongoProductionProcRepository;
//import com.ge.digital.gearbox.mapper.MongoTemperRepository;
//import com.ge.digital.gearbox.mapper.MongoTunnelRepository;
//import com.ge.digital.gearbox.mapper.MongoWashRepository;
//import com.ge.digital.gearbox.mapper.SpoJobpayloadRepository;
//import com.ge.digital.gearbox.mq.Customer;
//import com.ge.digital.gearbox.mq.Producer;
//import com.ge.digital.gearbox.util.SaveDateThread;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ApplicationTests {
//
//	private static final Logger log = LoggerFactory.getLogger(ApplicationTests.class);
//
//	@Value("${custom.archive.beannames}")
//	String beansNames;
//
//	@Autowired
//	Producer producer;
//
//	@Autowired
//	CustomMongoRepository customMongoRepository;
//
//	@Autowired
//	Customer customer;
//
//	@Autowired
//	MongoC2H2Repository mongoC2H2Repository;
//
//	@Autowired
//	MongoCcfRepository mongoCcfRepository;
//
//	@Autowired
//	MongoCtgRepository mongoCtgRepository;
//
//	@Autowired
//	MongoExCarRepository mongoExCarRepository;
//
//	@Autowired
//	MongoInCarRepository mongoInCarRepository;
//
//	@Autowired
//	MongoPreoxRepository mongoPreoxRepository;
//	@Autowired
//	MongoTemperRepository mongoTemperRepository;
//	@Autowired
//	MongoTunnelRepository mongoTunnelRepository;
//	@Autowired
//	MongoWashRepository mongoWashRepository;
//
//	@Autowired
//	MongoProductionProcRepository mongoProductionProcRepository;
//
//	@Value("${custom.archive.beannames}")
//	String beanNames;
//
//	@Autowired
//	private SpoJobpayloadRepository spoJobpayloadRepository;
//	@Autowired
//	MongoCcfRepository mccfR;
//	@Autowired
//	MongoProductionProcRepository mongoProdProcRepository;
//
//	@Test
//	public void test() throws Exception {
//		long before=System.currentTimeMillis();
//		List<ProductionProc> procs = mongoProductionProcRepository.findByloadNumber("1018500");
//		long after=System.currentTimeMillis();
//		System.out.println(after-before);
//	}
// 
//	public void basicData() throws Exception {
//		SaveDateThread saveDateThread1 = new SaveDateThread(mongoC2H2Repository, 1);
//		saveDateThread1.start();
//		SaveDateThread saveDateThread2 = new SaveDateThread(mongoCcfRepository, 2);
//		saveDateThread2.start();
//		SaveDateThread saveDateThread3 = new SaveDateThread(mongoCtgRepository, 3);
//		saveDateThread3.start();
//		SaveDateThread saveDateThread4 = new SaveDateThread(mongoExCarRepository, 4);
//		saveDateThread4.start();
//		SaveDateThread saveDateThread5 = new SaveDateThread(mongoInCarRepository, 5);
//		saveDateThread5.start();
//		SaveDateThread saveDateThread6 = new SaveDateThread(mongoPreoxRepository, 6);
//		saveDateThread6.start();
//		SaveDateThread saveDateThread7 = new SaveDateThread(mongoTemperRepository, 7);
//		saveDateThread7.start();
//		SaveDateThread saveDateThread8 = new SaveDateThread(mongoTunnelRepository, 8);
//		saveDateThread8.start();
//		SaveDateThread saveDateThread9 = new SaveDateThread(mongoWashRepository, 9);
//		saveDateThread9.start();
//		SaveDateThread saveDateThread10 = new SaveDateThread(mongoProductionProcRepository, 10);
//		saveDateThread10.start();
//
//		Thread.sleep(3000000);
//	}
//
//}
