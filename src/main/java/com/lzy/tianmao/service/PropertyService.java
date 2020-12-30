package com.lzy.tianmao.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzy.tianmao.bean.Property;
import com.lzy.tianmao.bean.PropertyValue;
import com.lzy.tianmao.dao.PropertyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyService {

    @Autowired
    PropertyDao propertyDao;

    public PageInfo<Property> list(int cid,int start, int size, int navigatePages){
        PageHelper.startPage(start, size);
        List<Property> properties = propertyDao.findAll(cid);
        PageInfo<Property> pageInfo = new PageInfo<>(properties, navigatePages);
        return pageInfo;
    }

    public Property findById(int id){
        Property property = propertyDao.findById(id);
        return property;
    }

    //根据传入的 propertyValues 集合查询所有的属性（**有顺序**）
    public List<Property> findByPropertyValues(List<PropertyValue> propertyValues){
        List<Property> properties = new ArrayList<>();
        for (PropertyValue propertyValue : propertyValues) {
            Property property = propertyDao.findById(propertyValue.getPtid());
            properties.add(property);
        }
        return properties;
    }

    public void add(Property property){
        propertyDao.save(property);
    }

    public void delete(int id){
        propertyDao.delete(id);
    }

    public void update(Property property){
        propertyDao.update(property);
    }

}
