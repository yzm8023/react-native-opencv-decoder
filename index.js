
import React, { Component } from 'react';
import {
    NativeModules,
} from 'react-native';

const QRDecoder = NativeModules.OpencvQRDecoder;

export default class OpencvQRDecoder extends Component {
    static init() {
        QRDecoder.init()
    }

    static decode(uri,callback) {
        QRDecoder.decode(uri,callback);
    }
}
