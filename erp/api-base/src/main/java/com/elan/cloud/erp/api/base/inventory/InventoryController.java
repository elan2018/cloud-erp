package com.elan.cloud.erp.api.base.inventory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {
    @GetMapping("/warehouse/list")
    public Object getWarehouseListAll(){
        return "所有的仓库列表";
    }
}
