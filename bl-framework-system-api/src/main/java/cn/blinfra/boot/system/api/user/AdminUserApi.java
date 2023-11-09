package cn.blinfra.boot.system.api.user;

import cn.blinfra.boot.common.util.CollectionUtils;
import cn.blinfra.boot.system.api.user.dto.AdminUserRespDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AdminUserApi {

  /**
   * 通过用户ID查询用户
   * @param id 用户ID
   * @return 用户对象信息
   */
  AdminUserRespDTO getUser(Long id);

  /**
   * 通过用户ID查询用户们
   * @param ids 用户IDs
   * @return 用户对象信息
   */
  List<AdminUserRespDTO> getUserList(Collection<Long> ids);

  /**
   * 获得制定部门的用户数组
   * @param deptIds
   * @return 用户对象信息
   */
  List<AdminUserRespDTO> getUserListByDeptIds(Collection<Long> deptIds);

  /**
   * 获得制定岗位的用户数组
   * @param postIds 岗位数组
   * @return 用户数组
   */
  List<AdminUserRespDTO> getUsersByPostIds(Collection<Long> postIds);

  /**
   * 获得用户 Map
   * @param ids 用户编号数组
   * @return 用户Map
   */
  default Map<Long, AdminUserRespDTO> getUserMap(Collection<Long> ids) {
    List<AdminUserRespDTO> users = getUserList(ids);
    return CollectionUtils.convertMap(users, AdminUserRespDTO::getId);
  }

  /**
   * 校验用户们是否有效，如下情况，是为无效
   * 1. 用户编号不存在
   * 2. 用户被禁用
   * @param ids 用户编号数组
   */
  void validateUsetrList(Collection<Long> ids);

}
