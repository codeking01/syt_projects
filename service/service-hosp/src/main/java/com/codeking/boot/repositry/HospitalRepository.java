package com.codeking.boot.repositry;

import com.codeking.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {

    // 命名有严格要求，可以参考官网链接
    Hospital getHospitalByHoscode(String hoscode);

    // 通过id去查询
    Hospital getHospitalById(String id);
}
