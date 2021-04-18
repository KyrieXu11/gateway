function ServiceItem(id, service_name, service_desc, load_type, service_addr, qps, qpd, total_node) {
  this.id = id
  this.service_name = service_name
  this.service_desc = service_desc
  this.load_type = load_type
  this.service_addr = service_addr
  this.qps = qps
  this.qpd = qpd
  this.total_node = total_node
}

const serviceList = [
  {
    id: 1,
    service_name: 'mockService',
    service_desc: 'mock service',
    load_type: 1,
    service_addr: 'localhost:8081',
    qps: 0,
    qpd: 0,
    total_node: 123
  },
  {
    id: 100,
    service_name: 'test_http_string',
    service_desc: 'test_http_string',
    load_type: 0,
    service_addr: 'localhost:8080',
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
    url: '/service/delService',
    type: 'delete',
    response: config => {
      return {
        code: 200,
        msg: '',
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
        msg: '',
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
            data: s
          }
        }
      })
      return {
        code: 200,
        msg: '',
        data: null
      }
    }
  },
  {
    url: '/verifyCode',
    type: 'get',
    response: config => {
      return {
        code: 200,
        msg: '',
        data: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAAAeCAIAAABVOSykAAAB80lEQVR4Xu3YsUoDQRAG4DyCnWBjY2lhZWln7ytY2KigINikslIQn3lcMsk4+ed2dvayd8a4P4OcsxMhH3NH4oJ6wllgoyefSbBOrm6cwul5c/R0mwq7scyKdb98rMV6P/9Ihd3tFGf0wIRYDy93qbDrJrdBSQo6Os+fb9haJQgRmaGN1N5hYXcI6/psyRc5KSphiVRxhq93kaLZsKwUbbAcKQpg8c/cDFD+YazE5EtRAEJf2MDRPmLJhfNcZya5E3OJQORmuD8VFtNooFosAdLFWHrjZKFGY+l+ZIZ2frqTxtJSlxenufp56VC0EXfkBpS+vvWmw+KmxVIj1VljwULpDmyW5fNLP61AisJYYAGdyAw1x9o+GhCsTZI6/nrlso6DBX/BQsQ78iunDZYj4hxFotdK1LikD8tl+fyig8GykcdZzs5Gv3PrYvn8kheOyK9hQRw7i6VPbX9wRtbK8sUd9wULAnb85u1aSaSfG9j9HqTmWEWL4oBNevPO3snMTFiUQeGm7TuRD1N4sIpz5ERDcAfsePVgBtIeS7tIpwqL8l65fjEMkVsZjr96LNUMi7Z1xGgEFmW+9IyTIvdRJYEZsGsiRc2/G0qaMHFGYEH8vYun8F+Hg8xou/+IBYmrdayKdKyKdKyKdKyKdKyKdKyKfAMszRSvB2agKAAAAABJRU5ErkJggg=='
      }
    }
  }
]
