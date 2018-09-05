package com.ge.digital.gearbox.common.autoIncrKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.ge.digital.gearbox.common.autoIncrKey.*;


@Service
public class BizAutoIncrService {
	private static final int INCREASE = 1;
	private static final int INIT_NUM = 0;

	@Autowired
	BizAutoIncrRepository bizAutoIncrRepository;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public int nextSerial(String key) {
		if (key == null) {
			throw new NullPointerException();
		}

		while (true) {
			BizAutoIncr currernt = bizAutoIncrRepository.findBybizKey(key);
			if (currernt == null) {
				throw new IllegalStateException("No record for biz key: " + key + "  in database.");
			}
			Integer nextVal = currernt.getValue() + INCREASE;

			int affectedRows = bizAutoIncrRepository.updateWithLagerValue(key, nextVal);

			if (affectedRows > 1) {
				throw new IllegalStateException("More than one record updated.");
			} else if (affectedRows == 1) {
				return nextVal;
			} else {
				continue;
			}
		}
	}

	public void removeAll() {
		bizAutoIncrRepository.deleteAll();
	}

	public List<BizAutoIncr> insert(List<BizAutoIncr> bizAutoIncrs) {
		if (bizAutoIncrs == null || bizAutoIncrs.isEmpty()) {
			return new ArrayList<BizAutoIncr>();
		}

		return bizAutoIncrRepository.save(bizAutoIncrs);
	}

	public List<BizAutoIncr> getAll() {
		return bizAutoIncrRepository.findAll();
	}

	@Transactional
	public void init() {
		Set<String> existedKeys = bizAutoIncrRepository.findAll().stream().map(i -> i.getBizKey())
				.collect(Collectors.toSet());

		List<BizAutoIncr> entries = Arrays.asList(BizAutoIncrKey.values()).stream()
				.filter(ik -> !existedKeys.contains(ik.getValue())).map(ik -> {
					BizAutoIncr i = new BizAutoIncr();
					i.setBizKey(ik.getValue());
					i.setValue(INIT_NUM);
					return i;
				}).collect(Collectors.toList());

		bizAutoIncrRepository.save(entries);
	}
}
