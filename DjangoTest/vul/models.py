from django.db import models


class User1(models.Model):
    user_id = models.AutoField(primary_key=True)
    user_name = models.CharField(max_length=20)
    password = models.CharField(max_length=20)


class Idc(models.Model):
    name = models.CharField("IDC名称", max_length=128, blank=False, null=False)
    address = models.CharField("IDC地址", max_length=200, default="")
    phone = models.CharField("IDC联系电话", max_length=15, null=True)
    # user = models.CharField("IDC联系人",max_length=32,null=True)
