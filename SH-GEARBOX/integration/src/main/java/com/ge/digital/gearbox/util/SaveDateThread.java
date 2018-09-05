
//package com.ge.digital.gearbox.util;
//
//import java.util.Date;
//
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import com.ge.digital.gearbox.entity.C2H2;
//import com.ge.digital.gearbox.entity.Ccf;
//import com.ge.digital.gearbox.entity.Ctg;
//import com.ge.digital.gearbox.entity.ExCar;
//import com.ge.digital.gearbox.entity.InCar;
//import com.ge.digital.gearbox.entity.Preox;
//import com.ge.digital.gearbox.entity.ProductionProc;
//import com.ge.digital.gearbox.entity.Temper;
//import com.ge.digital.gearbox.entity.Tunnel;
//import com.ge.digital.gearbox.entity.Wash;
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
//
//public class SaveDateThread extends Thread {
//
//	MongoRepository mongoRepository;
//
//	int level = 0;
//
//	public SaveDateThread(MongoRepository mongoRepository, int level) {
//		// TODO Auto-generated constructor stub
//		this.mongoRepository = mongoRepository;
//		this.level = level;
//	}
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		if (mongoRepository instanceof MongoC2H2Repository) {
//			for (int i = 0; i < 100000; i++) {
//				String index = i + "";
//				C2H2 c2h2 = new C2H2();
//				c2h2.setC2H2_flow(index);
//				c2h2.setC2H2_pressure(index);
//				c2h2.setEuipId(index);
//				c2h2.setLine(index);
//				c2h2.setPlusData1(index);
//				c2h2.setPlusData2(index);
//				c2h2.setPlusData3(index);
//				c2h2.setTimestamp(new Date());
//				mongoRepository.save(c2h2);
//			}
//		}
//		if (mongoRepository instanceof MongoCcfRepository) {
//			for (int i = 0; i < 100000; i++) {
//				String index = i + "";
////				Ccf ccf = new Ccf();
////				ccf.setEuipId(index);
////				ccf.setICBP_C1_DEB_ACE(index);
////				ccf.setICBP_C1_DEB_AZO(index);
////				ccf.setICBP_C1_P_GAZS(index);
////				ccf.setICBP_C1_T_CONS(index);
////				ccf.setICBP_C1_T_MES(index);
////				ccf.setICBP_C1_T_SORT(index);
////				ccf.setLine(index);
////				ccf.setPlusData1(index);
////				ccf.setPlusData2(index);
////				ccf.setPlusData3(index);
////				ccf.setTimestamp(new Date());
////				mongoRepository.save(ccf);
//			}
//		}
//		if (mongoRepository instanceof MongoCtgRepository) {
//			for (int i = 0; i < 100000; i++) {
//				String index = i + "";
//				Ctg ctg = new Ctg();
//				ctg.setEuipId(index);
//				ctg.setICBP_RG_PRES_BALLON_HP(index);
//				ctg.setICBP_TRG_COUR1(index);
//				ctg.setICBP_TRG_COUR2(index);
//				ctg.setICBP_TRG_PRESSION(index);
//				ctg.setICBP_TRG_TE_MES(index);
//				ctg.setICBP_TRG_TS_MES(index);
//				ctg.setLine(index);
//				ctg.setPlusData1(index);
//				ctg.setPlusData2(index);
//				ctg.setPlusData3(index);
//				ctg.setTimestamp(new Date());
//				mongoRepository.save(ctg);
//			}
//		}
//		if (mongoRepository instanceof MongoExCarRepository) {
//			for (int i = 0; i < 100000; i++) {
//				String index = i + "";
//				ExCar exCar = new ExCar();
//				exCar.setEuipId(index);
//				exCar.setLine(index);
//				exCar.setPlusData1(index);
//				exCar.setPlusData2(index);
//				exCar.setPlusData3(index);
//				exCar.setPOS_ENFOUR(index);
//				exCar.setPOS_TRANS(index);
//				exCar.setTimestamp(new Date());
//				mongoRepository.save(exCar);
//			}
//		}
//		if (mongoRepository instanceof MongoInCarRepository) {
//			for (int i = 0; i < 100000; i++) {
//				String index = i + "";
//				InCar inCar = new InCar();
//				inCar.setEuipId(index);
//				inCar.setLine(index);
//				inCar.setPlusData1(index);
//				inCar.setPlusData2(index);
//				inCar.setPlusData3(index);
//				inCar.setPOS_ENFOUR(index);
//				inCar.setPOS_TRANS(index);
//				inCar.setTimestamp(new Date());
//				mongoRepository.save(inCar);
//			}
//		}
//		if (mongoRepository instanceof MongoPreoxRepository) {
//			for (int i = 0; i < 100000; i++) {
//				String index = i + "";
//				Preox preox = new Preox();
//				preox.setEuipId(index);
//				preox.setFAS_PREOX1_T_CONS(index);
//				preox.setFAS_PREOX1_T_MES(index);
//				preox.setFAS_PREOX1_T_SORT(index);
//				preox.setLine(index);
//				preox.setPlusData1(index);
//				preox.setPlusData2(index);
//				preox.setPlusData3(index);
//				preox.setTimestamp(new Date());
//				mongoRepository.save(preox);
//			}
//		}
//		if (mongoRepository instanceof MongoTemperRepository) {
//			for (int i = 0; i < 100000; i++) {
//				String index = i + "";
//				Temper temper = new Temper();
//				temper.setEuipId(index);
//				temper.setFOUR_Z3_T_CONS(index);
//				temper.setFOUR_Z3_T_MES(index);
//				temper.setFOUR_Z3_T_SORT(index);
//				temper.setLine(index);
//				temper.setPlusData1(index);
//				temper.setPlusData2(index);
//				temper.setPlusData3(index);
//				temper.setTimestamp(new Date());
//				mongoRepository.save(temper);
//			}
//		}
//		if (mongoRepository instanceof MongoTunnelRepository) {
//			for (int i = 0; i < 100000; i++) {
//				String index = i + "";
//				Tunnel tunnel = new Tunnel();
//				tunnel.setEuipId(index);
//				tunnel.setICBP_SAS_P_CONS(index);
//				tunnel.setICBP_SAS_P_MES(index);
//				tunnel.setICBP_SAS_P_SORT(index);
//				tunnel.setLine(index);
//				tunnel.setPlusData1(index);
//				tunnel.setPlusData2(index);
//				tunnel.setPlusData3(index);
//				tunnel.setTimestamp(new Date());
//				mongoRepository.save(tunnel);
//			}
//		}
//		if (mongoRepository instanceof MongoWashRepository) {
//			for (int i = 0; i < 100000; i++) {
//				String index = i + "";
//				Wash wash = new Wash();
//				wash.setEuipId(index);
//				wash.setFAS_MAL1_LAV_T_MES(index);
//				wash.setFAS_MAL1_RINC_T_MES(index);
//				wash.setFAS_MAL1_T_SORT(index);
//				wash.setLine(index);
//				wash.setPlusData1(index);
//				wash.setPlusData2(index);
//				wash.setPlusData3(index);
//				wash.setTimestamp(new Date());
//				mongoRepository.save(wash);
//			}
//		}
//		if (mongoRepository instanceof MongoProductionProcRepository) {
//			for (int i = 0; i < 100000; i++) {
//				ProductionProc productionProc = new ProductionProc();
//				productionProc.setEventCode(i);
//				productionProc.setHeatingEntryDate(new Date());
//				productionProc.setHeatingExitDate(new Date());
//				productionProc.setHeatingNumber(i+"");
//				productionProc.setiCBPEntryDate(new Date());
//				productionProc.setiCBPExitDate(new Date());
//				productionProc.setLineEntryDate(new Date());
//				productionProc.setLineExitDate(new Date());
//				productionProc.setLine(i+"");
//				productionProc.setLoadNumber(i);
//				productionProc.setMonth(i);
//				productionProc.setPartNumber(i);
//				productionProc.setPreHeatingEntryDate(new Date());
//				productionProc.setPreHeatingExitDate(new Date());
//				productionProc.setQuenchingEntryDate(new Date());
//				productionProc.setQuenchingExitDate(new Date());
//				productionProc.setRecipeNumber("" + i);
//				productionProc.setTemperingEntryDate(new Date());
//				productionProc.setTemperingExitDate(new Date());
//				productionProc.setTemperingNumber(i+"");
//				productionProc.setWashingEntryDate(new Date());
//				productionProc.setWashingExitDate(new Date());
//				productionProc.setWashingNumber(i+"");
//				productionProc.setYear(i);
//				mongoRepository.save(productionProc);
//			}
//		}
//
//	}
//}

