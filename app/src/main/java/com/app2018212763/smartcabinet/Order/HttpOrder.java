package com.app2018212763.smartcabinet.Order;

import com.alibaba.fastjson.JSON;
import com.app2018212763.smartcabinet.Bean.Order;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpOrder {

    public static String GetBoxInfo(String req){
        String address = "http://39.107.226.190:8080/smartcabinet_server/getboxinfo";
        String result = "";
        try{
            URL url = new URL(address);//初始化URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");//请求方式

            //超时信息
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            //post方式不能设置缓存，需手动设置为false
            conn.setUseCaches(false);

            //我们请求的数据
            String data = "req="+ URLEncoder.encode(req,"UTF-8");

            //获取输出流
            OutputStream out = conn.getOutputStream();

            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();

            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
                result = new String(message.toByteArray());
                //return result;
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static String OrderAdd (Order order){
        String jsonOutput = JSON.toJSONString(order);//bean转json字符串
        String address = "http://39.107.226.190:8080/smartcabinet_server/orderadd";
        String result = "";
        try{
            URL url = new URL(address);//初始化URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");//请求方式

            //超时信息
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            //post方式不能设置缓存，需手动设置为false
            conn.setUseCaches(false);

            //我们请求的数据
            String data = "jsonstring="+ URLEncoder.encode(jsonOutput,"UTF-8");

            //获取输出流
            OutputStream out = conn.getOutputStream();

            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();

            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
                result = new String(message.toByteArray());
                //return result;
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static String unlock(String msg){
        String address = "http://39.107.226.190:8080/smartcabinet_server/unlockcheck";
        String result = "";
        try{
            URL url = new URL(address);//初始化URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");//请求方式

            //超时信息
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            //post方式不能设置缓存，需手动设置为false
            conn.setUseCaches(false);

            //我们请求的数据
            String data = "unlockcheck="+ URLEncoder.encode(msg,"UTF-8");

            //获取输出流
            OutputStream out = conn.getOutputStream();

            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();

            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
                result = new String(message.toByteArray());
                //return result;
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static String GetOrderInfo(String order_creator, String order_range){
        String address = "http://39.107.226.190:8080/smartcabinet_server/getorderinfo";
        String result = "";
        try{
            URL url = new URL(address);//初始化URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");//请求方式

            //超时信息
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            //post方式不能设置缓存，需手动设置为false
            conn.setUseCaches(false);

            //我们请求的数据
            String data = "order_creator="+ URLEncoder.encode(order_creator,"UTF-8")
                    + "&" + "order_range="+ URLEncoder.encode(order_range,"UTF-8");

            //获取输出流
            OutputStream out = conn.getOutputStream();

            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();

            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
                result = new String(message.toByteArray());
                //return result;
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static String FinishOrder(String order_number){
        String address = "http://39.107.226.190:8080/smartcabinet_server/finishorder";
        String result = "";
        try{
            URL url = new URL(address);//初始化URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");//请求方式

            //超时信息
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            //post方式不能设置缓存，需手动设置为false
            conn.setUseCaches(false);

            //我们请求的数据
            String data = "order_number="+ URLEncoder.encode(order_number,"UTF-8");

            //获取输出流
            OutputStream out = conn.getOutputStream();

            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();

            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
                result = new String(message.toByteArray());
                //return result;
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static String DeleteOrder(String order_number){
        String address = "http://39.107.226.190:8080/smartcabinet_server/deleteorder";
        String result = "";
        try{
            URL url = new URL(address);//初始化URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");//请求方式

            //超时信息
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            //post方式不能设置缓存，需手动设置为false
            conn.setUseCaches(false);

            //我们请求的数据
            String data = "order_number="+ URLEncoder.encode(order_number,"UTF-8");

            //获取输出流
            OutputStream out = conn.getOutputStream();

            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();

            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
                result = new String(message.toByteArray());
                //return result;
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}
