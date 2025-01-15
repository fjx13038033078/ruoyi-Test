import request from '@/utils/request'

// 查询所有自习室列表
export function listStudyRooms(query) {
  return request({
    url: '/study/room/listAll',
    method: 'get',
    params: query
  })
}

// 查询自习室详细信息
export function getStudyRoom(roomId) {
  return request({
    url: '/study/room/detail',
    method: 'get',
    params: { roomId }
  })
}

// 添加自习室
export function addStudyRoom(data) {
  return request({
    url: '/study/room/add',
    method: 'post',
    data: data
  })
}

// 更新自习室信息
export function updateStudyRoom(data) {
  return request({
    url: '/study/room/update',
    method: 'post',
    data: data
  })
}

// 删除自习室
export function deleteStudyRoom(roomId) {
  return request({
    url: '/study/room/delete',
    method: 'get',
    params: { roomId }
  })
}
