
import React, { Component } from 'react';
import {
    NativeModules,
    Platform,
} from 'react-native';

const QRDecoder = Platform.OS == 'android' ? NativeModules.OpencvQRDecoder : NativeModules.LocalBarcodeRecognizer;

export default class OpencvQRDecoder extends Component {
    static init() {
        if(Platform.OS == 'android'){
            QRDecoder.init();
        }
    }

    static decode(base64Img,callback) {
        QRDecoder.decode(base64Img,callback);
    }
}
