package com.lzy.tianmao.web;

import com.github.pagehelper.PageInfo;
import com.lzy.tianmao.bean.Property;
import com.lzy.tianmao.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping("/categories/{cid}/properties")
    public PageInfo<Property> list(
            @PathVariable(value = "cid") int cid,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "navigatePages", defaultValue = "5") int navigatePages) {
        PageInfo<Property> pageInfo = propertyService.list(cid, start, size, navigatePages);
        return pageInfo;
    }

    @PostMapping("/properties")
    public Property add(@RequestBody Property property){
        System.out.println(property);
        propertyService.add(property);

        return property;
    }

    @DeleteMapping("/properties/{id}")
    public String delete(@PathVariable("id") Integer id){
        propertyService.delete(id);
        return null;
    }

    @GetMapping("/properties/{id}")
    public Property findById(@PathVariable("id") Integer id){
        Property property = propertyService.findById(id);
        return property;
    }

    @PutMapping("/properties")
    public Property update(@RequestBody Property property){
        propertyService.update(property);
        return property;
    }
}
