package com.gfg.employee.service;

import com.gfg.employee.entity.Employee;
import com.gfg.employee.feignclient.AddressClient;
import com.gfg.employee.repository.EmployeeRepo;
import com.gfg.employee.response.AddressResponse;
import com.gfg.employee.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AddressClient addressClient;

    public EmployeeResponse getEmployeeById(int id){
        Logger log = LoggerFactory.getLogger(EmployeeService.class);


        Optional<Employee> employee = employeeRepo.findById(id);
//        log.info(employee.toString());
        EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);

        // Using FeignClient
        ResponseEntity<AddressResponse> addressResponse = addressClient.getAddressByEmployeeId(id);
        employeeResponse.setAddressResponse(addressResponse.getBody());

        return employeeResponse;
    }
}
