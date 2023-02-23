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
    /**
     * 根据上级编码与值获取数据字典名称
     * @param parentDictCode
     * @param value
     * @return
     */
    String getNameByParentDictCodeAndValue(String parentDictCode, String value);

    List<Dict> findByDictCode(String dictCode);
}
