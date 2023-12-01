import json

from django.http import HttpResponse
from django.shortcuts import render, redirect

from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User

from vul.models import User1
from vul.util.tool import tool

import nmap

def login_view(request):
    if request.method == 'POST':
        body = json.loads(request.body)
        username = body["username"]  # 获取用户名
        password = body["password"]  # 获取用户的密码
        # lion = User.objects.get(user_name="lion")
        # print(lion.balance)
        # 与数据库中的用户名和密码比对，django默认保存密码是以哈希形式存储，并不是明文密码，这里的password验证默认调用的是User类的check_password方法，以哈希值比较。
        user = authenticate(username=username, password=password)
        # 验证如果用户不为空
        if user is not None:
            # if lion.password == password:
            # login方法登录
            login(request, user)
            # 返回登录成功信息
            t = tool(0, 'success')
            return HttpResponse(json.dumps(t.res()), content_type='application/json')
        else:
            t = tool(1, 'fail')
            return HttpResponse(json.dumps(t.res()), content_type='application/json')

    return render(request, 'login.html')


def register(request):
    body = json.loads(request.body)
    username = body["username"]  # 获取用户名
    password = body["password"]  # 获取用户的密码
    d = dict(username=username, password=password, is_staff=1, is_superuser=1, is_active=1)
    user = User.objects.create_user(**d)
    user.save()

    # User1.objects.create(user_name="lion", password="roar")
    # User1.objects.create(user_name="cat", password="meow")
    # 返回登录成功信息
    t = tool(0, 'register success')
    return HttpResponse(json.dumps(t.res()), content_type='application/json')


def logout(request):
    logout(request)


def port_scan(request):
    nm = nmap.PortScanner()
    nm.scan(hosts='192.168.108.129', arguments='-p 22-443')
    #
    # for host in nm.all_hosts():
    #     print('Host : %s (%s)' % (host, nm[host].hostname()))
    #     print('State : %s' % nm[host].state())
    #     for proto in nm[host].all_protocols():
    #         print('Protocol : %s' % proto)
    #
    #         lport = nm[host][proto].keys()
    #         for port in lport:
    #             print('port : %s\tstate : %s' % (port, nm[host][proto][port]['state']))
