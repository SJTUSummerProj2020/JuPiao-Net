import React from "react";
import {Form, Input, Button, Checkbox, message} from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import '../css/login.css';
import {login} from "../services/userService";
import {history} from "../utils/history";

export class LoginForm extends React.Component{
    constructor(props) {
        super(props);
    }
    onFinish = values => {
        console.log('Received values of form: ', values);
        const data = {
            username: values.username,
            password: values.password
        };
        const callback = (data) => {
            console.log(data);
            if(data.status === 0){
                message.success(data.msg);
                localStorage.setItem('user', JSON.stringify(data.data));
                history.push("/");
            }
            else if(data.status < 0){
                message.error(data.msg);
            }
        }
        login(data,callback);
    }
    render() {
        return(
            <Form
                name="normal_login"
                className="login-form"
                onFinish={this.onFinish}
                initialValues={{ remember: false }}
            >
                <Form.Item
                    name="username"
                    rules={[{ required: true, message: '请输入您的用户名' }]}
                >
                    <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="用户名" />
                </Form.Item>
                <Form.Item
                    name="password"
                    rules={[{ required: true, message: '请输入您的密码' }]}
                >
                    <Input
                        prefix={<LockOutlined className="site-form-item-icon" />}
                        type="password"
                        placeholder="密码"
                    />
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" className="login-form-button">
                        登录
                    </Button>
                    <a className={"register"} href="" >立即注册</a>
                </Form.Item>
            </Form>
        );
    }
}
