import request from '@/utils/request'

// 查询所有预约记录
export function listReservations(query) {
  return request({
    url: '/study/reservation/listAll',
    method: 'get',
    params: query
  })
}

// 查询预约详细信息
export function getReservation(reservationId) {
  return request({
    url: '/study/reservation/detail',
    method: 'get',
    params: { reservationId }
  })
}

// 根据用户ID查询预约记录
export function listReservationsByUserId(userId) {
  return request({
    url: '/study/reservation/listByUserId',
    method: 'get',
    params: { userId }
  })
}

// 根据座位ID查询预约记录
export function listReservationsBySeatId(seatId) {
  return request({
    url: '/study/reservation/listBySeatId',
    method: 'get',
    params: { seatId }
  })
}

// 添加预约记录
export function addReservation(data) {
  return request({
    url: '/study/reservation/add',
    method: 'post',
    data: data
  })
}

// 更新预约记录
export function updateReservation(data) {
  return request({
    url: '/study/reservation/update',
    method: 'post',
    data: data
  })
}

// 删除预约记录
export function deleteReservation(reservationId) {
  return request({
    url: '/study/reservation/delete',
    method: 'get',
    params: { reservationId }
  })
}
