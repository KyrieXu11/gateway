function APPListItemOutput(ID, AppID, Name, Secret, WhiteIPS, Qpd, Qps, RealQpd, RealQps, UpdatedAt, CreatedAt, IsDelete) {
  this.id = ID
  this.app_id = AppID
  this.name = Name
  this.secret = Secret
  this.white_ips = WhiteIPS
  this.qpd = Qpd
  this.qps = Qps
  this.real_qpd = RealQpd
  this.real_qps = RealQps
  this.updated_at = UpdatedAt
  this.created_at = CreatedAt
  this.is_delete = IsDelete
}

function App(ID, AppID, Name, Secret, WhiteIPS, Qpd, Qps, UpdatedAt, CreatedAt, IsDelete) {
  this.id = ID
  this.app_id = AppID
  this.name = Name
  this.secret = Secret
  this.white_ips = WhiteIPS
  this.qpd = Qpd
  this.qps = Qps
  this.updated_at = UpdatedAt
  this.created_at = CreatedAt
  this.is_delete = IsDelete
}

function Stat(yes, to) {
  this.yesterday = yes
  this.today = to
}

const a = new APPListItemOutput(1, 1, '123', '123', '', 100, 100, 100, 100, new Date(), new Date())

const lst = [
  a
]

const resp = {
  total: 1,
  list: lst
}

const yesterday = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 12, 21, 234]
const today = [1, 222, 555, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 12, 21, 234]

const s1 = new Stat(yesterday, today)

const app1 = new App(1, '123', '123', 'ashdoiahdoawihdohawoidhawoi', 'dwidhaoihdoiwa', 1, 2, new Date(), new Date(), 0)

module.exports = [
  {
    url: '/app/appList',
    type: 'get',
    response: config => {
      return {
        code: 200,
        msg: '',
        data: resp
      }
    }
  },
  {
    url: '/app/appStat',
    type: 'get',
    response: config => {
      return {
        code: 200,
        msg: '',
        data: s1
      }
    }
  },
  {
    url: '/app/detail',
    type: 'get',
    response: config => {
      return {
        code: 200,
        msg: '',
        data: app1
      }
    }
  }
]
