import request from '@/utils/request'

// 查询所有省份高校统计信息列表
export function listProvinces(query) {
  return request({
    url: '/university/province/listAll',
    method: 'get',
    params: query
  })
}

// 查询省份详细信息
export function getProvince(provinceId) {
  return request({
    url: '/university/province/detail',
    method: 'get',
    params: { provinceId }
  })
}

// 添加省份高校统计信息
export function addProvince(data) {
  return request({
    url: '/university/province/add',
    method: 'post',
    data: data
  })
}

// 更新省份高校统计信息
export function updateProvince(data) {
  return request({
    url: '/university/province/update',
    method: 'post',
    data: data
  })
}

// 删除省份高校统计信息
export function deleteProvince(provinceId) {
  return request({
    url: '/university/province/delete',
    method: 'get',
    params: { provinceId }
  })
}
