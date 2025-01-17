import request from '@/utils/request'

// 根据自习室ID获取评论列表
export function listCommentsByRoomId(roomId) {
  return request({
    url: '/study/comment/listByRoomId',
    method: 'get',
    params: { roomId }
  })
}

// 根据评论ID获取评论详情
export function getCommentById(commentId) {
  return request({
    url: '/study/comment/get',
    method: 'get',
    params: { commentId }
  })
}

// 添加评论
export function addComment(data) {
  return request({
    url: '/study/comment/add',
    method: 'post',
    data: data
  })
}

// 删除评论
export function deleteComment(commentId) {
  return request({
    url: '/study/comment/delete',
    method: 'get',
    params: { commentId }
  })
}
