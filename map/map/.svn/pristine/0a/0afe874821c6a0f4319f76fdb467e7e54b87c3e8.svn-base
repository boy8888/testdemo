package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;
//接收上传证件照片数据
public class UploadPictureVO extends AppBaseVO implements AppMobileDecidable{
	/*{
    "app":{
        "appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"
    },
     "upload":{
    "mobileNum":"13912345678",
    "name":"张三",
    "idType":"ID#",
    "idCode":"441233343143432141413"
    "picture":"BASE64后的图片",
    "picture2":"BASE64后的图片"
}
} */ 
	private UploadVO upload;
	@Override
	public String toString() {
		return "UploadPictureVO [upload=" + upload + ", app=" + app + "]";
	}
	@Override
	public String getAppId() {
		return app.getAppId();
	}

	@Override
	public String getMobileNum() {
		// TODO Auto-generated method stub
		return upload.getMobileNum();
	}

	public UploadVO getUpload() {
		return upload;
	}

	public void setUpload(UploadVO upload) {
		this.upload = upload;
	}
	
}
