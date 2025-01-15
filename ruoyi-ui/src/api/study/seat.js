import request from '@/utils/request'

// 查询所有座位信息
export function listSeats(query) {
  return request({
    url: '/study/seat/listAll',
    method: 'get',
    params: query
  })
}

// 查询座位详细信息
export function getSeat(seatId) {
  return request({
    url: '/study/seat/detail',
    method: 'get',
    params: { seatId }
  })
}

// 根据自习室ID查询所有座位
export function listSeatsByRoomId(roomId) {
  return request({
    url: '/study/seat/listByRoomId',
    method: 'get',
    params: { roomId }
  })
}

// 添加座位
export function addSeat(data) {
  return request({
    url: '/study/seat/add',
    method: 'post',
    data: data
  })
}

// 更新座位信息
export function updateSeat(data) {
  return request({
    url: '/study/seat/update',
    method: 'post',
    data: data
  })
}

// 删除座位
export function deleteSeat(seatId) {
  return request({
    url: '/study/seat/delete',
    method: 'get',
    params: { seatId }
  })
}
