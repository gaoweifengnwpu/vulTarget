#!/bin/bash

#find / -regex ".*\(\.conf\|\.yaml\|\.sh\)$" -print0 | xargs -0  grep -H -rni -E "password:|pwd:|token:|password=|pwd=|token=" 2>finderror.log
#把标准输出和标准错误一起重定向到filename文件中
#find / -regex ".*\(\.conf\|\.yaml\|\.sh\)$" -print0 | xargs -0  grep -H -rni -E "password:|pwd:|token:|password=|pwd=|token=" > finderror.log 2>&1

#find / ! -path "/proc/*" -regex ".*\(\.conf\|\.yaml\|\.sh\)$" -print0 | xargs -0  grep -H -rni -E "password:|pwd:|token:|password=|pwd=|token=" 2> /dev/null
find /  ! -path "/root/sens.sh" -regex ".*\(\.conf\|\.yaml\|\.sh\)$" -print0 | xargs -0  grep -H -rni -E "password:|pwd:|token:|password=|pwd=|token=" 2> /dev/null


#command < filename 把标准输入重定向到filename文件中
#command 0< filename 把标准输入重定向到filename文件中
#command > filename 把标准输出重定向到filename文件中(覆盖)
#command 1> fielname 把标准输出重定向到filename文件中(覆盖)
#command >> filename 把标准输出重定向到filename文件中(追加)
#command 1>> filename 把标准输出重定向到filename文件中(追加)
#command 2> filename 把标准错误重定向到filename文件中(覆盖)
#command 2>> filename 把标准输出重定向到filename文件中(追加)
#command > filename 2>&1 把标准输出和标准错误一起重定向到filename文件中(覆盖)
#command >> filename 2>&1 把标准输出和标准错误一起重定向到filename文件中(追加)
#command < filename >filename2 把标准输入重定向到filename文件中，把标准输出重定向到filename2文件中
#command 0< filename 1> filename2 把标准输入重定向到filename文件中，把标准输出重定向到filename2文件中
