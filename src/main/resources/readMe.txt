npm install --registry=https://registry.npm.taobao.org
npm install -g vue-cli  脚手架

修改源码  element-ui/lib/theme-chalk/index.css修改el-carousel__arrow样式  高度100px 宽度100px 是切换图片左右两侧的按钮  color:red  background-color:rgba(239, 71, 11, 0.36)

修改源码 element-ui.common.js TreeStore.prototype.getCheckedNodes方法修改 if ((child.checked || includeHalfChecked || child.indeterminate) && (!leafOnly || leafOnly && child.isLeaf)) {


参数：
{
    "VisitGuid": "1",
    "Station":"control",
    "Msg":"作业成功",
    "ArriveTime": "2019/12/25 23:27:39",
    "EnterTime": "2019/12/26 00:00:39",
    "GeneralInfo":
    {
      "LaneCode":"YT-1",
      "RFID":"WEFSDFWE23456764354",
      "Weight":"23456",
      "CntrSize":"2"
    },
    "OCRCarPlate": {
        "OcrPlate": "闽C92154",
        "PlateColor": ""
    },
    "OCRFrontContainer": {
        "OCRContainerNo": "CCLU0123456",
        "OCRContainerConf": 0.99,
        "OCRContainerDirection": "A",
        "OCRContainerISO": "22G1",
        "OCRISOConf": 0.99,
        "OCRDamage":"0",
        "EFID":"E"
    },
    "OCRAfterContainer": {
        "OCRContainerNo": "CCLU6543210",
        "OCRContainerConf": 0.99,
        "OCRContainerDirection": "F",
        "OCRContainerISO": "22G1",
        "OCRISOConf": 0.99,
        "OCRDamage":"0",
        "EFID":"E"
    },
    "FtpImages": {
        "Folder": "232739",
        "ImageName": "Plate.jpg,A01.jpg,B01.jpg",
        "ImagePath": "ftp://192.168.80.250/G04/2018/201812/20181204/232739"
    }
}



添加引入 v-viewer
  npm install v-viewer --save
  main.js中引入
    import Viewer from 'v-viewer'
    import 'viewerjs/dist/viewer.css'

    Vue.use(Viewer)


