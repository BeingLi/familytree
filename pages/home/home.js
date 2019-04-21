// pages/home/home.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    tables: [],
    array: [],
    levelNumber: 1,
    userName: '',
    levelstring: '第1辈'

  },
  //事件处理函数

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
  btn_add: function (e) {
    wx.redirectTo({
      url: '../add/add'
    })
  },
  btn_home: function () {
    wx.redirectTo({
      url: '../home/home?levelNumber=1'
    });
  },
  btn_up: function () {

  },
  btn_down: function () {


  },
  bindPickerChange: function (e) {
    var temp = e.detail.value;
    temp++;
    this.setData({
      levelstring: this.data.array[e.detail.value],
      levelnumber: temp
    })

    wx.redirectTo({
      url: '../home/home?levelNumber=' + temp
    });

  },
  btn_update: function () {
    wx.showToast({
      title: '请先确定您要修改的家族成员',
      icon: 'success',
      duration: 2000
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    var that = this;
    if (options.levelNumber) {
      this.setData({
        levelstring: '第' + options.levelNumber + '辈',
        levelNumber: options.levelNumber
      })
    }
    wx.request({
      url: getApp().data.servsers + 'memberListByLevel', //仅为示例，并非真实的接口地址
      data: {
        levelNumber: that.data.levelNumber
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
    wx.request({
      url: getApp().data.servsers + 'levelList',
      data: {

      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      method: 'GET',
      success: function (res) {
        that.setData({
          array: res.data
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