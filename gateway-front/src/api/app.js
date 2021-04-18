import request from '@/utils/request'

export function appList(query) {
  return request({
    url: '/app/list',
    method: 'get',
    params: query
  })
}

export function appDetail(query) {
  return request({
    url: '/app/detail',
    method: 'get',
    params: query
  })
}

export function appStat(query) {
  return request({
    url: '/app/appStat',
    method: 'get',
    params: query
  })
}

export function appDelete(query) {
  return request({
    url: '/app/delApp',
    method: 'delete',
    params: query
  })
}

export function appAdd(data) {
  return request({
    url: '/app/addApp',
    method: 'post',
    data
  })
}

export function appUpdate(data) {
  return request({
    url: '/app/updateApp',
    method: 'put',
    data
  })
}
