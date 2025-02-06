import request from '@/utils/request'

// 查询所有高校列表
export function listUniversities(query) {
  return request({
    url: '/university/university/listAll',
    method: 'get',
    params: query
  })
}

// 查询高校详细信息
export function getUniversity(universityId) {
  return request({
    url: '/university/university/detail',
    method: 'get',
    params: { universityId }
  })
}

// 添加高校信息
export function addUniversity(data) {
  return request({
    url: '/university/university/add',
    method: 'post',
    data: data
  })
}

// 更新高校信息
export function updateUniversity(data) {
  return request({
    url: '/university/university/update',
    method: 'post',
    data: data
  })
}

// 删除高校信息
export function deleteUniversity(universityId) {
  return request({
    url: '/university/university/delete',
    method: 'get',
    params: { universityId }
  })
}
