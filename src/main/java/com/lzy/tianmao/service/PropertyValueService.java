package com.lzy.tianmao.service;


import com.lzy.tianmao.bean.PropertyValue;
import com.lzy.tianmao.dao.PropertyValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueService {

    @Autowired
    PropertyValueDao propertyValueDao;

    public List<PropertyValue> list(int pid){
        List<PropertyValue> propertyValues = propertyValueDao.findAll(pid);
        return propertyValues;
    }

    public void update(PropertyValue propertyValue){
        propertyValueDao.update(propertyValue);
    }
}
