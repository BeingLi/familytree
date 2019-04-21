// pages/add/add.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    worklist:[],
    array: [],
    status:1,
    levelstring:'第1辈',
    levelnumber:1,
    workstring:'',
    birthday:'',
    deathday:''
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  btn_home: function () {
    wx.redirectTo({
      url: '../home/home?levelNumber=1'
    });
  },
  btn_update: function () {
    wx.showToast({
      title: '请先搜索确定您要修改的家族成员',
      icon: 'success',
      duration: 2000
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
  alivecheck: function (e) {
    if (e.detail.value == true) {
      this.setData({
        status: 1
      })
    } else {
      this.setData({
        status: 0
      })
    }
    

  },
  
  btn_add: function (e) {
    wx.redirectTo({
      url: '../add/add'
    })
  },
  bindPickerChange: function (e) {
    var temp = e.detail.value;
    temp++;
    this.setData({
      levelstring: this.data.array[e.detail.value],
      levelnumber: temp
    })
  },
  bindDateChange: function (e) {
    this.setData({
      birthday: e.detail.value
    })
  },
  deathdayChange: function (e) {
    this.setData({
      deathday: e.detail.value
    })
  },
  workChange: function (e) {
    this.setData({
      workstring: this.data.worklist[e.detail.value],
    })
  },
 
  onLoad: function (options) {
    var that = this;

    wx.request({
      url: getApp().data.servsers +'workList',
      data: {

      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      method: 'GET',
      success: function (res) {
        that.setData({
          worklist: res.data
        })
      }
    })
    wx.request({
      url: getApp().data.servsers +'levelList',
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
  
  },
  formSubmit: function (e) {
  var that=this;
    wx.request({
      url: getApp().data.servsers +'addmember', //仅为示例，并非真实的接口地址
      data: {
        name: e.detail.value.username, sex: e.detail.value.sex,  mobile: e.detail.value.mobile, home: e.detail.value.home, father: e.detail.value.father, mother: e.detail.value.mother, children: e.detail.value.children, level: that.data.levelnumber, status: that.data.status,work: that.data.workstring,birthday: that.data.birthday,deathday: that.data.deathday
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res) {
        if (res.data) {
          wx.showToast({
            title: '提交成功',
            icon: 'success',
            duration: 2000
          })
          wx.redirectTo({
            url: '../home/home?levelNumber=' + that.data.levelnumber
          });
        }
        else
        {
          wx.showToast({
            title: '提交失败',
            icon: 'loading',
            duration: 2000
          })
        }
      }
    });
    
  },
  formReset: function () {
    
    console.log('form发生了reset事件')
  }
})