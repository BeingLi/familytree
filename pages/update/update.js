// pages/add/add.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    array: [],
    worklist:[],
    status: 1,
    birthday: '',
    deathday: '',
    customItem: '全部',
    username:'未输入',
    mobile: '未输入',
    work: '未输入',
    home: '未输入',
    sex: 'male',
    father: '未输入',
    mother: '未输入',
    children: '未输入',
    levelnumber: 1,
    levelstring:'',
    workstring:'',
    memberIndex: "1",
    malecheck:false,
    femalecheck:false

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
    wx.showToast({
      title: '当前已经是更改页面',
      icon: 'success',
      duration: 2000
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
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      memberIndex: options.memberIndex
    })
    var that = this;
    
    wx.request({
      url: getApp().data.servsers+'workList',
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
    wx.request({
      url: getApp().data.servsers +'memberDetail?memberIndex=' + options.memberIndex,
      data: {

      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      method: 'POST',
      success: function (res) {
        if (res.data.sex=="male"){
          that.setData({
          malecheck:true})
        }
        if (res.data.sex == "female") {
          that.setData({
            femalecheck: true
          })
        }
        that.setData({
          birthday: res.data.birthDayString
        })
        if (res.data.deathday != null ){
          that.setData({
            deathday: res.data.deathdayString
          })
        }
        
        that.setData({

          username: res.data.username,
          mobile: res.data.mobile,
          workstring: res.data.work,
          home: res.data.home,
          sex: res.data.sex,
          father: res.data.father,
          mother: res.data.mother,
          children: res.data.children,
          levelnumber: res.data.levelCode,
          levelstring: '第' + res.data.levelCode+'辈',
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
  formSubmit: function (e) {
    var that = this;
    wx.request({
      url: getApp().data.servsers +'updatemember', //仅为示例，并非真实的接口地址
      data: {
        name: e.detail.value.username, sex: e.detail.value.sex, work: e.detail.value.work, mobile: e.detail.value.mobile, home: e.detail.value.home, father: e.detail.value.father, mother: e.detail.value.mother, children: e.detail.value.children, level: that.data.levelnumber, memberIndex: that.data.memberIndex, birthday: that.data.birthday, deathday: that.data.deathday, status: that.data.status,
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res) {
        var tip = res;
        wx.showToast({
          title: '提交成功',
          icon: 'success',
          duration: 2000
        })
        wx.redirectTo({
          url: '../home/home?levelNumber=' + that.data.levelnumber
        });
        
      }
    });

  },
  formReset: function () {

    console.log('form发生了reset事件')
  }
})