package com.lzy.tianmao.web;

import com.lzy.tianmao.bean.Property;
import com.lzy.tianmao.bean.PropertyValue;
import com.lzy.tianmao.service.PropertyService;
import com.lzy.tianmao.service.PropertyValueService;
import com.lzy.tianmao.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class PropertyValueController {

    @Autowired
    PropertyService propertyService;
    @Autowired
    PropertyValueService propertyValueService;

    @GetMapping("/products/{pid}/propertyValues")
    public Object list(@PathVariable("pid") int pid){

        List<PropertyValue> propertyValues = propertyValueService.list(pid);
        List<Property> properties = propertyService.findByPropertyValues(propertyValues);

        HashMap<String, Object> map = new HashMap<>();
        map.put("propertyValues",propertyValues);
        map.put("properties",properties);

        return Result.success(map);
    }

    @PutMapping("/propertyValues")
    public List<PropertyValue> update(@RequestBody List<PropertyValue> propertyValues){
        for (PropertyValue propertyValue : propertyValues) {
            propertyValueService.update(propertyValue);
        }
        return propertyValues;
    }
}
