import request from '@/utils/request'

export function serviceList(query) {
  return request({
    url: '/service/list',
    method: 'get',
    params: query
  })
}

export function serviceDelete(query, id) {
  return request({
    url: `/service/delService/` + id,
    method: 'delete',
    params: query
  })
}

export function addHttpService(data) {
  return request({
    url: '/service/addHttpService',
    method: 'post',
    data
  })
}

export function updateHttpService(data) {
  return request({
    url: '/service/updateHttpService',
    method: 'put',
    data
  })
}

export function serviceDetail(query) {
  return request({
    url: '/service/detail',
    method: 'get',
    params: query
  })
}

export function serviceStat(query) {
  return request({
    url: '/service/serviceStat',
    method: 'get',
    params: query
  })
}

export function serviceAddTcp(data) {
  return request({
    url: '/service/addTcpService',
    method: 'post',
    data
  })
}

export function serviceUpdateTcp(data) {
  return request({
    url: '/service/updateTcpService',
    method: 'put',
    data
  })
}

export function serviceAddGrpc(data) {
  return request({
    url: '/service/addGrpcService',
    method: 'post',
    data
  })
}

export function serviceUpdateGrpc(data) {
  return request({
    url: '/service/updateGrpcService',
    method: 'put',
    data
  })
}

export function verifyCode(query) {
  return request({
    url: '/verifyCode',
    method: 'get',
    params: query
  })
}
