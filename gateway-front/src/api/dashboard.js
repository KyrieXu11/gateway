import request from '@/utils/request'

export function panelGroupData() {
  return request({
    url: '/dashboard/panelGroupData',
    method: 'get'
  })
}

export function flowStat() {
  return request({
    url: '/dashboard/flowStat',
    method: 'get'
  })
}

export function serviceStat() {
  return request({
    url: '/dashboard/serviceStat',
    method: 'get'
  })
}
