/*
 * A smart barcode scanner for react-native apps
 * https://github.com/react-native-component/react-native-smart-barcode/
 * Released under the MIT license
 * Copyright (c) 2016 react-native-component <moonsunfall@aliyun.com>
 */


import React, { Component } from 'react';
import PropTypes from 'prop-types';
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
