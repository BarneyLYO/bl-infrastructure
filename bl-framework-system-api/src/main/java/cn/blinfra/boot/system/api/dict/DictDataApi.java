package cn.blinfra.boot.system.api.dict;

import cn.blinfra.boot.system.api.dict.dto.DictDataRespDTO;

import java.util.Collection;

/**
 * 字典数据API接口
 */
public interface DictDataApi {

  /**
   * 校验字典数据们是否有效
   * 无效：
   *  字典数据不存在
   *  字典数据被禁用
   * @param dictType 字典类型
   * @param values 字典数据值的数组
   */
  void validateDictDataList(String dictType, Collection<String> values);

  /**
   * 获得制定的字典数据，从缓存中
   * 无效：
   *  字典数据不存在
   *  字典数据被禁用
   * @param type 字典类型
   * @param value 字典数据值
   * @return 字典数据
   */
  DictDataRespDTO getDictData(String type, String value);


  /**
   * 解析获得指定的字典数据，从缓存中
   * @param type 字典类型
   * @param label 字典数据值
   * @return 字典数据
   */
  DictDataRespDTO parseDictData(String type, String label);
}
