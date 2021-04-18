const panelData = {
  serviceNum: 2,
  appNum: 1,
  currentQps: 300,
  todayRequestNum: 10
}

const data = {
  today: [1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 10],
  yesterday: [200, 222, 111, 2222, 99999, 1111, 1]
}

const DashServiceStatItem1 = {
  name: 'abc',
  loadType: 1,
  value: 1
}

const DashServiceStatItem2 = {
  name: 'aaa',
  loadType: 2,
  value: 3
}

const DashServiceStat = {
  legend: ['123', '234'],
  data: [DashServiceStatItem1, DashServiceStatItem2]
}

module.exports = [
  {
    url: '/dashboard/panelGroupData',
    type: 'get',
    response: req => {
      // mock error
      return {
        code: 200,
        msg: '',
        data: panelData
      }
    }
  },
  {
    url: '/dashboard/flowStat',
    type: 'get',
    response: req => {
      return {
        code: 200,
        msg: '',
        data: data
      }
    }
  },
  {
    url: '/dashboard/serviceStat',
    type: 'get',
    response: req => {
      return {
        code: 200,
        msg: '',
        data: DashServiceStat
      }
    }
  }
]
