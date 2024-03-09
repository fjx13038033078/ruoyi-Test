import request from '@/utils/request'

// 查询所有订单记录信息列表
export function listBalanceRecords(query) {
  return request({
    url: '/featherBall/balanceRecord/listAll',
    method: 'get',
    params: query
  })
}

// 查询订单记录详细信息
export function getBalanceRecord(recordId) {
  return request({
    url: '/featherBall/balanceRecord/detail',
    method: 'get',
    params: { recordId }
  })
}

// 添加订单记录信息
export function addBalanceRecord(data) {
  return request({
    url: '/featherBall/balanceRecord/add',
    method: 'post',
    data: data
  })
}
