package io.github.wangtianruipopo.trcoregen.controller;

import io.github.tr.common.web.controller.BaseController;
import io.github.wangtianruipopo.trcoregen.entity.CreateCodeFilter;
import io.github.wangtianruipopo.trcoregen.entity.Schema;
import io.github.wangtianruipopo.trcoregen.entity.Tables;
import io.github.wangtianruipopo.trcoregen.service.ITablesService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tables")
public class TablesController extends BaseController<ITablesService, Tables> {

    @PostMapping("/createCode")
    public void createCode(@RequestBody CreateCodeFilter filter, HttpServletResponse response) {
        this.baseService.createCode(filter, response);
    }

    @GetMapping("/listSchema")
    public List<Schema> listSchema() {
        return this.baseService.listSchema();
    }
}
