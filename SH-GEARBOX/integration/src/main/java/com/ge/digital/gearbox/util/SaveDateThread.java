
package com.ge.digital.gearbox.util;

import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ge.digital.gearbox.entity.C2H2;
import com.ge.digital.gearbox.entity.Ccf;
import com.ge.digital.gearbox.entity.Ctg;
import com.ge.digital.gearbox.entity.ExCar;
import com.ge.digital.gearbox.entity.InCar;
import com.ge.digital.gearbox.entity.Preox;
import com.ge.digital.gearbox.entity.ProductionProc;
import com.ge.digital.gearbox.entity.Temper;
import com.ge.digital.gearbox.entity.Tunnel;
import com.ge.digital.gearbox.entity.Wash;
import com.ge.digital.gearbox.mapper.MongoC2H2Repository;
import com.ge.digital.gearbox.mapper.MongoCcfRepository;
import com.ge.digital.gearbox.mapper.MongoCtgRepository;
import com.ge.digital.gearbox.mapper.MongoExCarRepository;
import com.ge.digital.gearbox.mapper.MongoInCarRepository;
import com.ge.digital.gearbox.mapper.MongoPreoxRepository;
import com.ge.digital.gearbox.mapper.MongoProductionProcRepository;
import com.ge.digital.gearbox.mapper.MongoTemperRepository;
import com.ge.digital.gearbox.mapper.MongoTunnelRepository;
import com.ge.digital.gearbox.mapper.MongoWashRepository;

public class SaveDateThread extends Thread {

	MongoRepository mongoRepository;

	int level = 0;

	Integer loopCount =10;
	String name;
	public SaveDateThread(MongoRepository mongoRepository, int level,String name) {
		// TODO Auto-generated constructor stub
		this.mongoRepository = mongoRepository;
		this.level = level;
		this.name=name;
	}

	@Override
	public void run() {
		long before=System.currentTimeMillis();
		// TODO Auto-generated method stub
		if (mongoRepository instanceof MongoC2H2Repository) {
			for (int i = 0; i < loopCount; i++) {
				String index = i + "";
				C2H2 c2h2 = new C2H2();
				c2h2.setC2H2_flow(index);
				c2h2.setC2H2_pressure(index);
				c2h2.setEquipId(index);
				c2h2.setLine(index);
				c2h2.setPlusData1(index);
				c2h2.setPlusData2(index);
				c2h2.setPlusData3(index);
				c2h2.setTimestamp(new Date());
				mongoRepository.save(c2h2);
			}
		}
		if (mongoRepository instanceof MongoCcfRepository) {
			for (int i = 0; i < loopCount; i++) {
				String index = i + "";
				Ccf ccf = new Ccf();
				ccf.setEquipId(index);
				ccf.setDEB_ACE(Float.valueOf(index+""));
				ccf.setDEB_AZO(Float.valueOf(index+""));
				ccf.setP_GAZS(Float.valueOf(index+""));
				ccf.setT_CONS(Float.valueOf(index+""));
				ccf.setT_MES(Float.valueOf(index+""));
				ccf.setT_SORT(Float.valueOf(index+""));
				ccf.setLine(index);
				ccf.setPlusData1(index);
				ccf.setPlusData2(index);
				ccf.setPlusData3(index);
				ccf.setTimestamp(new Date());
				mongoRepository.save(ccf);
			}
		}
		if (mongoRepository instanceof MongoCtgRepository) {
			for (int i = 0; i < loopCount; i++) {
				String index = i + "";
				Ctg ctg = new Ctg();
				ctg.setEquipId(index);
				ctg.setPRES_BALLON_HP(Float.valueOf(index+""));
				ctg.setCOUR1(Float.valueOf(index+""));
				ctg.setCOUR2(Float.valueOf(index+""));
				ctg.setPRESSION(Float.valueOf(index+""));
				ctg.setTE_MES(Float.valueOf(index+""));
				ctg.setTS_MES(Float.valueOf(index+""));
				ctg.setLine(index);
				ctg.setPlusData1(index);
				ctg.setPlusData2(index);
				ctg.setPlusData3(index);
				ctg.setTimestamp(new Date());
				mongoRepository.save(ctg);
			}
		}
		if (mongoRepository instanceof MongoExCarRepository) {
			for (int i = 0; i < loopCount; i++) {
				String index = i + "";
				ExCar exCar = new ExCar();
				exCar.setEquipId(index);
				exCar.setLine(index);
				exCar.setPlusData1(index);
				exCar.setPlusData2(index);
				exCar.setPlusData3(index);
				exCar.setPOS_ENFOUR(index);
				exCar.setPOS_TRANS(index);
				exCar.setTimestamp(new Date());
				mongoRepository.save(exCar);
			}
		}
		if (mongoRepository instanceof MongoInCarRepository) {
			for (int i = 0; i < loopCount; i++) {
				String index = i + "";
				InCar inCar = new InCar();
				inCar.setEquipId(index);
				inCar.setLine(index);
				inCar.setPlusData1(index);
				inCar.setPlusData2(index);
				inCar.setPlusData3(index);
				inCar.setPOS_ENFOUR(index);
				inCar.setPOS_TRANS(index);
				inCar.setTimestamp(new Date());
				mongoRepository.save(inCar);
			}
		}
		if (mongoRepository instanceof MongoPreoxRepository) {
			for (int i = 0; i < loopCount; i++) {
				String index = i + "";
				Preox preox = new Preox();
				preox.setEquipId(index);
				preox.setT_CONS(Float.valueOf(index+""));
				preox.setT_MES(Float.valueOf(index+""));
				preox.setT_SORT(Float.valueOf(index+""));
				preox.setLine(index);
				preox.setPlusData1(index);
				preox.setPlusData2(index);
				preox.setPlusData3(index);
				preox.setTimestamp(new Date());
				mongoRepository.save(preox);
			}
		}
		if (mongoRepository instanceof MongoTemperRepository) {
			for (int i = 0; i < loopCount; i++) {
				String index = i + "";
				Temper temper = new Temper();
				temper.setEquipId(index);
				temper.setT_CONS(Float.valueOf(index+""));
				temper.setT_MES(Float.valueOf(index+""));
				temper.setT_SORT(Float.valueOf(index+""));
				temper.setLine(index);
				temper.setPlusData1(index);
				temper.setPlusData2(index);
				temper.setPlusData3(index);
				temper.setTimestamp(new Date());
				mongoRepository.save(temper);
			}
		}
		if (mongoRepository instanceof MongoTunnelRepository) {
			for (int i = 0; i < loopCount; i++) {
				String index = i + "";
				Tunnel tunnel = new Tunnel();
				tunnel.setEquipId(index);
				tunnel.setP_CONS(Float.valueOf(index+""));
				tunnel.setP_MES(Float.valueOf(index+""));
				tunnel.setP_SORT(Float.valueOf(index+""));
				tunnel.setLine(index);
				tunnel.setPlusData1(index);
				tunnel.setPlusData2(index);
				tunnel.setPlusData3(index);
				tunnel.setTimestamp(new Date());
				mongoRepository.save(tunnel);
			}
		}
		if (mongoRepository instanceof MongoWashRepository) {
			for (int i = 0; i < loopCount; i++) {
				String index = i + "";
				Wash wash = new Wash();
				wash.setEquipId(index);
				wash.setRINC_T_MES(Float.valueOf(index+""));
				wash.setLAV_T_MES(Float.valueOf(index+""));
				wash.setT_SORT(Float.valueOf(index+""));
				wash.setLine(index);
				wash.setPlusData1(index);
				wash.setPlusData2(index);
				wash.setPlusData3(index);
				wash.setTimestamp(new Date());
				mongoRepository.save(wash);
			}
		}
		if (mongoRepository instanceof MongoProductionProcRepository) {
			for (int i = 0; i < loopCount; i++) {
				ProductionProc productionProc = new ProductionProc();
				productionProc.setEventCode(i);
				productionProc.setHeatingEntryDate(new Date());
				productionProc.setHeatingExitDate(new Date());
				productionProc.setHeatingNumber(i+"");
				productionProc.setiCBPEntryDate(new Date());
				productionProc.setiCBPExitDate(new Date());
				productionProc.setLineEntryDate(new Date());
				productionProc.setLineExitDate(new Date());
				productionProc.setLine(i+"");
				productionProc.setLoadNumber(i);
				productionProc.setMonth(i);
				productionProc.setPartNumber(i);
				productionProc.setPreHeatingEntryDate(new Date());
				productionProc.setPreHeatingExitDate(new Date());
				productionProc.setQuenchingEntryDate(new Date());
				productionProc.setQuenchingExitDate(new Date());
				productionProc.setRecipeNumber("" + i);
				productionProc.setTemperingEntryDate(new Date());
				productionProc.setTemperingExitDate(new Date());
				productionProc.setTemperingNumber(i+"");
				productionProc.setWashingEntryDate(new Date());
				productionProc.setWashingExitDate(new Date());
				productionProc.setWashingNumber(i+"");
				productionProc.setYear(i);
				mongoRepository.save(productionProc);
			}
		}
		long after=System.currentTimeMillis();
		System.out.println(name.toUpperCase()+"loop count:"+loopCount+",total time:"+(after-before)+"ms");
	}
}

