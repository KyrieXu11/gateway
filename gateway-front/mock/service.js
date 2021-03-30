const serviceList = [
  {
    id: 1,
    service_name: 'mockService',
    service_desc: 'mock service',
    load_type: 0,
    service_addr: 'localhost:8080',
    qps: 0,
    qpd: 0,
    total_node: 123
  },
  {
    id: 100,
    service_name: 'mockService2',
    service_desc: 'mock service 2',
    load_type: 1,
    service_addr: 'localhost:8081',
    qps: 0,
    qpd: 0,
    total_node: 222
  }
]

const servicePageBean = {
  total: serviceList.length,
  current: 1,
  items: serviceList
}

module.exports = [
  {
    url: '/service/list',
    type: 'get',
    response: config => {
      return {
        code: 200,
        msg: '',
        data: servicePageBean
      }
    }
  },
  {
    url: '/service/deleteService',
    type: 'delete',
    response: config => {
      return {
        code: 200,
        msg: '删除成功',
        data: null
      }
    }
  },
  {
    url: '/service/updateHttpService',
    type: 'put',
    response: config => {
      return {
        code: 200,
        msg: '删除成功',
        data: null
      }
    }
  },
  {
    url: '/service/detail',
    type: 'get',
    response: config => {
      const { id } = config.query
      serviceList.forEach(s => {
        if (s.id === id) {
          return {
            code: 200,
            msg: '',
            data: null
          }
        }
      })
      return {
        code: 200,
        msg: '删除成功',
        data: null
      }
    }
  }
]
