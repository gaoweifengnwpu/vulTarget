#!/usr/bin/python3
# -*- coding: utf-8 -*-

import paramiko
import os
import xlwt


def ssh_connect(_host, _username, _password):
    try:
        _ssh_fd = paramiko.SSHClient()
        _ssh_fd.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        _ssh_fd.connect(_host, username=_username, password=_password)
    except Exception:
        print('ssh %s@%s: %s' % (_username, _host, Exception))
        exit()
    return _ssh_fd


def ssh_exec_cmd(_ssh_fd, _cmd):
    return _ssh_fd.exec_command(_cmd)


def ssh_close(_ssh_fd):
    _ssh_fd.close()


localFile = 'sens.sh'
localpath = r'C:\Users\LENOVO\Desktop\poc\excavator-main\shellscan\password' + os.sep + localFile

remotepath = '/root/' + localFile


def ftpModuleFile(_host, _username, _password):
    t = paramiko.Transport((_host, 22))
    t.connect(username=_username, password=_password)
    sftp = paramiko.SFTPClient.from_transport(t)
    # remotepath='/csdp/user_launcher-1.0-dev/user-1.0-release.jar'
    # localpath= r'D:\workspace\csdp201512041\csdp-ningxia\csdp_user\user\target\user-1.0-release.jar'
    sftp.put(localpath, remotepath)
    t.close()
    print("：） 成功上传%s文件。" % remotepath)


def scan():
    wb = xlwt.Workbook(encoding='utf-8')  # 新建一个excel文件
    ws1 = wb.add_sheet('first')  # 添加一个新表，名字为first
    ws1.write(0, 0, '文件路径')
    ws1.write(0, 1, '所在行')
    ws1.write(0, 2, 'key')
    ws1.write(0, 3, 'value')
    row = 1  # 写入的起始行
    col = 0  # 写入的起始列
    # 通过row和col的变化实现指向单元格位置的变化
    # k = 1
    hostname = '192.168.108.220'
    port = 22
    username = 'root'
    password = 'Admin_0819'
    cmd = 'chmod 777 /root/sens.sh'
    cmd1 = 'bash ./sens.sh'
    ftpModuleFile(hostname, username, password)
    # cmd = "ifconfig"
    sshd = ssh_connect(hostname, username, password)
    stdin, stdout, stderr = ssh_exec_cmd(sshd, cmd)
    err_list = stderr.readlines()
    if len(err_list) > 0:
        print('脚本授权ERROR:' + err_list[0])
    stdin, stdout, stderr = ssh_exec_cmd(sshd, cmd1)
    err_list = stderr.readlines()
    if len(err_list) > 0:
        print('查找ERROR:' + err_list[0])
        # exit()
    for lines in stdout.readlines():
        # print(lines)
        a = lines.split(':')  # txt文件中每行的内容按逗号分割并存入数组中
        for i in range(len(a)):
            ws1.write(row, col, a[i])  # 向Excel文件中写入每一项
            col += 1
        row += 1
        col = 0
    wb.save("数据表.xls")
    ssh_close(sshd)
