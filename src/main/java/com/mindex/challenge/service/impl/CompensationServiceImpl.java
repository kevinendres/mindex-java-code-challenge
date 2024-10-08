package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {
  private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);
  private final CompensationRepository compensationRepository;

  public CompensationServiceImpl(CompensationRepository compensationRepository) {
    this.compensationRepository = compensationRepository;
  }

  @Override
  public Compensation create(Compensation compensation) {
    LOG.debug("Creating compensation [{}]", compensation);

    if (compensation.getSalary() < 0) {
      compensation.setSalary(0);
    }
    return compensationRepository.save(compensation);
  }

  @Override
  public Compensation read(String id) {
    LOG.debug("Reading compensation with employeeId [{}]", id);

    Compensation compensation = compensationRepository.findByEmployee(id);
    if (compensation == null) {
      throw new RuntimeException("Invalid employeeId: " + id);
    }
    return compensation;
  }
}
