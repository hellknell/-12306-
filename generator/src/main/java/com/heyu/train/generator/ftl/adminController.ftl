package com.heyu.train.${module}.controller.admin;

import com.heyu.train.common.context.LoginMemberContext;
import com.heyu.train.common.resp.CommonResp;
import com.heyu.train.common.resp.PageResp;
import com.heyu.train.${module}.req.${Domain}QueryReq;
import com.heyu.train.${module}.req.${Domain}SaveReq;
import com.heyu.train.${module}.resp.${Domain}QueryResp;
import com.heyu.train.${module}.service.${Domain}Service;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/${do_main}")
public class ${Domain}AdminController {

    @Resource
    private ${Domain}Service ${domain}Service;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody ${Domain}SaveReq req) {
        ${domain}Service.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<${Domain}QueryResp>> queryList(@Valid ${Domain}QueryReq req) {
        PageResp<${Domain}QueryResp> list = ${domain}Service.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        ${domain}Service.delete(id);
        return new CommonResp<>();
    }

}
