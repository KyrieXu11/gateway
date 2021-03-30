const tokens = {
  admin: {
    token: 'admin-token'
  },
  editor: {
    token: 'editor-token'
  }
}

const users = {
  'admin-token': {
    roles: ['admin'],
    introduction: 'I am a super administrator',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    username: 'Super Admin'
  },
  'editor-token': {
    roles: ['editor'],
    introduction: 'I am an editor',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    username: 'Normal Editor'
  }
}

module.exports = [
  // user login
  {
    url: '/admin/login',
    type: 'post',
    response: config => {
      const { username } = config.body
      const token = tokens[username]

      // mock error
      if (!token) {
        return {
          code: 400,
          msg: 'Account and password are incorrect.'
        }
      }

      return {
        code: 200,
        msg: '登录成功',
        data: token
      }
    }
  },

  // get user info
  {
    url: '/admin/info\.*',
    type: 'get',
    response: config => {
      const { token } = config.query
      const info = users[token]

      // mock error
      if (!info) {
        return {
          code: 400,
          msg: 'Login failed, unable to get user details.'
        }
      }

      return {
        code: 200,
        msg: '',
        data: info
      }
    }
  },

  // user logout
  {
    url: '/admin/logout',
    type: 'post',
    response: _ => {
      return {
        code: 200,
        msg: '',
        data: 'success'
      }
    }
  },

  {
    url: '/admin/changePass',
    type: 'put',
    response: config => {
      return {
        code: 200,
        msg: '修改成功',
        data: null
      }
    }
  }
]
