package cn.blinfra.boot.starter.mybatis;

import cn.blinfra.boot.starter.web.WebFrameworkUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 通用参数填充实体类
 * 如果每页显式的对通用参数赋值，这里会对通用参数进行填充赋值
 */
public class DefaultDBFieldHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    if (!Objects.nonNull(metaObject)) {
      return;
    }

    if (!(metaObject.getOriginalObject() instanceof BaseDO)) {
      return;
    }

    BaseDO baseDO = (BaseDO) metaObject.getOriginalObject();

    LocalDateTime current = LocalDateTime.now();

    if (Objects.isNull(baseDO.getCreateTime())) {
      baseDO.setCreateTime(current);
    }

    if (Objects.isNull(baseDO.getUpdateTime())) {
      baseDO.setUpdateTime(current);
    }

    Long userId = WebFrameworkUtils.getLoginUserId();
    if (Objects.nonNull(userId) && Objects.isNull(baseDO.getCreator())) {
      baseDO.setCreator(userId.toString());
    }

    if (Objects.nonNull(userId) && Objects.isNull(baseDO.getUpdater())) {
      baseDO.setUpdater(userId.toString());
    }
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    Object modifyTime = getFieldValByName("updateTime", metaObject);
    if (Objects.isNull(modifyTime)) {
      setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
    Object modifier = getFieldValByName("updater", metaObject);
    Long userId = WebFrameworkUtils.getLoginUserId();
    if (Objects.nonNull(userId) && Objects.isNull(modifier)) {
      setFieldValByName("updater", userId.toString(), metaObject);
    }
  }
}
