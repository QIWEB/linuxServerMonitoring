/**
 * 配置全局使用的请求地址url
 *  11.24.201.68
 *  192.168.0.5
 */
export default {
  rootDirectory: function () { // 请求接口的根路径
    return 'http://192.168.0.26:9999'
  },
  loginURL: function () {
    return this.rootDirectory() + '/xx/login'
  },
  serverURL: function () { // 获取服务器所有数据
    return this.rootDirectory() + '/xx/getAll'
  },
  updateAllURL: function () { // 批量更新服务器数据
    return this.rootDirectory() + '/xx/updateFabanAll'
  },
  saveFabanURL: function () {
    return this.rootDirectory() + '/xx/saveFaban'
  },
  updateFabanURL: function () {
    return this.rootDirectory() + '/xx/updateFaban'
  },
  deleteFaban: function () {
    return this.rootDirectory() + '/xx/deleteFaban'
  },
  getUnilizationRatio: function () {
    return this.rootDirectory() + '/xx/getUnilizationRatio'
  },
  getServerOwnership: function () {
    return this.rootDirectory() + '/xx/getServerOwnership'
  },
  getServerDetails: function () {
    return this.rootDirectory() + '/xx/getServerDetails'
  },
  getServiceProcess: function () {
    return this.rootDirectory() + '/xx/getServiceProcess'
  },
  saveServiceProcess: function () {
    return this.rootDirectory() + '/process/saveServiceProcess'
  },
  getServiceProcessList: function () {
    return this.rootDirectory() + '/process/getServiceProcessList'
  },
  updateServiceProcess: function () {
    return this.rootDirectory() + '/process/updateServiceProcess'
  },
  deleteServiceProcess: function () {
    return this.rootDirectory() + '/process/deleteServiceProcess'
  },
  getServerIp: function () {
    return this.rootDirectory() + '/process/getServerIp'
  },
  batchUpdate: function () {
    return this.rootDirectory() + '/process/batchUpdate'
  }
}
