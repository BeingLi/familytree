// pages/home/home.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tables: [],
    levelNumber: '2'

  },
  //事件处理函数
  btn_default: function (e) {
    wx.showModal({
      title: '提示',
      content: '这是一个模态弹窗',
      success: function (res) {
        if (res.confirm) {
          console.log('用户点击确定')
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },
  btn_home: function () {
    wx.redirectTo({
      url: '../home/home?levelNumber=1'
    });
  },
  btn_add: function (e) {
    wx.redirectTo({
      url: '../add/add'
    })
  },
  userNameInput: function (e) {
    this.setData({
      userName: e.detail.value
    })
  },

  btn_search: function (e) {
    wx.redirectTo({
      url: '../search/search?userName=' + this.data.userName,
    })

  },
  btn_update: function () {
    wx.showToast({
      title: '请先搜索确定您要修改的家族成员',
      icon: 'success',
      duration: 2000
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.request({
      url: getApp().data.servsers +'memberListByName', //仅为示例，并非真实的接口地址
      data: {
        userName: options.userName
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        that.setData({
          tables: res.data
        })
      }
    })

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})