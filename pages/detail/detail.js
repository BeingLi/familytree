// pages/add/add.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    status:1,
    birthday:'',
    deathday:'',
    customItem: '全部',
    username: '未输入',
    mobile: '未输入',
    work: '未输入',
    home: '未输入',
    sex: 'male',
    father: '未输入',
    mother: '未输入',
    children: '未输入',
    level: '',
    memberIndex:''
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
  btn_update: function () {
    wx.redirectTo({
      url: '../update/update?memberIndex=' + this.data.memberIndex
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      memberIndex: options.memberIndex
    })   
    var that = this;
    wx.request({
      url: getApp().data.servsers +'memberDetail?memberIndex=' + options.memberIndex, 
      data: {
        
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      method: 'POST',
      success: function (res) {
        var sexstring = '';
        if (res.data.sex=='male'){
          sexstring='男';
        }
        if (res.data.sex == 'female') {
          sexstring = '女';
        }
        that.setData({
          birthday: res.data.birthDayString
        })
        if (res.data.deathday != null) {
          that.setData({
            deathday: res.data.deathdayString
          })
        }
        that.setData({
          username: res.data.username,
          mobile: res.data.mobile,
          work: res.data.work,
          home: res.data.home,
          sex: sexstring,
          father: res.data.father,
          mother: res.data.mother,
          children: res.data.children,
          level: res.data.levelCode,
          status: res.data.status
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

  },
  

})