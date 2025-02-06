import request from '@/utils/request'

// 获取所有反馈记录
export function listAllFeedbacks() {
  return request({
    url: '/university/feedback/listAll',
    method: 'get'
  })
}

// 根据反馈ID查询反馈记录详情
export function getFeedbackById(feedbackId) {
  return request({
    url: '/university/feedback/detail',
    method: 'get',
    params: { feedbackId }
  })
}

// 根据用户ID查询反馈记录
export function listFeedbacksByUserId(userId) {
  return request({
    url: '/university/feedback/listByUserId',
    method: 'get',
    params: { userId }
  })
}

// 新增反馈记录
export function addFeedback(data) {
  return request({
    url: '/university/feedback/add',
    method: 'post',
    data: data
  })
}

// 回复反馈
export function replyFeedback(data) {
  return request({
    url: '/university/feedback/reply',
    method: 'post',
    data: data
  })
}

// 删除反馈记录
export function deleteFeedback(feedbackId) {
  return request({
    url: '/university/feedback/delete',
    method: 'get',
    params: { feedbackId }
  })
}
