package com.codeking.yygh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codeking.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author : codeking
 * @create : 2023/2/4 22:13
 */
public interface DictService extends IService<Dict> {
    //根据数据id查询子数据列表
    List<Dict> findChlidData(Long id);
    /**
     * 导出
     * @param response
     */
    void exportData(HttpServletResponse response);

    // 导入数据
    void importDictData(MultipartFile file);

}
