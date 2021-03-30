import request from '@/utils/request'

export function serviceList(query) {
  return request({
    url: '/service/list',
    method: 'get',
    params: query
  })
}

export function serviceDelete(id) {
  return request({
    url: `/service/deleteService/` + id,
    method: 'delete'
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
    url: '/service/service_stat',
    method: 'get',
    params: query
  })
}

export function serviceAddTcp(data) {
  return request({
    url: '/service/service_add_tcp',
    method: 'post',
    data
  })
}

export function serviceUpdateTcp(data) {
  return request({
    url: '/service/service_update_tcp',
    method: 'post',
    data
  })
}

export function serviceAddGrpc(data) {
  return request({
    url: '/service/service_add_grpc',
    method: 'post',
    data
  })
}

export function serviceUpdateGrpc(data) {
  return request({
    url: '/service/service_update_grpc',
    method: 'post',
    data
  })
}
